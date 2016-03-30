package shortestPathAlgorithm;

import java.util.*;

import trafficNetwork.*;

/**
 * @author wuxch 基本想法是：从终点的节点向前回溯，不断的记录最小的路阻，直到起点。此时可以得到某个终点对应的所有最短路径
 *
 * 假设网络图如下：
 *
 *    ----- 1 -----
 *   /             \
 *  0 ----- 2 ------- 4 ----- 5
 *   \                      /
 *    ----- 3 -------------
 *
 * 计算应该分为一下几个步骤：
 * 0) 定义一个node pool，;
 * 1) 找到终点是node 5，设定node 5到终点（也就是自己）的最小路阻为0。把node 5放到pool中；
 * 2) node 5之前的节点有 node 4和node 3；
 * 3) 更新node 4和node 3对应的到终点的路阻，同时更新他们到终点最小路阻的条件下的下一个节点是5，
 *    以及下一个路段(link)的标识（考虑到仅仅记录下一个节点不能唯一确定路径）
 * 4) 把node 5从pool中取出来，把node 4和node 3放进去；
 * 5) 继续向前，找到node 0和node 2和node 1,类似于3)和4)的步骤步骤，更新，直到pool里面没有任何node;
 * 6) 结束，此时每个节点都记录了到终点的最小路阻，以及最小路阻情况下的下一个节点和路段(link)
 */

public class AlgoCostSpread extends AlgoSP
{
	// 定义一个结构，每个node对应保存一个。保存了此node中的下一个node已经对应的路阻
	class Pair
	{
		public Node m_next_node;
		public Link m_link;// 2个node之间可能会有多个link
		public double m_cost;

		public Pair(Node m_next_node, Link m_link, double m_cost)
		{
			super();
			this.m_next_node = m_next_node;
			this.m_link = m_link;
			this.m_cost = m_cost;
		}
	}

	private HashMap<Node, Pair> m_min_cost;
	private NodeSet m_node_pool;

	public AlgoCostSpread()
	{
		super("CostSpread");
		m_min_cost = new HashMap<Node, Pair>();
		m_node_pool = new NodeSet();
	}

	private NodeSet getPreviousNodeSet(Network network,Node node)
	{
		/**
		 * 首先把以这个node为to node的link set找到，然后把link set中的没有link的from node返回。
		 */
		LinkSet link_set = network.getLinkSetByToNode(node);
		if (link_set == null || link_set.size() == 0)
		{
			return null;
		}

		NodeSet node_set = new NodeSet();
		for (Link link : link_set)
		{
			node_set.add(link.getFromNode());
		}
		return node_set;
	}

	public boolean Do(Network network,Node start_node, Node end_node)
	{
		// 需要清空m_min_cost和m_node_pool
		if( m_min_cost.isEmpty() == false )
			m_min_cost.clear();
		if( m_node_pool.isEmpty()== false )
			m_node_pool.clear();

		// 首先对应于end_node的minimal cost肯定是0
		m_min_cost.put(end_node, new Pair(null, null, 0));
		// m_node_pool.addAll(getPreviousNodeSet(m_end_node));
		m_node_pool.add(end_node);
		while (m_node_pool.isEmpty() == false)
		{
			updateMinCostForAllNodes(network);
		}

		return true;
	}

	/**
	 * 基本想法是：
	 * 首先把m_node_pool中的node全部取出来，一个个更新后删除。然后这些node的上一个node在放到m_node_pool中。
	 *
	 * @return
	 */
	private void updateMinCostForAllNodes(Network network)
	{
		NodeSet new_node_pool = new NodeSet();
		for(Node node : m_node_pool)
		{
			NodeSet previous_node_set = getPreviousNodeSet(network,node);
			if (previous_node_set != null)
			{
				updateNodeMinCost(network,previous_node_set, node);
				new_node_pool.addAll(previous_node_set);
			}
		}
		m_node_pool.clear();
		m_node_pool.addAll(new_node_pool);
		return;
	}

	/**
	 * 这个函数比较重要，就是更新路阻的函数 更新的方法是：
	 *
	 * @param node
	 */
	private void updateNodeMinCost(Network network,NodeSet previous_nodes, Node node)
	{
		for (Node previous_node : previous_nodes)
		{
			LinkSet link_set = network.getLinkSetByNodePair(previous_node,
                                                            node);
			Link link = link_set.getMinCostLink();
			// 如果这个node没有数据，或者已有的数据cost比现在的大，那么就更新
			if (m_min_cost.containsKey(previous_node) == false
                || m_min_cost.get(previous_node).m_cost > link.getCost())
			{
				Pair value = new Pair(node, link, link.getCost());
				m_min_cost.put(previous_node, value);
			}
		}
		return;
	}

	public Path getShortestPath(Node start_node, Node end_node)
	{
		Path path = new Path();
		Node from_node = start_node;
		Node to_node = null;
		while (true)
		{
			Pair pair = m_min_cost.get(from_node);
			to_node = pair.m_next_node;
			Link link = pair.m_link;
			path.addLast(link);
			if (to_node == end_node)
			{

				break;
			}
			// 下一次迭代
			from_node = to_node;
		}
		return path;
	}

	public void outputShortestPath(Node start_node, Node end_node)
	{
		System.out.print(getShortestPath(start_node,end_node));
	}
}
