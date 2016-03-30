package performanceFunction;

public class PF_ax_b implements PerformanceFunction
{
	public PF_ax_b(double m_a, double m_b)
	{
		super();
		this.m_a = m_a;
		this.m_b = m_b;
	}
	private	double m_a;
	private double m_b;
	public double calculate(double flow)
	{
		return (m_a * flow) + m_b;
	}

}
