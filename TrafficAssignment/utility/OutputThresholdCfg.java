package utility;

public abstract class OutputThresholdCfg
{
	private int m_threshold;
	private boolean m_should_output;
	public OutputThresholdCfg(boolean m_should_output, int m_threshold)
	{
		super();
		this.m_should_output = m_should_output;
		this.m_threshold = m_threshold;
	}
	public boolean OK(int threshold)
	{
		return (m_should_output && (threshold <= m_threshold));
	}
	public abstract void print(int threadhold, String str);
}
