package trafficNetwork;

public class FlowSet extends ValueSet
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public String toString()
	{
		String str = new String();
		for (Link link : this.keySet())
		{
			str = str + getLinkString(link) + "\n";
		}
		return str;
	}

	private String getLinkString(Link link)
	{
		return "LINK\"" + link.getLinkName() + "\"[" + link.getFromNode() + ","
				+ link.getToNode() + "]" + "(flow:" + String.format("%2.2f)", this.get(link).doubleValue());
	}
}
