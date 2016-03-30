package odAsign;

import utility.Output;
import utility.XmlReadConfig;

public class MainOD
{
	public static void main(String[] args)
	{

		XmlReadConfig cfg = XmlReadConfig.BuildCfgByArgs(args);
		if( cfg == null )
			return;
		cfg.Init();
		Output.print(3,"------- BEGIN ------");
		AlgoOD algo_OD = cfg.getAlgoOD();
		algo_OD.Do();
	}
}
