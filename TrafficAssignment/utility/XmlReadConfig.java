package utility;

import java.io.*;
import java.util.*;
import odAsign.*;

import org.jdom.*;
import org.jdom.input.*;

import performanceFunction.*;
import shortestPathAlgorithm.*;
import trafficNetwork.*;

public class XmlReadConfig
{
	private final String TAG_NODES		= "nodes";
	private final String TAG_NODE		= "node";
	private final String TAG_NODE_ID	= "id";
	private final String TAG_LINKS		= "links";
	private final String TAG_LINK		= "link";
	private final String TAG_LINK_NAME	= "name";
	private final String TAG_OD_NAME	= "name";
	private final String TAG_FROM_NODE	= "from-node";
	private final String TAG_TO_NODE	= "to-node";
	private final String TAG_PERF_FUNC	= "perf-func";
	private final String TAG_TYPE		= "type";
	private final String TAG_VALUE		= "value";
	private final String TAG_A			= "a";
	private final String TAG_B			= "b";
	private final String TAG_C			= "c";
	private final String TAG_E			= "e";
	private final String TAG_N			= "n";
	private final String TAG_FLOW		= "flow";
	private final String TAG_SHORTEST 	= "shortest_path_algo";
	private final String TAG_O_D   		= "o_d";
	private final String TAG_METHOD		= "method";
	private final String TAG_FW			= "fw";
	private final String TAG_MSA		= "msa";
	private final String TAG_NODE_PAIR	= "node_pair";
	private final String TAG_BISEC_DELTA = "bisec_delta";

	public AlgoSP getAlgo()
	{
		return m_algo;
	}

	private Network		m_network;
	private NodeSet		m_nodeset;
	private AlgoSP		m_algo;
	private AlgoOD		m_algoOD;

	private HashSet<ODPair>	m_start_end_pair;

	public HashSet<ODPair> getStartEndPair()
	{
		return m_start_end_pair;
	}

	public AlgoOD getAlgoOD()
	{
		return m_algoOD;
	}

	public Network getNetwork()
	{
		return m_network;
	}

