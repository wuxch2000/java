package odAsign;

import shortestPathAlgorithm.*;
import trafficNetwork.*;
import utility.Output;

public class AlgoSingleMSA extends AlgoSingleOD
{
	private int			N;
	public AlgoSingleMSA(Network network,AlgoSP algo, int n, ODPair start_end_node)
	{
		super("msa(single)",network,algo,start_end_node);
		m_algo_shortest_path = algo;
		N = n;
	}

	private void update_set_x(double alpha)
	{
		for( Link link : m_flow_set_x.keySet() )
		{
			double old_x = m_flow_set_x.get(link);
			double old_y = m_flow_set_y.get(link);
			double new_x = old_x + alpha * (old_y - old_x);
			m_flow_set_x_old.put(link,old_x);
			m_flow_set_x.put(link,new_x);
		}
	}

	public void Do()
	{
		Path shortest_path_x;
		Path shortest_path_y;
		Network network = getNetwork();

		// 首先设定network的流量为0
		network.load(0);
		m_flow_set_x_old = network.getFlowSet();
		Output.print(3,"network is:\n" + network);
		// 计算最短的路径
		shortest_path_x = getShortestPath();
		Output.print(3,"shortest_path_x is:\n" + shortest_path_x);
		// 给这个最短的路径上加载flow;
		network.setPathFlow(shortest_path_x, GetFlow());
		m_flow_set_x = network.getFlowSet();
		Output.print(3,"m_flow_set_x is:\n" + m_flow_set_x);

		// 再算一次最短的路径
		shortest_path_y = getShortestPath();
		Output.print(3,"shortest_path_y is:\n" + shortest_path_y);
		// 给这个最短的路径上加载flow;
		network.load(0);
		network.setPathFlow(shortest_path_y, GetFlow());
		m_flow_set_y = network.getFlowSet();
		Output.print(3,"m_flow_set_y is:\n" + m_flow_set_y);

		for(int i = 0; i < N ; i++)
		{
			double alpha = 1/((double)(i+2));
			Output.print(3,String.format("alpha is:%.10f(i=%d)", alpha,i));
			update_set_x(alpha);

			// 使用新的vector_x更新整个网络的flow
			network.load(0);
			network.load(m_flow_set_x);
			Output.print(3,"new m_flow_set_x is:\n" + m_flow_set_x);

			// 计算最短的路径
			shortest_path_y = getShortestPath();
			Output.print(3,"shortest_path_x is:\n" + shortest_path_y);

			// 给这个最短的路径上加载flow;
			network.setPathFlow(shortest_path_y, GetFlow());
			m_flow_set_y = network.getFlowSet();
			Output.print(3,"m_flow_set_y is:\n" + m_flow_set_y);

		}

		Output.print(3,"m_flow_set_x is:\n" + m_flow_set_x.toString());
		network.load(0);
		network.load(m_flow_set_x);
		super.outputResult();
	}

}
