package wordLib;

import java.util.*;
import java.io.*;

public class WordLib
{

	public WordLib()
	{
	}

	public static String getWord()
	{
		ArrayList<String> source = getSource();
		if( source.isEmpty() )
		{
			return "Run Out!";
		}
		Integer i = rand.nextInt(source.size());
		String prompt = source.get(i);
		source.remove(prompt);
		return prompt;
	}
//	public static int getPercent()
//	{
//		ArrayList<String> source = getSource();
//		int current_size = source.size();
//		return 100 - (current_size * 100 / source_size);
//	}
	private static ArrayList<String> word_source;
	private static int source_size;
	private static Random rand = new Random();
	private static final String source_file_name = "wg.txt";
	
	public static int getInitSize()
	{
		getSource();
		return source_size;
	}
	public static int getCurrentSize()
	{
		return getSource().size();
	}
	
	private static ArrayList<String> getSource()
	{
		if( word_source == null)
		{
			word_source = new ArrayList<String>();
			initSource();
			source_size = word_source.size();
		}
		return word_source;
	}
	private static void initSource()
	{
		try
		{
			File f = new File(source_file_name);
			InputStreamReader read;
			read = new InputStreamReader (new FileInputStream(f),"UTF-8");
			BufferedReader reader=new BufferedReader(read);
			
			String line;
			while ((line = reader.readLine()) != null) 
			{
				word_source.add(line);
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		
	}
}