	private Document	m_network_doc;
	private Document	m_flow_doc;
	private Document	m_algo_doc;
	private Document	m_output_doc;
	public XmlReadConfig(String network_file_name,String flow_file_name,String algo_file_name, String output_file_name)
	{
		super();
		SAXBuilder network_builder = new SAXBuilder();
		SAXBuilder flow_builder = new SAXBuilder();
		SAXBuilder algo_builder = new SAXBuilder();
		SAXBuilder output_builder = new SAXBuilder();
		try
		{
			m_network_doc = network_builder.build(new File(network_file_name));
			m_flow_doc = flow_builder.build(new File(flow_file_name));
			m_algo_doc = algo_builder.build(new File(algo_file_name));
			m_output_doc = output_builder.build(new File(output_file_name));
			m_network = new Network();
			m_nodeset = new NodeSet();
			m_start_end_pair = new HashSet<ODPair>();
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	public static XmlReadConfig BuildCfgByArgs(String[] args)
	{
		String network_file = null;
		String flow_file = null;
		String algo_file = null;
		String output_file = null;
		for (int i=0; args != null && i<args.length; i++)
		{
			if(args[i].equalsIgnoreCase("-n"))
			{
				i++;
				network_file = new String(args[i]);
			}
			if(args[i].equalsIgnoreCase("-f"))
			{
				i++;
				flow_file = new String(args[i]);
			}
			if(args[i].equalsIgnoreCase("-a"))
			{
				i++;
				algo_file = new String(args[i]);
			}
			if(args[i].equalsIgnoreCase("-o"))
			{
				i++;
				output_file = new String(args[i]);
			}
		}
		if( network_file == null || flow_file == null || algo_file == null || output_file == null)
		{
			System.err.println("Usage:java -jar od.jar -n network.xml -a algo.xml -f flow.xml -o output.xml");
			return null;
		}
		else
			return new XmlReadConfig(network_file,flow_file,algo_file,output_file);
	}

	public void Init()
	{

		try
		{
			readOutput();
			readNodes();
			readLinks();
			readFlow();
			readStartEndPair();
			readAlgoStortestPath();
			readAlgoOD();
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		return;
	}

	private void readOutput()
	{
		try
		{
			Element element_root = m_output_doc.getRootElement();


			Element element_console = element_root.getChild("console");
			String output_to_console_str = element_console.getAttributeValue("output");
			boolean output_to_console = false;
			if(output_to_console_str.equalsIgnoreCase("yes"))
			{
				output_to_console = true;
			}
			int console_threshold = element_console.getAttribute("threshold").getIntValue();
			OutputConsoleCfg console = new OutputConsoleCfg(output_to_console,console_threshold);


			Element element_file = element_root.getChild("file");
			String output_to_file_str = element_file.getAttributeValue("output");
			boolean output_to_file = false;
			if(output_to_file_str.equalsIgnoreCase("yes"))
			{
				output_to_file = true;
			}
			int file_threshold = element_file.getAttribute("threshold").getIntValue();
            String output_to_file_name = element_file.getAttributeValue("file_name");
            OutputFileCfg file = new OutputFileCfg(output_to_file,file_threshold,output_to_file_name);

            Output.buildOutput(console, file);


		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void readStartEndPair()
	{
		try
		{
			Element element_root = m_algo_doc.getRootElement();
			List<Element> element_node_pair_list = element_root.getChildren(TAG_NODE_PAIR);
			for (Element element_node_pair : element_node_pair_list)
			{
				double o_d_flow = element_node_pair.getAttribute(TAG_FLOW).getDoubleValue();
				String o_d_name = element_node_pair.getAttributeValue(TAG_OD_NAME);
				List<Element> element_node_list = element_node_pair.getChildren(TAG_NODE);
				Node start_node = null;
				Node end_node = null;
				for (Element element_node : element_node_list)
				{
					String node_type = element_node.getAttributeValue(TAG_TYPE);
					int node_id = element_node.getAttribute(TAG_NODE_ID).getIntValue();
					if (node_type.equalsIgnoreCase("start"))
						start_node = m_nodeset.getNode(node_id);
					else if (node_type.equalsIgnoreCase("end"))
						end_node = m_nodeset.getNode(node_id);
					else
						return;
				}
				m_start_end_pair.add(new ODPair(o_d_name,start_node, end_node,o_d_flow));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	private void readAlgoOD()
	{
		try
		{
			Element element_root = m_algo_doc.getRootElement();

			
			
			Element element_od = element_root.getChild(TAG_O_D);

			String method = element_od.getAttributeValue(TAG_METHOD);

			if(method.equalsIgnoreCase(TAG_FW))
			{
				double o_d_e = element_od.getChild(TAG_FW).getAttribute(TAG_E).getDoubleValue();
				double o_d_bisec_delta = element_od.getChild(TAG_FW).getAttribute(TAG_BISEC_DELTA).getDoubleValue();
				if( m_start_end_pair.size() == 1 )
				{
					ODPair start_end_node = (ODPair)m_start_end_pair.iterator().next();
					m_algoOD = new AlgoSingleFW(m_network,m_algo,o_d_e,o_d_bisec_delta,start_end_node);
				}
				else
				{
					m_algoOD = new AlgoMultiFW(m_network,m_algo,o_d_e,o_d_bisec_delta,m_start_end_pair);

				}
			}
			else if (method.equalsIgnoreCase(TAG_MSA))
			{
				int o_d_n =  element_od.getChild(TAG_MSA).getAttribute(TAG_N).getIntValue();
				if( m_start_end_pair.size() == 1 )
				{
					ODPair start_end_node = (ODPair)m_start_end_pair.iterator().next();
					m_algoOD = new AlgoSingleMSA(m_network,m_algo,o_d_n,start_end_node);
				}
				else
				{
					m_algoOD = new AlgoMultiMSA(m_network,m_algo,o_d_n,m_start_end_pair);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	@SuppressWarnings("unchecked")
	private void readNodes()
	{
		Element element_network = m_network_doc.getRootElement();
		Element element_nodes	= element_network.getChild(TAG_NODES);
		List<Element> element_node_list = element_nodes.getChildren(TAG_NODE);

		for (Element element_node : element_node_list)
		{
			int node_id;
			try
			{
				node_id = element_node.getAttribute(TAG_NODE_ID).getIntValue();
			} catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
			Node node = new Node(node_id);
			m_nodeset.add(node);
		}

		return;
	}
	@SuppressWarnings("unchecked")
	private void readLinks()
	{
		Element element_network = m_network_doc.getRootElement();
		Element element_links	= element_network.getChild(TAG_LINKS);
		List<Element> element_link_list = element_links.getChildren(TAG_LINK);
		for( Element element_link : element_link_list)
		{
			readLink(element_link);
		}
		return;
	}

	private void readAlgoStortestPath()
	{
		try
		{
			Element element_root = m_algo_doc.getRootElement();
			Element element_algo_shortest_path = element_root.getChild(TAG_SHORTEST);
			String algo_type_shortest_path = element_algo_shortest_path.getAttributeValue(TAG_TYPE);
			if(algo_type_shortest_path.equalsIgnoreCase("CostSpread"))
				m_algo = new AlgoCostSpread();
			else if (algo_type_shortest_path.equalsIgnoreCase("LableSetting"))
				m_algo = new AlgoLableSetting();
			else
				return;

		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void readFlow()
	{
		Element element_links = m_flow_doc.getRootElement();
		List<Element> element_link_list = element_links.getChildren(TAG_LINK);
		for( Element element_link : element_link_list)
		{
			try
			{
				String link_name = element_link.getAttributeValue(TAG_LINK_NAME);
				Element element_flow = element_link.getChild(TAG_FLOW);
				double flow = element_flow.getAttribute(TAG_VALUE).getDoubleValue();
				m_network.load(link_name, flow);
			} catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
		}
		return;
	}

	private void readLink(Element element_link)
	{
		String link_name;
		String perf_func_type;

		Node from_node;
		Node to_node;
		try
		{
			link_name = element_link.getAttributeValue(TAG_LINK_NAME);
			int from_node_id = element_link.getChild(TAG_FROM_NODE).getAttribute(TAG_NODE_ID).getIntValue();
			int to_node_id = element_link.getChild(TAG_TO_NODE).getAttribute(TAG_NODE_ID).getIntValue();
			perf_func_type = element_link.getChild(TAG_PERF_FUNC).getAttributeValue(TAG_TYPE);

			from_node = m_nodeset.getNode(from_node_id);
			to_node = m_nodeset.getNode(to_node_id);

			PerformanceFunction perf_func;
			if( perf_func_type.equalsIgnoreCase("x_a") )
			{
				double perf_func_value = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_A).getDoubleValue();
				perf_func = new PF_x_a(perf_func_value);
			}
			else if( perf_func_type.equalsIgnoreCase("a") )
			{
				double perf_func_value = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_A).getDoubleValue();
				perf_func = new PF_a(perf_func_value);
			}
			else if(perf_func_type.equalsIgnoreCase("ax_b"))
			{
				double perf_func_a = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_A).getDoubleValue();
				double perf_func_b = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_B).getDoubleValue();
				perf_func = new PF_ax_b(perf_func_a,perf_func_b);
			}
			else if(perf_func_type.equalsIgnoreCase("a_x4_b4_c"))
			{
				double perf_func_a = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_A).getDoubleValue();
				double perf_func_b = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_B).getDoubleValue();
				double perf_func_c = element_link.getChild(TAG_PERF_FUNC).getAttribute(TAG_C).getDoubleValue();
				perf_func = new PF_a_x4_b4_c(perf_func_a,perf_func_b,perf_func_c);
			}
			else
			{
				return;
			}
			Link link = new Link(link_name,from_node,to_node,perf_func);
			m_network.addLink(link);

		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
}
