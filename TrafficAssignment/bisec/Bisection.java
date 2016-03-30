package bisec;

import performanceFunction.*;

public class Bisection
{
	private PerformanceFunction m_function;
	private double m_delta; // ¾«¶È
	private double m_left_edge;
	private double m_right_edge;

	public Bisection(PerformanceFunction function, double delta, double left_edge,
			double right_edge)
	{
		m_function = function;
		m_delta = delta;
		m_left_edge = left_edge;
		m_right_edge = right_edge;
	}

	public double Do()
	{
		double left = m_left_edge;
		double right = m_right_edge;

		double left_value = m_function.calculate(left);
		double mid = (left + right) / 2;
		double mid_value = m_function.calculate(mid);

		while (IsOver(mid_value, left, right) == false)
		{
			if ((Sgn(left_value) * Sgn(mid_value)) == -1)
			{
				right = mid;
				mid = (left + right) / 2;
				mid_value = m_function.calculate(mid);
			} else
			{
				left = mid;
				mid = (left + right) / 2;
				mid_value = m_function.calculate(mid);
			}
		}
		return mid;
	}

	private int Sgn(double v)
	{
		if (v > 0)
			return 1;
		else if (v < 0)
			return -1;
		else
			return 0;
	}

	private boolean IsOver(double mid_value, double left, double right)
	{
		if ((right - left) < m_delta)
		{
			return true;
		}
		else
			return false;
	}

}
