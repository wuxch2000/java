package trafficNetwork;

import java.util.HashMap;

public class ValueSet extends HashMap<Link,Double>
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void add(ValueSet value_set)
	{
		for (Link link : value_set.keySet())
		{
			Double new_value;
			if(this.containsKey(link) == false)
			{
				new_value = value_set.get(link);
			}
			else
			{
				new_value = Double.valueOf(this.get(link).doubleValue() + value_set.get(link).doubleValue());
			}
			this.put(link, new_value);
		}
	}
	public void clear()
	{
		for( Link link : this.keySet() )
		{
			this.put(link, 0.0);
		}
	}
}
