package pack01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class HeterogeneousAlgorithm {
	
	public class Pair{
		
		public double[] p1;
		public int p2;
		
		public Pair(double[] P1, int P2)
		{
			p1 = P1;
			p2 = P2;
		}
		
		public Pair (Pair p)
		{
			p1 = new double[p.p1.length];
			System.arraycopy( p.p1, 0, this.p1, 0, p.p1.length );
			this.p2 = p.p2;
		}
	}
	
	OptimizationFunction f;			//fitness function
	
	ArrayList<Pair> population = new ArrayList();
	int size;
	int numOfParameters;
	
	int max = 100;
	int min = 0;
	
	double numOfPopulations = 0.4;		// * numOfParameters for total number of populations
	
	double [][] beliefSpace;
	
	int cycle = 0;
	double localSearchParameter;
	
	public HeterogeneousAlgorithm(int size, int numOfParameters)
	{
		this.size = size;
		this.numOfParameters = numOfParameters;
		Initialize();
	}
	
	public void Initialize()
	{
		//initialize population
		localSearchParameter = (0.1 * (max - min));
		
		beliefSpace = new double[numOfParameters][2];
		for (int i = 0; i < numOfParameters; i++)
		{
			beliefSpace[i][0] = 0;
			beliefSpace[i][1] = -9E100;
		}
		
		Random rand = new Random();
		
		for (int i = 0; i < size; i++)
		{
			double [] newAgent = new double[numOfParameters];
			for (int j = 0; j < numOfParameters; j++)
			{
				newAgent[j] = rand.nextDouble() * (max - min);		//every parameter between 0 and 100
			}
			population.add(new Pair(newAgent, rand.nextInt((int)(numOfParameters*numOfPopulations))));
		}
		
		//population is looking to optimize its own parameters, which are ALL n if (n % (numOfParameters * numOfPopulations) == popNum)
	}
	
	public void SetFitness(OptimizationFunction newF)
	{
		f = newF;
	}
	
	public void Update()
	{
		cycle++;
		
		/*if (cycle % 3 == 0), then make new generation, else local search
			if local search, 
				for each individual in population, copy and add n, subtract n or cut n, where n = 0.1 * (max - min)
					if copy is better, replace
					else, nothing
					repeat, with n = n * 0.1;
					mutation = randomly choose one of the parameters to do this to, not all
			else if new generation
				don't replace best 0.1 * population.size
				the rest, randomly select and combine based on their fitness
				
			belief space?
				keep track of parameter * 2 array, with best parameters and their respective fitness in original find
		*/
		
		
		if (cycle % 5 == 0)
		{
			localSearchParameter = (0.1 * (max - min));
			
			Pair [] bestIndividuals = new Pair[(int)(numOfParameters * numOfPopulations)];
			for (int i = 0; i < population.size(); i++)
			{
				if (bestIndividuals[population.get(i).p2] == null)
				{
					bestIndividuals[population.get(i).p2] = population.get(i);
				}
				else
				{
					if (f.CalculateValue(bestIndividuals[population.get(i).p2].p1) < 
							f.CalculateValue(population.get(i).p1))
					{
						bestIndividuals[population.get(i).p2] = population.get(i);
					}
				}
			}
			
			for (int j = 0; j < bestIndividuals.length; j++)
			{
				for (int i = 0; i < numOfParameters; i++)
				{
					if (bestIndividuals[j].p2 == i % (numOfParameters * numOfPopulations))
					{
						if (f.CalculateValue(bestIndividuals[j].p1) > beliefSpace[i][1])
						{
							beliefSpace[i][0] = bestIndividuals[j].p1[i];
							beliefSpace[i][1] = f.CalculateValue(bestIndividuals[j].p1);
						}
					}
				}
			}
			
			ArrayList<Pair> newPopulation = new ArrayList();
			Random rand = new Random();
			for (int i = 0; i < bestIndividuals.length; i++)
			{
				newPopulation.add(bestIndividuals[i]);
			}
			for (int i = bestIndividuals.length; i < population.size(); i++)
			{
				int num1, num2;
				num1 = rand.nextInt(population.size());
				num2 = rand.nextInt(population.size());
				Pair indiv1, indiv2 = null;
				indiv1 = population.get(num1);
				while (indiv2 == null)
				{
					if (population.get(num2).p2 == indiv1.p2)
						indiv2 = population.get(num2);
					else
						num2 = rand.nextInt(population.size());
				}
				double fit1,fit2,fitTot;
				fit1 = f.CalculateValue(indiv1.p1);
				fit2 = f.CalculateValue(indiv2.p1);
				fitTot = fit1 + fit2;
				fit1 = fit1 / fitTot;
				fit2 = fit2 / fitTot;
				double[] newIndividual = new double[numOfParameters];
				for (int j = 0; j < numOfParameters; j++)
				{
					newIndividual[j] = beliefSpace[j][0];
				}
				for (int j = indiv1.p2; j < numOfParameters; j+=(int)(numOfParameters*numOfPopulations))
				{
					if (fit1 > 0.5)
						newIndividual[j] = indiv1.p1[j];
					else if (fit2 > 0.5)
						newIndividual[j] = indiv2.p1[j];
					else
						newIndividual[j] = (indiv1.p1[j] + indiv2.p1[j]) / 2.0;
				}
				newPopulation.add(new Pair(newIndividual, indiv1.p2));
			}
			population.clear();
			population = newPopulation;
		}
		else
		{
			//local search
			
			for (int i = 0; i < population.size(); i++)
			{

				double [] d1 = population.get(i).p1;
				int d2 = population.get(i).p2;
				Pair newCopy = new Pair(population.get(i));
				Pair addCopy = new Pair (population.get(i));
				Pair subtractCopy = new Pair(population.get(i));
				Pair cutCopy = new Pair (population.get(i));

				
				Random rand = new Random();
				int randNum;
				
				randNum = rand.nextInt((int)((numOfParameters*numOfPopulations) / numOfParameters)+1);
				if ((int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2) < addCopy.p1.length)
					addCopy.p1[(int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2)] += localSearchParameter;
				else
					addCopy.p1[(int) (((randNum - 1) * (numOfParameters*numOfPopulations)) + addCopy.p2)] += localSearchParameter;
				
				randNum = rand.nextInt((int)((numOfParameters*numOfPopulations) / numOfParameters)+1);
				if ((int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2) < addCopy.p1.length)
					subtractCopy.p1[(int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2)] -= localSearchParameter;
				else
					subtractCopy.p1[(int) (((randNum - 1) * (numOfParameters*numOfPopulations)) + addCopy.p2)] -= localSearchParameter;
				
				randNum = rand.nextInt((int)((numOfParameters*numOfPopulations) / numOfParameters)+1);
				if ((int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2) < addCopy.p1.length)
					cutCopy.p1[(int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2)] 
							= cutCopy.p1[(int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2)] / 2;
				else
					cutCopy.p1[(int) (((randNum - 1) * (numOfParameters*numOfPopulations)) + addCopy.p2)] 
							= cutCopy.p1[(int) ((randNum * (numOfParameters*numOfPopulations)) + addCopy.p2)] / 2;
				
				for (int j = 0; j < numOfParameters; j++)
				{
					addCopy.p1[j] = Math.max(min, addCopy.p1[j]);
					addCopy.p1[j] = Math.min(max, addCopy.p1[j]);
					subtractCopy.p1[j] = Math.max(min, subtractCopy.p1[j]);
					subtractCopy.p1[j] = Math.min(max, subtractCopy.p1[j]);
					cutCopy.p1[j] = Math.max(min, cutCopy.p1[j]);
					cutCopy.p1[j] = Math.min(max, cutCopy.p1[j]);
				}
				
				if (f.CalculateValue(addCopy.p1) > f.CalculateValue(population.get(i).p1))
					population.set(i, addCopy);
				if (f.CalculateValue(subtractCopy.p1) > f.CalculateValue(population.get(i).p1))
					population.set(i, subtractCopy);
				if (f.CalculateValue(cutCopy.p1) > f.CalculateValue(population.get(i).p1))
					population.set(i, cutCopy);
			
			}
			
			localSearchParameter = localSearchParameter * 0.1;
		}
	}
	
	public double[] PrintTopAgent()
	{
		//Collections.sort(population, new GAComparator());
		
		double[] d = new double[numOfParameters];
		for (int i = 0; i < numOfParameters; i++)
		{
			d[i] = beliefSpace[i][0];
		}
		
		double average = 0;
		double top = -9E100;
		for (int i = 0; i < size; i++)
		{
			average += f.CalculateValue(population.get(i).p1);
			if (f.CalculateValue(population.get(i).p1) > top)
				top = f.CalculateValue(population.get(i).p1);
		}
		average = average / (double)(size);
		
		//System.out.println(f.CalculateValue(d) + "\t" + average + "\t" + top);
		
		double[] returnValue = {average,top};
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
