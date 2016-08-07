package pwar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChangeFile {
	
	public static void main (String args[]) throws FileNotFoundException
	{
		int whichLine = 16;
		
		PrintWriter writer = new PrintWriter("data_" + whichLine +  ".txt");
		
		BufferedReader br = new BufferedReader(new FileReader("data.txt"));
		String line;
		int counter = 0;
		
		try {
			while ((line = br.readLine()) != null)
			{
				counter++;
				if (counter == whichLine + 3)
				{
					writer.println(line);
				}
				if (counter >= 40)
					counter = 0;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer.println("");

		
		writer.close();
		
		System.out.println("Done writing new file.");
		
	}

}
