package odAsign;

import shortestPathAlgorithm.AlgoSP;
import trafficNetwork.*;

public abstract class AlgoOD
{
	protected final String m_name;
	private	Network	m_network;
	protected AlgoSP m_algo_shortest_path;
	public Network getNetwork()
	{
		return m_network;
	}
	public String getName()
	{
		return m_name;
	}
	public AlgoOD(String name, Network network)
	{
		super();
		m_name = name;
		m_network = network;
	}
	abstract public void Do();
	protected Path getShortestPath(ODPair od_pair)
	{
		Node start_node = od_pair.getStartNode();
		Node end_node = od_pair.getEndNode();
		m_algo_shortest_path.Do(m_network,start_node,end_node);
		return m_algo_shortest_path.getShortestPath(start_node,end_node);
	}
}
