package utility;

public class OutputConsoleCfg extends OutputThresholdCfg
{

	public OutputConsoleCfg(boolean m_should_output, int m_threshold)
	{
		super(m_should_output, m_threshold);
	}

	public void print(int threshold, String str)
	{
		if(super.OK(threshold))
		{
			System.out.println(str);
		}
	}

}
