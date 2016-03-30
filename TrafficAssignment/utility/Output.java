package utility;

public class Output
{
	OutputConsoleCfg	m_console;
	OutputFileCfg		m_file;
	private static Output g_output = null;
	public static void print(int threshold,String str)
	{
		Output output = g_output;
		if( output == null )
			return;
		output.m_console.print(threshold, str);
		output.m_file.print(threshold, str);
		return;
	}
	public Output(OutputConsoleCfg m_console, OutputFileCfg m_file)
	{
		super();
		this.m_console = m_console;
		this.m_file = m_file;
	}
	public static Output buildOutput(OutputConsoleCfg console, OutputFileCfg file)
	{
		g_output = new Output(console,file);
		return g_output;
	}
}
