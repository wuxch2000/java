package odAsign;

import trafficNetwork.*;
import utility.Output;
import shortestPathAlgorithm.*;

public abstract class AlgoSingleOD extends AlgoOD
{
	protected FlowSet 	m_flow_set_x; // 对应于links的flow向量；
	protected FlowSet 	m_flow_set_x_old; // 对应于links的flow向量；
	protected FlowSet 	m_flow_set_y; // 对应于links的flow向量;
	private ODPair		m_od_pair;

	public AlgoSingleOD(String name, Network network, AlgoSP algo_sp ,ODPair od_pair)
	{
		super(name,network);
		m_algo_shortest_path = algo_sp;
		m_od_pair = od_pair;
	}

	public double GetFlow()
	{
		return m_od_pair.getFlow();
	}

	public void outputResult()
	{
		Output.print(3,String.format("------- OVER(OD Algo:%s,SP Algo;%s) --------", this.getName(),m_algo_shortest_path.getName()));
		Output.print(3,"network is:\n" + getNetwork());

	}
	protected Path getShortestPath()
	{
		return getShortestPath(m_od_pair);
	}
}
