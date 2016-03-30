package odAsign;

import java.util.HashSet;

import shortestPathAlgorithm.*;
import trafficNetwork.*;

public class AlgoMultiMSA extends AlgoMultiOD
{
//	private int			N;
	public AlgoMultiMSA(Network network,AlgoSP algo_sp, int n, HashSet<ODPair> start_end_node)
	{
		super("msa(multi)",network,algo_sp,start_end_node);
		m_algo_shortest_path = algo_sp;
//		N = n;
	}

	public void Do()
	{

	}

}
