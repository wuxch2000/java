package trafficNetwork;

public class CostSet extends ValueSet
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public String toString()
	{
		String string = new String();
		for (Link link : this.keySet())
		{
			string = string + getLinkString(link) + "\n";
		}
		return string;
	}

	private String getLinkString(Link link)
	{
		return "LINK\"" + link.getLinkName() + "\"[" + link.getFromNode() + ","
				+ link.getToNode() + "]" + "(cost:" + String.format("%2.2f)", this.get(link).doubleValue());
	}
}
