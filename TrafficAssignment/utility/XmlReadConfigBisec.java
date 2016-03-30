package utility;

import java.io.*;

import org.jdom.*;
import org.jdom.input.*;

import performanceFunction.*;
import bisec.*;

public class XmlReadConfigBisec
{

	private Document	m_algo_doc;
	private Document	m_output_doc;
	private Bisection	m_bisec;
	public XmlReadConfigBisec(String algo_file_name, String output_file_name)
	{
		super();
		SAXBuilder algo_builder = new SAXBuilder();
		SAXBuilder output_builder = new SAXBuilder();
		try
		{
			m_algo_doc = algo_builder.build(new File(algo_file_name));
			m_output_doc = output_builder.build(new File(output_file_name));
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	public Bisection getBisec()
	{
		return m_bisec;
	}
	
	public static XmlReadConfigBisec BuildCfgByArgs(String[] args)
	{
		String algo_file = null;
		String output_file = null;
		for (int i=0; args != null && i<args.length; i++)
		{
			if(args[i].equalsIgnoreCase("-a"))
			{
				i++;
				algo_file = new String(args[i]);
			}
			if(args[i].equalsIgnoreCase("-o"))
			{
				i++;
				output_file = new String(args[i]);
			}
		}
		if( algo_file == null || output_file == null)
		{
			System.err.println("Usage:java -jar bisec.jar -a algo.xml -o output.xml");
			return null;
		}
		else
			return new XmlReadConfigBisec(algo_file,output_file);
	}



	public void Init()
	{

		try
		{
			readOutput();
			readAlgoBisec();
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		return;
	}
	
	private void readOutput()
	{
		try
		{
			Element element_root = m_output_doc.getRootElement();


			Element element_console = element_root.getChild("console");
			String output_to_console_str = element_console.getAttributeValue("output");
			boolean output_to_console = false;
			if(output_to_console_str.equalsIgnoreCase("yes"))
			{
				output_to_console = true;
			}
			int console_threshold = element_console.getAttribute("threshold").getIntValue();
			OutputConsoleCfg console = new OutputConsoleCfg(output_to_console,console_threshold);


			Element element_file = element_root.getChild("file");
			String output_to_file_str = element_file.getAttributeValue("output");
			boolean output_to_file = false;
			if(output_to_file_str.equalsIgnoreCase("yes"))
			{
				output_to_file = true;
			}
			int file_threshold = element_file.getAttribute("threshold").getIntValue();
            String output_to_file_name = element_file.getAttributeValue("file_name");
            OutputFileCfg file = new OutputFileCfg(output_to_file,file_threshold,output_to_file_name);

            Output.buildOutput(console, file);


		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	private void readAlgoBisec()
	{
		try
		{
			Element element_root = m_algo_doc.getRootElement();
			Element element_algo_bisec_only = element_root.getChild("bisec_only");

			double delta = element_algo_bisec_only.getChild("delta").getAttribute("value").getDoubleValue();
			
			String perf_func_type;
			PerformanceFunction perf_func;
			
			perf_func_type = element_algo_bisec_only.getChild("perf-func").getAttributeValue("type");
			if(perf_func_type.equalsIgnoreCase("ax_b"))
			{
				double perf_func_a = element_algo_bisec_only.getChild("perf-func").getAttribute("a").getDoubleValue();
				double perf_func_b = element_algo_bisec_only.getChild("perf-func").getAttribute("b").getDoubleValue();
				perf_func = new PF_ax_b(perf_func_a,perf_func_b);
			}
			else if(perf_func_type.equalsIgnoreCase("func1"))
			{
				double a = element_algo_bisec_only.getChild("perf-func").getAttribute("a").getDoubleValue();
				double b = element_algo_bisec_only.getChild("perf-func").getAttribute("b").getDoubleValue();
				double c = element_algo_bisec_only.getChild("perf-func").getAttribute("c").getDoubleValue();
				double d = element_algo_bisec_only.getChild("perf-func").getAttribute("d").getDoubleValue();
				double e = element_algo_bisec_only.getChild("perf-func").getAttribute("e").getDoubleValue();
				double m1 = element_algo_bisec_only.getChild("perf-func").getAttribute("m1").getDoubleValue();
				double m2 = element_algo_bisec_only.getChild("perf-func").getAttribute("m2").getDoubleValue();
				
				perf_func = new PF_func1(a,b,c,d,e,m1,m2);
			}
			else
			{
				return;
			}
			
			double point_start = element_algo_bisec_only.getChild("point_start").getAttribute("value").getDoubleValue();
			double point_end = element_algo_bisec_only.getChild("point_end").getAttribute("value").getDoubleValue();
			
			m_bisec = new Bisection(perf_func,delta,point_start,point_end);
			

		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

}
