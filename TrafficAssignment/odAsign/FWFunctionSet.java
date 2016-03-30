package odAsign;

import java.util.*;
import trafficNetwork.*;
import performanceFunction.*;

public class FWFunctionSet extends HashMap<Link,FWFunction> implements PerformanceFunction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	FWFunctionSet(Network network)
	{
		super();
		for(Link link : network.getLinkSet())
		{
			put(link,new FWFunction(link.getPerformanceFunction()));
		}
	}

	public double calculate(double alpha)
	{
		double value = 0;
		for (FWFunction func : this.values())
		{
			value += func.calculate(alpha);
		}
		return value;
	}
	public void update(ValueSet x, ValueSet y)
	{
		for ( Link link : this.keySet())
		{
			get(link).update(x.get(link),y.get(link));
		}
	}
	
	
	public boolean IsOver(ValueSet x, ValueSet x_old, double e)
	{
		double numerator = 0;
		double denominator = 0;

		for ( Link link : x.keySet() )
		{
			double temp = x.get(link) - x_old.get(link);
			numerator += temp * temp;
			denominator += x_old.get(link);
		}
		numerator = Math.sqrt(numerator);

		if ((numerator / denominator) < e)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
