package shortestPathAlgorithm;

import utility.Output;
import utility.XmlReadConfig;
import java.util.*;

import odAsign.ODPair;

public class MainShortestPath
{
	public static void main(String[] args)
	{


		XmlReadConfig cfg = XmlReadConfig.BuildCfgByArgs(args);
		if( cfg == null )
			return;

		cfg.Init();
		Output.print(3,"BEGIN ---");
		AlgoSP algo = cfg.getAlgo();
		HashSet<ODPair> start_end_node_pair = cfg.getStartEndPair();
		for(ODPair node_pair : start_end_node_pair)
		{
			algo.Do(cfg.getNetwork(),node_pair.getStartNode(),node_pair.getEndNode());
			algo.outputShortestPath(node_pair.getStartNode(),node_pair.getEndNode());
		}
		Output.print(3,"End ---");
	}
}
