package trafficNetwork;

import java.util.HashSet;

public class NodeSet extends HashSet<Node>
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public Node getNode(int node_id)
	{
		for (Node node : this)
		{
			if (node.getNodeId() == node_id)
				return node;
		}
		return null;
	}

}
