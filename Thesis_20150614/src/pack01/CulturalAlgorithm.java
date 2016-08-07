package pack01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class CulturalAlgorithm {
	
	OptimizationFunction f;			//fitness function
	
	ArrayList<double[]> population = new ArrayList();
	int size;
	int numOfParameters;
	
	int max = 100;
	int min = 0;
	
	double[] beliefSpaceS;		//best solution so far
	
	public class BeliefSpaceN{
		
		public double lowerBound = min;
		public double upperBound = max;
		//public double lowerValue = 0;
		//public double upperValue = 0;
		
		public BeliefSpaceN()
		{
			
		}
	}
	
	ArrayList<BeliefSpaceN> beliefSpaceN = new ArrayList();
	
	
	public CulturalAlgorithm(int size, int numOfParameters)
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
				newAgent[j] = (rand.nextDouble() * (max - min)) + min;		//every parameter between 0 and 100
			}
			population.add(newAgent);
		}
		
		beliefSpaceS = new double[numOfParameters];
		for (int i = 0; i < numOfParameters; i++)
		{
			beliefSpaceN.add(new BeliefSpaceN());
		}
	}
	
	public void SetFitness(OptimizationFunction newF)
	{
		f = newF;
	}
	
	public void Update()
	{
		/* - initialize/update belief space
		 * - make second population, influenced mutation from belief space
		 * - randomly take from both population and keep "winners"
		 * - influence of belief space to new population: 0.2 prob. of putting best solution's parameters, additional 0.5 of rand. values within range
		 * */
		
		Collections.sort(population, new GAComparator());
		
		//update belief space
		System.arraycopy( population.get(0), 0, beliefSpaceS, 0, population.get(0).length );
		for (int i = 0; i < numOfParameters; i++)
		{
			if (population.get(0)[i] < population.get(1)[i])
			{
				beliefSpaceN.get(i).lowerBound = population.get(0)[i];
				beliefSpaceN.get(i).upperBound = population.get(1)[i];
			}
			else
			{
				beliefSpaceN.get(i).lowerBound = population.get(1)[i];
				beliefSpaceN.get(i).upperBound = population.get(0)[i];
			}
		}
		for (int j = 2; j < size * 0.1; j++)
		{
			for (int i = 0; i < numOfParameters; i++)
			{
				if (population.get(j)[i] < beliefSpaceN.get(i).lowerBound)
					beliefSpaceN.get(i).lowerBound = population.get(j)[i];
				else if (population.get(j)[i] > beliefSpaceN.get(i).upperBound)
					beliefSpaceN.get(i).upperBound = population.get(j)[i];
			}
		}
		
		
		Random rand = new Random();
		ArrayList<double[]> newPopulation = new ArrayList();
		for (int i = 0; i < size; i++)
		{
			double[] newIndividual = new double[numOfParameters];
			for (int j = 0; j < numOfParameters; j++)
			{
				if (rand.nextInt(10) <= 2)
				{
					newIndividual[j] = beliefSpaceS[j];
				}
				else if (rand.nextInt(10) <= 5)
				{
					newIndividual[j] 
							= (rand.nextDouble() * (beliefSpaceN.get(j).upperBound - beliefSpaceN.get(j).lowerBound)) 
							+ beliefSpaceN.get(j).lowerBound;
				}
				else
				{
					newIndividual[j] = (rand.nextDouble() * (max - min)) + min;
				}
			}
			newPopulation.add(newIndividual);
		}
		
		ArrayList<double[]> combinedList = new ArrayList();
		for (int i = 0; i < size; i++)
		{
			combinedList.add(population.get(i));
		}
		for (int i = 0; i < size; i++)
		{
			combinedList.add(newPopulation.get(i));
		}
		Collections.sort(combinedList, new GAComparator());
		population.clear();
		for (int i = 0; i < size; i++)
		{
			population.add(combinedList.get(i));
		}
		
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
		double [] returnValue = {average,f.CalculateValue(population.get(0))};
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
