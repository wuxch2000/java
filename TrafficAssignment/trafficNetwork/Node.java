package trafficNetwork;

public class Node
{
	private final int m_node_id;

	public int getNodeId()
	{
		return m_node_id;
	}

	public Node(int id)
	{
		m_node_id = id;
	}

	public String toString()
	{
		return "Node(" + m_node_id + ")";
	}
}
