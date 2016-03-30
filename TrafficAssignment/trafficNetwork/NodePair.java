package trafficNetwork;

public class NodePair
{
	private Node m_fromNode;
	private Node m_toNode;

	protected NodePair(Node from_node, Node to_node)
	{
		m_fromNode = from_node;
		m_toNode = to_node;
	}

	protected Node getFromNode()
	{
		return m_fromNode;
	}

	protected void setFromNode(Node node)
	{
		m_fromNode = node;
	}

	protected Node getToNode()
	{
		return m_toNode;
	}

	protected void setToNode(Node node)
	{
		m_toNode = node;
	}

	protected boolean equals(NodePair node_pair)
	{
		return (m_fromNode == node_pair.m_fromNode)
				&& (m_toNode == node_pair.m_toNode);
	}
}
