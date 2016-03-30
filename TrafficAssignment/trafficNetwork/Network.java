package trafficNetwork;

import java.util.*;

public class Network
{
	private LinkSet m_links;
	// ���¶���pointer����һ�����LinkSet��HashMap��ÿ��node_id��Ӧ��һ��LinkSet����������Դ�Ϊ���Ķ��Link
	private HashMap<Node, LinkSet> m_from_node_pointers;
	// ���һ��to_node_pointers����������Դ�Ϊ�յ�Ķ��Link
	private HashMap<Node, LinkSet> m_to_node_pointers;
	// ���һ�����Դ���������յ�Ϊ������LinkSet����Ҫ����Ϊ���ǵ����ܴ���2��node֮���ж��link�����
	// private HashMap<NodePair,LinkSet> m_node_pair_pointers;
	private NodeSet m_nodes;

	public Network()
	{
		m_from_node_pointers = new HashMap<Node, LinkSet>();
		m_to_node_pointers = new HashMap<Node, LinkSet>();
		// m_node_pair_pointers = new HashMap<NodePair, LinkSet>();
		m_links = new LinkSet();
		m_nodes = new NodeSet();
	}

	public NodeSet getNodeSet()
	{
		return m_nodes;
	}

	public LinkSet getLinkSet()
	{
		return m_links;
	}

	private void addToNodePointer(Link link)
	{
		Node node = link.getToNode();
		LinkSet link_set;
		if (!m_to_node_pointers.containsKey(node))
		{
			link_set = new LinkSet();
			m_to_node_pointers.put(node, link_set);
		} else
		{
			link_set = m_to_node_pointers.get(node);
		}
		link_set.add(link);
	}

	private void addFromNodePointer(Link link)
	{
		Node node = link.getFromNode();
		LinkSet link_set;
		if (!m_from_node_pointers.containsKey(node))
		{
			link_set = new LinkSet();
			m_from_node_pointers.put(node, link_set);
		} else
		{
			link_set = m_from_node_pointers.get(node);
		}
		link_set.add(link);
	}

	public void addLink(Link link)
	{
		m_links.add(link);
		m_nodes.add(link.getFromNode());
		m_nodes.add(link.getToNode());

		// ������Ҫ���������link��Ӧ��point����
		addFromNodePointer(link);
		addToNodePointer(link);
	}

	public LinkSet getLinkSetByNodePair(Node from_node, Node to_node)
	{
		LinkSet from_node_link_set = getLinkSetByFromNode(from_node);
		LinkSet link_set = new LinkSet();
		for (Link link : from_node_link_set)
		{
			if (link.getToNode().equals(to_node))
			{
				link_set.add(link);
			}
		}
		return link_set;
	}

	public LinkSet getLinkSetByFromNode(Node from_node)
	{
		return m_from_node_pointers.get(from_node);
	}

	public LinkSet getLinkSetByToNode(Node to_node)
	{
		return m_to_node_pointers.get(to_node);
	}

	public boolean deleteLink(Link link)
	{
		// m_pointerҲ��Ҫ�޸�
		LinkSet link_set;

		link_set = getLinkSetByFromNode(link.getFromNode());
		link_set.remove(link);

		link_set = getLinkSetByToNode(link.getToNode());
		link_set.remove(link);

		link_set = getLinkSetByNodePair(link.getFromNode(), link.getToNode());
		link_set.remove(link);

		m_links.remove(link);
		return true;
	}

	public int getNodeNumber()
	{
		return m_nodes.size();
	}

	private void setAllLinksFlow(double flow)
	{
		for(Link link : m_links)
		{
			link.setFlow(flow);
		}
		return;
	}

	public void setPathFlow(Path path, double flow)
	{
		setAllLinksFlow(0);
		for (Link link : path)
		{
			link.setFlow(flow);
		}
	}

	public void addPathFlow(Path path, double flow)
	{
		for (Link link : path)
		{
			link.setFlow(flow);
		}
	}

	public void load(FlowSet flow_set)
	{
		setFlowSet(flow_set);
		return;
	}
	public void load(String link_name, double flow)
	{
		Link link = m_links.getLink(link_name);
		link.setFlow(flow);
	}
	public void load(double flow)
	{
		setAllLinksFlow(flow);
	}

	public String toString()
	{
		String string = new String();
		for (Link link : m_links)
		{
			string = string + link.toString() + "\n";
		}
		return string;
	}

	public FlowSet getFlowSet()
	{
		FlowSet flow_set = new FlowSet();
		for (Link link : m_links)
		{
			flow_set.put(link,link.getFlow());
		}
		return flow_set;
	}

	public CostSet getCostSet()
	{
		CostSet const_set = new CostSet();
		for (Link link : m_links)
		{
			const_set.put(link,link.getCost());
		}
		return const_set;
	}

	public void setFlowSet(FlowSet flow_set)
	{
		for (Link link : flow_set.keySet())
		{
			link.setFlow(flow_set.get(link));
		}
		return;
	}

}
