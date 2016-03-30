package shortestPathAlgorithm;

import java.util.*;

import trafficNetwork.*;

/**
 * @author wuxch �����뷨�ǣ����յ�Ľڵ���ǰ���ݣ����ϵļ�¼��С��·�裬ֱ����㡣��ʱ���Եõ�ĳ���յ��Ӧ���������·��
 *
 * ��������ͼ���£�
 *
 *    ----- 1 -----
 *   /             \
 *  0 ----- 2 ------- 4 ----- 5
 *   \                      /
 *    ----- 3 -------------
 *
 * ����Ӧ�÷�Ϊһ�¼������裺
 * 0) ����һ��node pool��;
 * 1) �ҵ��յ���node 5���趨node 5���յ㣨Ҳ�����Լ�������С·��Ϊ0����node 5�ŵ�pool�У�
 * 2) node 5֮ǰ�Ľڵ��� node 4��node 3��
 * 3) ����node 4��node 3��Ӧ�ĵ��յ��·�裬ͬʱ�������ǵ��յ���С·��������µ���һ���ڵ���5��
 *    �Լ���һ��·��(link)�ı�ʶ�����ǵ�������¼��һ���ڵ㲻��Ψһȷ��·����
 * 4) ��node 5��pool��ȡ��������node 4��node 3�Ž�ȥ��
 * 5) ������ǰ���ҵ�node 0��node 2��node 1,������3)��4)�Ĳ��貽�裬���£�ֱ��pool����û���κ�node;
 * 6) ��������ʱÿ���ڵ㶼��¼�˵��յ����С·�裬�Լ���С·������µ���һ���ڵ��·��(link)
 */

public class AlgoCostSpread extends AlgoSP
{
	// ����һ���ṹ��ÿ��node��Ӧ����һ���������˴�node�е���һ��node�Ѿ���Ӧ��·��
	class Pair
	{
		public Node m_next_node;
		public Link m_link;// 2��node֮����ܻ��ж��link
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
		 * ���Ȱ������nodeΪto node��link set�ҵ���Ȼ���link set�е�û��link��from node���ء�
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
		// ��Ҫ���m_min_cost��m_node_pool
		if( m_min_cost.isEmpty() == false )
			m_min_cost.clear();
		if( m_node_pool.isEmpty()== false )
			m_node_pool.clear();

		// ���ȶ�Ӧ��end_node��minimal cost�϶���0
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
	 * �����뷨�ǣ�
	 * ���Ȱ�m_node_pool�е�nodeȫ��ȡ������һ�������º�ɾ����Ȼ����Щnode����һ��node�ڷŵ�m_node_pool�С�
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
	 * ��������Ƚ���Ҫ�����Ǹ���·��ĺ��� ���µķ����ǣ�
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
			// ������nodeû�����ݣ��������е�����cost�����ڵĴ���ô�͸���
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
			// ��һ�ε���
			from_node = to_node;
		}
		return path;
	}

	public void outputShortestPath(Node start_node, Node end_node)
	{
		System.out.print(getShortestPath(start_node,end_node));
	}
}
