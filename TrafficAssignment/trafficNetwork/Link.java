package trafficNetwork;

import performanceFunction.PerformanceFunction;

public class Link
{
	private NodePair m_node_pair;
	private final int m_link_id;// 考虑到相同2个节点之间也可能有多个link，所以这里需要使用link_id区分
	private final String m_link_name;// 定义一个名字，便于输出时显示
	private double m_cost;
	private double m_flow;
	private PerformanceFunction m_performanceFunction;
	private static int LINK_ID = 0;

	public Link(String link_name, Node from_node, Node to_node,
			PerformanceFunction performanceFunction)
	{
		m_node_pair = new NodePair(from_node, to_node);
		m_performanceFunction = performanceFunction;
		m_link_id = LINK_ID++;
		m_link_name = link_name;
	}

	public PerformanceFunction getPerformanceFunction()
	{
		return m_performanceFunction;
	}
	public int getLinkId()
	{
		return m_link_id;
	}

	public String toString()
	{
		return "LINK\"" + m_link_name + "\"[" + getFromNode() + ","
				+ getToNode() + "]" + "(flow:" + String.format("%2.2f", getFlow())
				+ ",cost:" + String.format("%2.2f", getCost()) + ")";
	}

	public Node getFromNode()
	{
		return m_node_pair.getFromNode();
	}

	public Node getToNode()
	{
		return m_node_pair.getToNode();
	}

	public NodePair getNodePair()
	{
		return m_node_pair;
	}

	public double getCost()
	{
		return m_cost;
	}

	public double getFlow()
	{
		return m_flow;
	}

	public void setFlow(double flow)
	{
		m_flow = flow;
		m_cost = m_performanceFunction.calculate(flow);
	}

	public boolean equals(Link link)
	{
		return m_link_id == link.m_link_id;
	}

	public String getLinkName()
	{
		return m_link_name;
	}
}
