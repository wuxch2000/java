package odAsign;

import performanceFunction.*;

public class FWFunction implements PerformanceFunction
{
	private PerformanceFunction m_perf_func;
	private double m_x;
	private double m_y;

	FWFunction(PerformanceFunction perf_func)
	{
		m_perf_func = perf_func;
	}

	void update(double x, double y)
	{
		m_x = x;
		m_y = y;
	}

	@Override
	public double calculate(double alpha)
	{
		// (y-x)perf_func(x+a(y-x)
		double value = (m_y - m_x)* m_perf_func.calculate(m_x + (alpha * (m_y - m_x)));
		return value;
	}
}
