package pack01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class HeritageAlgorithm {
	OptimizationFunction f;			//fitness function
	
	ArrayList<double[]> population = new ArrayList();
	int size;
	int numOfParameters;
	
	int max = 100;
	int min = 0;
	
	ArrayList<ArrayList<double[]>> populationTypes = new ArrayList();		//x by 3 (solution parameter, influence value, score), belief spaces
	ArrayList<double[]> heritage = new ArrayList();
	
	// DIFFERENCE FROM GA:
	/* Selection 	- take best performing agents, randomly pick two to be parents to make child
	 *           	- population of parents forms heritage (list of pairs) of agent
	 * 			 	- population has only one parameter value in mind: using best values from other populations in previous generation to grade with
	 * 				- agent's success is weighted average of goals based on heritage
	 * 				- ... so agent should automatically set parameters to be best of other populations in previous generations.
	 * */
	
	
	public HeritageAlgorithm(int size, int numOfParameters)
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
		
		for (int i = 0; i < numOfParameters; i++)
		{
			populationTypes.add(new ArrayList<>());
		}
		
		//initialize agents to belong to population
		for (int i = 0; i < size; i++)
		{
			double [] newHeritage = new double[numOfParameters];
			for (int j = 0; j < numOfParameters; j++)
			{
				newHeritage[j] = 0;
			}
			newHeritage[rand.nextInt(numOfParameters)] = 1;
			heritage.add(newHeritage);
		}
		
	}
	
	public void SetFitness(OptimizationFunction newF)
	{
		f = newF;
	}
	
	public void Update()
	{
		//update population "belief spaces"
		for (int i = 0; i < numOfParameters; i++)
		{
			populationTypes.get(i).clear();
		}
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < numOfParameters; j++)
			{
				if (heritage.get(i)[j] > 0)
				{
					double[] newSolution = new double[3];
					newSolution[0] = population.get(i)[j];
					newSolution[1] = heritage.get(i)[j];
					newSolution[2] = f.CalculateValue(population.get(i));
					populationTypes.get(j).add(newSolution);
				}
			}
		}
		
		//sort
		ArrayList<double[]> sortedPopulation = population;
		Collections.sort(sortedPopulation, new GAComparator());
		
		//choose top n for "selection"
		double n = 0.2;
		ArrayList<double[]> newPopulation = new ArrayList();
		ArrayList<double[]> newHeritage = new ArrayList();
		ArrayList<double[]> sortedHeritage = new ArrayList();
		/*for (int i = 0; i < size * n; i++)
		{
			newPopulation.add(sortedPopulation.get(i));
			for (int j = 0; j < size; j++)
			{
				if (sortedPopulation.get(i) == population.get(j))
				{
					newHeritage.add(heritage.get(j));
					break;
				}
			}
		}*/
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (sortedPopulation.get(i) == population.get(j))
				{
					sortedHeritage.add(heritage.get(j));
					break;
				}
			}
		}
		
		Random rand = new Random();
		//combine top n for "reproduction"
		/*for (int i = (int)(size*n); i < size; i++)
		{
			double [] newAgentHeritage = new double[numOfParameters];
			int parent1 = rand.nextInt((int)(size*n));
			int parent2 = rand.nextInt((int)(size*n));
			for (int j = 0; j < numOfParameters; j++)
			{
				newAgentHeritage[j] = newHeritage.get(parent1)[j]*0.5 + newHeritage.get(parent2)[j]*0.5;
			}
			
			if (f.CalculateValue(sortedPopulation.get(parent1)) >= f.CalculateValue(sortedPopulation.get(parent2)))
			{
				int largestHeritage = 0;
				for (int j = 0; j < numOfParameters; j++)
				{
					if (newHeritage.get(parent1)[j] > newHeritage.get(parent1)[largestHeritage])
						largestHeritage = j;
				}
				newAgentHeritage[largestHeritage] += 1;
			}
			else
			{
				int largestHeritage = 0;
				for (int j = 0; j < numOfParameters; j++)
				{
					if (newHeritage.get(parent2)[j] > newHeritage.get(parent2)[largestHeritage])
						largestHeritage = j;
				}
				newAgentHeritage[largestHeritage] += 1;
			}
			//normalize heritage values
			double sumHeritage = 0;
			for (int j = 0; j < numOfParameters; j++)
			{
				sumHeritage += newAgentHeritage[j];
			}
			for (int j = 0; j < numOfParameters; j++)
			{
				newAgentHeritage[j] = newAgentHeritage[j] / sumHeritage;
			}
			//add new heritage to set
			newHeritage.add(newAgentHeritage);
			//define parameter values for agent
			double[] newAgent = new double[numOfParameters];
			for (int j = 0; j < numOfParameters; j++)
			{
				if (newAgentHeritage[j] > 0)
				{
					double sumHeritageParameter = 0;
					for (int k = 0; k < populationTypes.get(j).size(); k++)
					{
						//sumHeritageParameter += populationTypes.get(j).get(k)[1];
						sumHeritageParameter += populationTypes.get(j).get(k)[1] * populationTypes.get(j).get(k)[2];
					}
					double choiceHeritageParameter = rand.nextDouble() * sumHeritageParameter;
					sumHeritageParameter = 0;
					for (int k = 0; k < populationTypes.get(j).size(); k++)
					{
						if (choiceHeritageParameter 
								//< (populationTypes.get(j).get(k)[1]) + sumHeritageParameter)
								< (populationTypes.get(j).get(k)[1] * populationTypes.get(j).get(k)[2]) + sumHeritageParameter)
						{
							newAgent[j] = populationTypes.get(j).get(k)[0];
							break;
						}
						else
						{
							//sumHeritageParameter += populationTypes.get(j).get(k)[1];
							sumHeritageParameter += populationTypes.get(j).get(k)[1] * populationTypes.get(j).get(k)[2];
						}
					}
				}
				else
				{
					newAgent[j] = rand.nextDouble() * (max - min);
				}
			}
			//add new agent to set
			newPopulation.add(newAgent);
		}
		
		//modify some for "mutation"
		for (int i = (int)(size*n); i < size; i++)
		{
			if (rand.nextDouble() < 0.5)
			{
				for (int j = 0; j < numOfParameters; j++)
				{
					newPopulation.get(i)[j] += ((rand.nextDouble() * 10) - 5);
				}
			}
		}*/
		for (int i = 0; i < size; i++)
		{
			double [] newAgentHeritage = new double[numOfParameters];
			int parent1 = rand.nextInt((int)(size));
			int parent2 = rand.nextInt((int)(size));
			for (int j = 0; j < numOfParameters; j++)
			{
				newAgentHeritage[j] = sortedHeritage.get(parent1)[j]*0.5 + sortedHeritage.get(parent2)[j]*0.5;
			}
			
			if (f.CalculateValue(sortedPopulation.get(parent1)) >= f.CalculateValue(sortedPopulation.get(parent2)))
			{
				int largestHeritage = 0;
				for (int j = 0; j < numOfParameters; j++)
				{
					if (sortedHeritage.get(parent1)[j] > sortedHeritage.get(parent1)[largestHeritage])
						largestHeritage = j;
				}
				newAgentHeritage[largestHeritage] += 1;
			}
			else
			{
				int largestHeritage = 0;
				for (int j = 0; j < numOfParameters; j++)
				{
					if (sortedHeritage.get(parent2)[j] > sortedHeritage.get(parent2)[largestHeritage])
						largestHeritage = j;
				}
				newAgentHeritage[largestHeritage] += 1;
			}
			//normalize heritage values
			double sumHeritage = 0;
			for (int j = 0; j < numOfParameters; j++)
			{
				sumHeritage += newAgentHeritage[j];
			}
			for (int j = 0; j < numOfParameters; j++)
			{
				newAgentHeritage[j] = newAgentHeritage[j] / sumHeritage;
			}
			//add new heritage to set
			newHeritage.add(newAgentHeritage);
			//define parameter values for agent
			double[] newAgent = new double[numOfParameters];
			for (int j = 0; j < numOfParameters; j++)
			{
				if (newAgentHeritage[j] > 0)
				{
					double sumHeritageParameter = 0;
					for (int k = 0; k < populationTypes.get(j).size(); k++)
					{
						//sumHeritageParameter += populationTypes.get(j).get(k)[1];
						sumHeritageParameter += populationTypes.get(j).get(k)[1] * populationTypes.get(j).get(k)[2];
					}
					double choiceHeritageParameter = rand.nextDouble() * sumHeritageParameter;
					sumHeritageParameter = 0;
					for (int k = 0; k < populationTypes.get(j).size(); k++)
					{
						if (choiceHeritageParameter 
								//< (populationTypes.get(j).get(k)[1]) + sumHeritageParameter)
								< (populationTypes.get(j).get(k)[1] * populationTypes.get(j).get(k)[2]) + sumHeritageParameter)
						{
							newAgent[j] = populationTypes.get(j).get(k)[0];
							break;
						}
						else
						{
							//sumHeritageParameter += populationTypes.get(j).get(k)[1];
							sumHeritageParameter += populationTypes.get(j).get(k)[1] * populationTypes.get(j).get(k)[2];
						}
					}
				}
				else
				{
					newAgent[j] = rand.nextDouble() * (max - min);
				}
			}
			//add new agent to set
			newPopulation.add(newAgent);
		}
		
		//modify some for "mutation"
		for (int i = 0; i < size; i++)
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
		heritage.clear();
		heritage = newHeritage;
	}
	
	public void PrintTopAgent()
	{
		//Collections.sort(population, new GAComparator());
		ArrayList<double[]> newPopulation = population;
		Collections.sort(newPopulation, new GAComparator());
		
		double average = 0;
		for (int i = 0; i < size; i++)
		{
			average += f.CalculateValue(population.get(i));
		}
		average = average / (double)(size);
		
		double averageHeritageSizes = 0;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < numOfParameters; j++)
			{
				if (heritage.get(i)[j] > 0)
				{
					averageHeritageSizes += 1;
				}
			}
		}
		averageHeritageSizes = averageHeritageSizes / (double)(size);
		
		System.out.println(f.CalculateValue(newPopulation.get(0)) + "\t" + average + "\t" + averageHeritageSizes);
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
