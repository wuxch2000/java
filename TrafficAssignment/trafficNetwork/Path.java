package trafficNetwork;

import java.util.*;

public class Path extends LinkedList<Link>
{
	private static final long serialVersionUID = 1L;

	public Link removeAndGetLast()
	{
		Link link = (Link) super.removeLast();
		return link;
	}

	public String toString()
	{
		String string = new String();
		for (Link link : this)
		{
			string = string + link.toString() + "\n";
		}
		return string;
	}

}
