package performanceFunction;

public class PF_x_a implements PerformanceFunction
{
	private double m_value;

	public PF_x_a(double value)
	{
		m_value = value;
	}

	public double calculate(double flow)
	{
		return m_value + flow;
	}
}
