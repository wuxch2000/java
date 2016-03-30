package utility;

import java.io.FileOutputStream;


public class OutputFileCfg extends OutputThresholdCfg
{
	private String m_file_name;
	public OutputFileCfg(boolean m_should_output, int m_threshold, String file_name)
	{
		super(m_should_output, m_threshold);
		m_file_name = file_name;

	}
	public void print(int threshold, String str)
	{
		if(super.OK(threshold))
		{
			try
			{
				FileOutputStream  out = new FileOutputStream(m_file_name, true);
				String out_str = new String(str + "\n");
				out.write(out_str.getBytes());
				out.close();
			} catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
