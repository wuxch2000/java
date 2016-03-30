package performanceFunction;

public class PF_a_x4_b4_c implements PerformanceFunction
{
	// means
	// cost = a*(x/b)^4+c
	public PF_a_x4_b4_c(double m_a, double m_b, double m_c)
	{
		super();
		this.m_a = m_a;
		this.m_b = m_b;
		this.m_c = m_c;
	}
	private	double m_a;
	private double m_b;
	private double m_c;
	public double calculate(double flow)
	{
		double temp = flow / m_b;
		return m_a * (temp*temp*temp*temp) + m_c;
	}

}
