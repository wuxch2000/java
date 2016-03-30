package odAsign;

import trafficNetwork.Node;
import trafficNetwork.NodePair;

public class ODPair extends NodePair
{
	private final String	m_name;
	private final double	m_flow;

	public ODPair(String m_name, Node from_node, Node to_node, double m_flow)
	{
		super(from_node, to_node);
		this.m_name = m_name;
		this.m_flow = m_flow;
	}

	public String getName()
	{
		return m_name;
	}

	public Node getStartNode()
	{
		return super.getFromNode();
	}
	public Node getEndNode()
	{
		return super.getToNode();
	}
	public double getFlow()
	{
		return m_flow;
	}
	public String toString()
	{
		return "ODPair\"" + m_name + "\"[" + getStartNode() + ","+ getEndNode() + "]";
	}
}
