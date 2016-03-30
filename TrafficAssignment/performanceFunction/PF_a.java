package performanceFunction;

public class PF_a implements PerformanceFunction
{
	private double m_value;

	public PF_a(double value)
	{
		m_value = value;
	}

	public double calculate(double flow)
	{
		return m_value;
	}
}
