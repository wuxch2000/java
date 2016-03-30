package odAsign;

import bisec.*;
import shortestPathAlgorithm.*;
import trafficNetwork.*;
import utility.Output;

public class AlgoSingleFW extends AlgoSingleOD
{
	private FWFunctionSet m_FW_func;

	private double E;
	private double m_bisec_delta;

	public AlgoSingleFW(Network network,AlgoSP algo, double e,double bisec_delta,ODPair od_pair)
	{
		super("fw(single)",network,algo,od_pair);
		E = e;
		m_FW_func = new FWFunctionSet(network);
		m_bisec_delta = bisec_delta;
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

		Bisection bisec = new Bisection(m_FW_func, m_bisec_delta, 0, 1);

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

		while (m_FW_func.IsOver(m_flow_set_x,m_flow_set_x_old,E) == false)
		{
			// 更新FW函数
			m_FW_func.update(m_flow_set_x, m_flow_set_y);

			double alpha = bisec.Do();
			Output.print(3,"alpha is:" + String.format("%.10f", alpha));
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
