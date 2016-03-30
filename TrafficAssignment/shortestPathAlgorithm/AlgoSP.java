package shortestPathAlgorithm;

import trafficNetwork.*;

public abstract class AlgoSP
{
	private final String m_name;

	public String getName()
	{
		return m_name;
	}
	public AlgoSP(String name)
	{
		m_name = name;
	}
	abstract public boolean Do(Network network,Node start_node, Node end_node);
	abstract public void outputShortestPath(Node start_node, Node end_node);
	abstract public Path getShortestPath(Node start_node, Node end_node);
}
