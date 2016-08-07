package pack01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GeneticAlgorithm {
	
	OptimizationFunction f;			//fitness function
	
	ArrayList<double[]> population = new ArrayList();
	int size;
	int numOfParameters;
	
	int max = 100;
	int min = 0;
	
	
	public GeneticAlgorithm(int size, int numOfParameters)
	{
		this.size = size;
		this.numOfParameters = numOfParameters;
		Initialize();
	}
	
	public void Initialize()
	{
		//initialize population
		
		Random rand = new Random();
		
		for (int i = 0; i < size; i++)
		{
			double [] newAgent = new double[numOfParameters];
			for (int j = 0; j < numOfParameters; j++)
			{
				newAgent[j] = rand.nextDouble() * (max - min);		//every parameter between 0 and 100
			}
			population.add(newAgent);
		}
	}
	
	public void SetFitness(OptimizationFunction newF)
	{
		f = newF;
	}
	
	public void Update()
	{
		//sort
		Collections.sort(population, new GAComparator());
		
		//choose top n for "selection"
		ArrayList<double[]> newPopulation = new ArrayList();
		for (int i = 0; i < size * 0.2; i++)
		{
			newPopulation.add(population.get(i));
		}
		
		Random rand = new Random();
		
		//combine top n for "reproduction"
		for (int i = (int)(size*0.2); i < size; i++)
		{
			double [] newAgent = new double[numOfParameters];
			int parent1 = rand.nextInt((int)(size*0.2));
			int parent2 = rand.nextInt((int)(size*0.2));
			for (int j = 0; j < numOfParameters; j++)
			{
				//pick parameters
				int randChoice = rand.nextInt(3);
				if (randChoice == 0)
					newAgent[j] = newPopulation.get(parent1)[j];
				else if (randChoice == 1)
					newAgent[j] = newPopulation.get(parent2)[j];
				else
					newAgent[j] = (newPopulation.get(parent1)[j] + newPopulation.get(parent2)[j])*0.5;
			}
			newPopulation.add(newAgent);
		}
		
		//modify some for "mutation"
		for (int i = (int)(size*0.2); i < size; i++)
		{
			if (rand.nextDouble() < 0.5)
			{
				for (int j = 0; j < numOfParameters; j++)
				{
					newPopulation.get(i)[j] += ((rand.nextDouble() * 10) - 5);
				}
			}
		}
		
		//fix to ensure all parameters are between 0 and 100
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < numOfParameters; j++)
			{
				newPopulation.get(i)[j] = Math.max(min, newPopulation.get(i)[j]);
				newPopulation.get(i)[j] = Math.min(max, newPopulation.get(i)[j]);
			}
		}
		
		//replace old population with new one
		population.clear();
		population = newPopulation;
	}
	
	public double[] PrintTopAgent()
	{
		Collections.sort(population, new GAComparator());
		
		double average = 0;
		for (int i = 0; i < size; i++)
		{
			average += f.CalculateValue(population.get(i));
		}
		average = average / (double)(size);
		
		//System.out.println(f.CalculateValue(population.get(0)) + "\t" + average);
		double [] returnValue = {average , f.CalculateValue(population.get(0))};
		return returnValue;
	}
	
	class GAComparator implements Comparator<double[]>{
		public int compare(double[] a, double[] b)
		{
			double aValue = f.CalculateValue(a);
			double bValue = f.CalculateValue(b);
			if (aValue > bValue)
				return -1;
			else if (aValue < bValue)
				return 1;
			else
				return 0;
		}
	}

}
