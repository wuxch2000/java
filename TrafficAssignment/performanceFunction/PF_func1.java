package performanceFunction;

import utility.Output;

public class PF_func1 implements PerformanceFunction
{
	public PF_func1(double m_a,
                    double m_b,
                    double m_c,
                    double m_d,
                    double m_e,
                    double m_m1,
                    double m_m2
        )
	{
		super();
		this.m_a = m_a;
		this.m_b = m_b;
        this.m_c = m_c;
        this.m_d = m_d;
        this.m_e = m_e;
        this.m_m1 = m_m1;
        this.m_m2 = m_m2;
	}
	private	double m_a;
	private double m_b;
    private double m_d;
    private double m_c;
    private double m_e;
    private double m_m1;
    private double m_m2;

	public double calculate(double x)
	{
        // <!-- func1 means: ax + b + c*(ln((d/x)+e)) - m1 + m2 -->
		double result;
		result = m_a*(x) + m_b + (m_c * Math.log((m_d/x) + m_e)) - m_m1 + m_m2 ;
		Output.print(3,"calculating... x=" + x + ",result=" + result);
		return result;
     }
}
