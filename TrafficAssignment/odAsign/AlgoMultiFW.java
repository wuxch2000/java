package odAsign;

import java.util.*;
import bisec.Bisection;
import shortestPathAlgorithm.*;
import trafficNetwork.*;
import utility.Output;

public class AlgoMultiFW extends AlgoMultiOD
{
	private FWFunctionSet m_FW_func;
	private double E;
	private double m_bisec_delta;

	public AlgoMultiFW(Network network,AlgoSP algo, double e, double bisec_delta, HashSet<ODPair> od_pair)
	{
		super("fw(multi)",network,algo,od_pair);
		E = e;
		m_FW_func = new FWFunctionSet(network);
		m_bisec_delta = bisec_delta;
	}

	private void update_set_x(double alpha)
	{
		for( Link link : m_flow_set_x_all.keySet() )
		{
			double old_x = m_flow_set_x_all.get(link);
			double old_y = m_flow_set_y_all.get(link);
			double new_x = old_x + alpha * (old_y - old_x);
			m_flow_set_x_all_old.put(link,old_x);
			m_flow_set_x_all.put(link,new_x);
		}
		Output.print(3,"update_set_x using alpha");
		Output.print(3,"m_flow_set_x_all_old is:\n" + m_flow_set_x_all_old);
		Output.print(3,"m_flow_set_x_all is:\n" + m_flow_set_x_all);
	}

//	1  零流加载，计算各路段的零流阻抗；
//	2 计算ODn的最短路径；
//	3 把ODn的流量加载到ODn的最短路上，有Xn，计算Xall=SIGMA(Xn);
//	4 根据Xall更新各段路阻；
//	5 计算ODn的最短路径
//	6 把ODn的流量加载到最短路上，有Yn，计算Yall=SIGMA(Yn)
//	7 使用二分法计算alpha
//	8 计算新的Xall-new=Xall + alpha*(Yall-Xall)
//	9 return to 4
	public void Do()
	{
		Network network = getNetwork();
		HashMap<ODPair,Path> shortest_path_x = new HashMap<ODPair,Path>();
		HashMap<ODPair,Path> shortest_path_y = new HashMap<ODPair,Path>();

		Bisection bisec = new Bisection(m_FW_func, m_bisec_delta, 0, 1);

//		1  零流加载，计算各路段的零流阻抗；
		network.load(0);
		Output.print(3,"network is:\n" + network);
		//初始化m_flow_set_x_all_old
		m_flow_set_x_all_old.clear();
		for (ODPair od_pair : m_od_pair)
		{
			FlowSet	flow_set = network.getFlowSet();
			m_flow_set_x_old.put(od_pair, flow_set);
			m_flow_set_x_all_old.add(flow_set);
		}
		Output.print(3,"m_flow_set_x_all_old is:\n" + m_flow_set_x_all_old);


//		2 计算ODn的最短路径；
		for (ODPair od_pair : m_od_pair)
		{
			Path shortest_path = getShortestPath(od_pair);
			shortest_path_x.put(od_pair, shortest_path);
			Output.print(3,od_pair + " shortest path x is:\n" + shortest_path);
		}
//		3 把ODn的流量加载到ODn的最短路上，有Xn，计算Xall=SIGMA(Xn);
		m_flow_set_x_all.clear();
		for (ODPair od_pair : m_od_pair)
		{
			FlowSet	flow_set;
			network.setPathFlow(shortest_path_x.get(od_pair), od_pair.getFlow());
			flow_set = network.getFlowSet();
			m_flow_set_x.put(od_pair,flow_set);
			Output.print(3,od_pair + " m_flow_set_x is:\n" + flow_set);
//			计算Xall=SIGMA(Xn);
			m_flow_set_x_all.add(flow_set);
		}
		Output.print(3,"m_flow_set_x_all is:\n" + m_flow_set_x_all);

//		4 根据Xall更新各段路阻；
		network.setFlowSet(m_flow_set_x_all);
		Output.print(3,"loading new m_flow_set_x_all, network is:\n" + network);

//		5 计算ODn的最短路径
		for (ODPair od_pair : m_od_pair)
		{
			Path shortest_path = getShortestPath(od_pair);
			shortest_path_y.put(od_pair, shortest_path);
			Output.print(3,od_pair + " shortest path y is:\n" + shortest_path);
		}
//		6 把ODn的流量加载到最短路上，有Yn，计算Yall=SIGMA(Yn)
		m_flow_set_y_all.clear();
		for (ODPair od_pair : m_od_pair)
		{
			FlowSet	flow_set;
			network.setPathFlow(shortest_path_y.get(od_pair), od_pair.getFlow());
			flow_set = network.getFlowSet();
			m_flow_set_y.put(od_pair,flow_set);
			Output.print(3,od_pair + " m_flow_set_y is:\n" + flow_set);
//			计算Yall=SIGMA(Yn);
			m_flow_set_y_all.add(flow_set);
		}
		Output.print(3,"m_flow_set_y_all is:\n" + m_flow_set_y_all);

		while (m_FW_func.IsOver(m_flow_set_x_all,m_flow_set_x_all_old,E) == false)
		{
			// 更新FW函数
			m_FW_func.update(m_flow_set_x_all, m_flow_set_y_all);
//			7 使用二分法计算alpha
			double alpha = bisec.Do();
			Output.print(3,"alpha is:" + String.format("%.10f", alpha));
			update_set_x(alpha);
//			4 根据Xall更新各段路阻；
			network.setFlowSet(m_flow_set_x_all);
			Output.print(3,"loading new m_flow_set_x_all, network is:\n" + network);

//			5 计算ODn的最短路径
			for (ODPair od_pair : m_od_pair)
			{
				Path shortest_path = getShortestPath(od_pair);
				shortest_path_y.put(od_pair, shortest_path);
				Output.print(3,od_pair + " shortest path y is:\n" + shortest_path);
			}
//			6 把ODn的流量加载到最短路上，有Yn，计算Yall=SIGMA(Yn)
			m_flow_set_y_all.clear();
			for (ODPair od_pair : m_od_pair)
			{
				FlowSet	flow_set;
				network.setPathFlow(shortest_path_y.get(od_pair), od_pair.getFlow());
				flow_set = network.getFlowSet();
				m_flow_set_y.put(od_pair,flow_set);
				Output.print(3,od_pair + " m_flow_set_y is:\n" + flow_set);
//				计算Yall=SIGMA(Yn);
				m_flow_set_y_all.add(flow_set);
			}
			Output.print(3,"m_flow_set_y_all is:\n" + m_flow_set_y_all);
		}

		Output.print(3,"m_flow_set_x_all is:\n" + m_flow_set_x_all);
		network.setFlowSet(m_flow_set_x_all);
		Output.print(3,"loading new m_flow_set_x_all, network is:\n" + network);
		super.outputResult();
	}


}
