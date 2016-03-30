package bisec;

import utility.*;

public class MainBisec
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		XmlReadConfigBisec cfg = XmlReadConfigBisec.BuildCfgByArgs(args);
		if( cfg == null )
			return;
		cfg.Init();
		Output.print(3,"------- BEGIN ------");
		Bisection bisec = cfg.getBisec();
		
		Output.print(3,"result is :" + bisec.Do());
		
	}

}
