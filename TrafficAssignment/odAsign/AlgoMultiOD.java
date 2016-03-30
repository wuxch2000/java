package odAsign;

import java.util.*;
import trafficNetwork.*;
import utility.Output;
import shortestPathAlgorithm.*;

public abstract class AlgoMultiOD extends AlgoOD
{
	protected HashMap<ODPair,FlowSet> 	m_flow_set_x; // 对应于links的flow向量；
	protected HashMap<ODPair,FlowSet> 	m_flow_set_x_old; // 对应于links的flow向量；
	protected HashMap<ODPair,FlowSet> 	m_flow_set_y; // 对应于links的flow向量；
	protected HashSet<ODPair>			m_od_pair;

	protected FlowSet m_flow_set_x_all_old;
	protected FlowSet m_flow_set_x_all;
	protected FlowSet m_flow_set_y_all;

	public AlgoMultiOD(String name,Network network, AlgoSP algo_sp ,HashSet<ODPair> od_pair)
	{
		super(name,network);
		m_algo_shortest_path = algo_sp;
		m_flow_set_x = new HashMap<ODPair,FlowSet>();
		m_flow_set_x_old = new HashMap<ODPair,FlowSet>();
		m_flow_set_y = new HashMap<ODPair,FlowSet>();
		m_flow_set_x_all_old = new FlowSet();
		m_flow_set_x_all = new FlowSet();
		m_flow_set_y_all = new FlowSet();
		m_od_pair = od_pair;
	}

	public void outputResult()
	{
		Output.print(3,String.format("------- OVER(OD Algo:%s,SP Algo;%s) --------", this.getName(),m_algo_shortest_path.getName()));
		Output.print(3,"network is:\n" + getNetwork());
	}
}
