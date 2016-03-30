package trafficNetwork;

import java.util.HashSet;

public class LinkSet extends HashSet<Link>
{
	/**
	 * 是link的集合
	 */
	private static final long serialVersionUID = 1L;

	public LinkSet()
	{
	}

	public Link getLink(String link_name)
	{
		for (Link link : this)
		{
			if( link.getLinkName().equals(link_name))
				return link;
		}
		return null;
	}
	public Link getMinCostLink()
	{
		double min_cost = 0;
		Link min_link = null;
		for (Link link : this)
		{
			if (min_link == null)
			{
				min_link = link;
				min_cost = link.getCost();
			}
			if (link.getCost() < min_cost)
			{
				min_link = link;
				min_cost = link.getCost();
			}
		}
		return min_link;
	}
}
