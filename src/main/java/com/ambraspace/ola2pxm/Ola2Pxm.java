package com.ambraspace.ola2pxm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Ola2Pxm
{

	public static void main(String[] args)
	{

		if (args.length != 2)
		{
			System.out.println("Usage: java -jar <input_file.ola> <output_file.csv>");
		} else {
			processFiles(args[0], args[1]);
		}

	}


	private static void processFiles(String inputFileName, String outputFileName)
	{

		File inputFile = new File(inputFileName);

		if (!inputFile.exists())
		{
			System.err.println("Input file can not be found!");
			return;
		}

		File outputFile = new File(outputFileName);

		try (
				BufferedReader input = new BufferedReader(new FileReader(inputFile));
				PrintWriter output = new PrintWriter(outputFile);
		) {
			String line1 = input.readLine();
			if (!line1.matches("OLA Show"))
			{
				throw new RuntimeException("File format error!");
			}
			String line2 = input.readLine();
			String[] channels;
			Integer universe = null;
			while (line2 != null) {
				if (universe == null)
				{
					universe = Integer.parseInt(line2.substring(0, line2.indexOf(" ")));
					output.println("" + universe + (", " + universe).repeat(511));
					output.print("1");
					for (int i = 2; i <= 512; i ++)
					{
						output.print(", " + i);
					}
					output.println("");
				}
				line2 = line2.substring(line2.indexOf(" ") + 1);
				channels = line2.split(",");
				output.print(channels[0]);
				for (int i = 1; i < 512; i ++)
				{
					output.print(", " + channels[i]);
				}
				output.println("");
				line1 = input.readLine();
				line2 = input.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}


}

