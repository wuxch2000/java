package shortestPathAlgorithm;

import java.util.*;

import trafficNetwork.*;
import utility.Output;

/**
 * @author wuxch
 *
 */
public class AlgoLableSetting extends AlgoSP
{

	private NodeSet m_permanent_nodes;
	private NodeSet m_temporary_nodes;
	private HashMap<Node, Double> m_d;// node_id��value��Ӧ
	private HashMap<Node, Node> m_pred; // ���ǰһ���ڵ����Ϣ
	private HashMap<Node, Link> m_pred_link;// ���ǵ�2���ڵ���Ҫ���ڶ��link�����������linkҲ����
	private final double INFINITE = Double.POSITIVE_INFINITY;

	public AlgoLableSetting()
	{
		super("LableSetting");

		m_permanent_nodes = new NodeSet();
		m_temporary_nodes = new NodeSet();

		m_d = new HashMap<Node, Double>();
		m_pred = new HashMap<Node, Node>();
		m_pred_link = new HashMap<Node, Link>();
	}

	private void InitialAll(Network network,Node start_node)
	{
		if( m_permanent_nodes.isEmpty() == false )
			m_permanent_nodes.clear();
		if( m_temporary_nodes.isEmpty() == false )
		{
			m_temporary_nodes.clear();

		}
		m_temporary_nodes.addAll(network.getNodeSet());
		InitialD(start_node);
		InitialPred(start_node);

		return;
	}

	private void InitialD(Node start_node)
	{
		if(m_d.isEmpty()==false)
			m_d.clear();
		m_d.put(start_node, 0.0);
	}

	private void InitialPred(Node start_node)
	{
		if(m_pred.isEmpty()==false)
			m_pred.clear();
		if(m_pred_link.isEmpty()==false)
			m_pred_link.clear();
		m_pred.put(start_node,start_node);
	}
	public boolean Do(Network network,Node start_node, Node end_node)
	{
		InitialAll(network,start_node);
		Node permanent_node;
		while (m_permanent_nodes.size() < network.getNodeNumber())
		{
			permanent_node = GetNodeFromTemporarySetByMinValueOfD();

			m_permanent_nodes.add(permanent_node);
			m_temporary_nodes.remove(permanent_node);

			LinkSet link_set = network.getLinkSetByFromNode(permanent_node);
			if (link_set == null)
				break;

			for (Link link : link_set)
			{
				double cost = link.getCost();
				Node fromNode = link.getFromNode();
				Node toNode = link.getToNode();

				double toNodeCost;
				if (m_d.containsKey(toNode))
				{
					toNodeCost = m_d.get(toNode);
				}
                else
				{
					toNodeCost = INFINITE;
				}

				double fromNodeCost;
				if (m_d.containsKey(fromNode))
				{
					fromNodeCost = m_d.get(fromNode);
				} else
				{
					fromNodeCost = INFINITE;
				}

				if (toNodeCost > fromNodeCost + cost)
				{
					m_d.put(toNode, fromNodeCost + cost);
					m_pred.put(toNode, fromNode);
					m_pred_link.put(toNode, link);
				}
			}
		}
		return true;
	}

	/**
	 * ����ʱnode�����ҵ���Ӧ��cost��С��node���������node
	 *
	 * @return Node
	 */
	private Node GetNodeFromTemporarySetByMinValueOfD()
	{
		Node node_corresponde_to_min_value = null;
		double min_value = INFINITE;

		for (Node node : m_temporary_nodes)
		{
			if (m_d.containsKey(node))
			{
				double value = m_d.get(node);
				if (value < min_value)
				{
					min_value = value;
					node_corresponde_to_min_value = node;
				}
			}
		}
		return node_corresponde_to_min_value;
	}


	public Path getShortestPath(Node start_node, Node end_node)
	{
		Path shortest_path_forward = new Path();

		Node to_node = end_node;
		Node from_node = null;
		while (true)
		{
			/**
			 * ��Ϊm_pred��ŵľ��������·���ϣ�ÿ��nodeǰ���һ��node��
			 * ����������m_pred�ϲ��˵Ĵ�end_node��ǰ������ֱ��start_node.
			 */
			from_node = m_pred.get(to_node);
			Link link = m_pred_link.get(to_node);
			shortest_path_forward.addFirst(link);
			if (from_node == start_node)
			{
				break;
			}
			// ��ǰ����һ��
			to_node = from_node;
		}
		return shortest_path_forward;
	}

	public void outputShortestPath(Node start_node, Node end_node)
	{
		System.out.print(getShortestPath(start_node, end_node));
	}

	public double getShortestCost(Node start_node, Node end_node)
	{
		return m_d.get(end_node);
	}

	public void outputShortestCost(Node start_node, Node end_node)
	{
		double cost = getShortestCost(start_node, end_node);
		Output.print(3,"Shortest to " + end_node + "'s cost is:" + cost);
	}
}
