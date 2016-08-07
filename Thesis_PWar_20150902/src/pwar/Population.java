package pwar;

public class Population {
	
	String name = "<default>";
	
	//populations must have:
	//- A : which side the country is on (0 or 1, -1 for undecided)
	//- B : weight of which side country is leaning towards to decide switching sides (0.0 to 1.0, 0.5 for undecided)
	//- C : weight of 'friend' value per country (-1.0 to 1.0, 0.0 for neutral)
	//- D : mutation to represent civil unrest, used at individual level, adds randomly to individual's C value when calculating B (0.0 to 1.0)
	// A <= B at important decisions, B <= sum of individual's heritage of C and D, C <= experiences during each year
	
	// for countries, their values also consider that of their children cities, cities consider their individuals.
	// individuals consider previous generation's belief space of both city and countries, 
	
	//individuals must have:
	//- just heritage. Their migrations will help diversify C in population.
	
	double[] heritage = new double[37];
	
	static int populationIndex = 0;
	static int individualIndex = 0;
	int currentIndex = 0;
	int currentSide = -1;
	double leaningSide = 0.0;
	double[] friends = new double[37];
	double mutation = 0.2;
	int lastPopulationNumber = 0;
	
	
	public Population(String n)
	{
		name = n;
		
		for (int i = 0; i < heritage.length; i++)
			heritage[i] = 0;
		
		if (name == "")
		{
			currentIndex = individualIndex;
			individualIndex++;
		}
		else
		{
			currentIndex = populationIndex;
			populationIndex++;
			if (currentIndex == 16)
				currentSide = 0;		//Sparta
			else if (currentIndex == 23)	
				currentSide = 1;		//Athens
			for (int i = 0; i < friends.length; i++)
			{
				friends[i] = 0.0f;
			}
			for (int i = 0; i < heritage.length; i++)
			{
				if (i == currentIndex)
					heritage[i] = 1.0;
				else
					heritage[i] = 0.0;
			}
		}
	}
	
	
	public void UpdateHeritage(double[] newHeritage)
	{
		double normalizedTotal = 0f;
		
		for (int i = 0; i < heritage.length; i++)
		{
			heritage[i] = heritage[i] * 0.5f;
			heritage[i] = heritage[i] + newHeritage[i];
			normalizedTotal += heritage[i];
		}
		
		for (int i = 0; i < heritage.length; i++)
		{
			if (normalizedTotal != 0.0f)
				heritage[i] = heritage[i] / normalizedTotal;
		}
	}
	
	public void UpdateHeritage(int index)
	{
		double normalizedTotal = 0f;
		
		for (int i = 0; i < heritage.length; i++)
		{
			heritage[i] = heritage[i] * 0.5f;
			if (index == i)
				heritage[i] = heritage[i] + 1.0f;
			normalizedTotal += heritage[i];
		}
		
		for (int i = 0; i < heritage.length; i++)
		{
			if (normalizedTotal != 0.0f)
				heritage[i] = heritage[i] / normalizedTotal;
		}
	}
	
	public void UpdateMutation(int popSize)
	{
		double mutationNew = 0;
		//(double)(lastPopulationNumber - popSize) / (double)(lastPopulationNumber);
		if (lastPopulationNumber != 0)
		{
			mutationNew = (double)(lastPopulationNumber - popSize) / (double)(lastPopulationNumber);
			mutation += mutationNew;
			mutation = Math.min(1.0, mutation);
			mutation = Math.max(0.0, mutation);
		}
		
		lastPopulationNumber = popSize;
	}
	
	public void UpdateCurrentSide(int i)
	{
		currentSide = i;
	}
	
	public void UpdateFriendship(int index, double change)
	{
		friends[index] += change;
		
		double normalizedTotal = 0;
		for (int i = 0; i < friends.length; i++)
		{
			normalizedTotal += Math.abs(friends[i]);
		}
		for (int i = 0; i < friends.length; i++)
		{
			if (normalizedTotal != 0.0f)
				friends[i] = friends[i] / normalizedTotal;
		}
	}
	
	public void ReplaceFriendship(double [] newFriendship)
	{
		for (int i = 0; i < friends.length; i++)
		{
			friends[i] = newFriendship[i];
		}
	}
	
	public void UpdateLeaningSide(double [] otherLeanings)
	{
		if (currentIndex != 16 && currentIndex != 23)
		{
			double newLeaning = 0;
			for (int i = 0; i < otherLeanings.length; i++)
			{
				newLeaning += otherLeanings[i] * friends[i];
			}
			
			leaningSide = newLeaning;
			leaningSide = Math.min(1, leaningSide);
			leaningSide = Math.max(-1, leaningSide);
			
			if (leaningSide > 0.5)
				currentSide = 1;
			else if (leaningSide < -0.5)
				currentSide = 0;
			else
				currentSide = -1;
		}
		else if (currentIndex == 16)
		{
			leaningSide = -1;
			currentSide = 0;
		}
		else if (currentIndex == 23)
		{
			leaningSide = 1;
			currentSide = 1;
		}
	}
	
	public void UpdateCurrentIndex(int i)
	{
		currentIndex = i;
	}
	
	public double[] GetHeritage()
	{
		double [] heritageCopy = new double[heritage.length];
		for (int i = 0; i < heritage.length; i++)
			heritageCopy[i] = heritage[i];
		return heritageCopy;
	}
	
	public double[] GetFriendship()
	{
		double [] friendCopy = new double[friends.length];
		for (int i = 0; i < friends.length; i++)
			friendCopy[i] = friends[i];
		return friendCopy;
	}
	
	public int GetCurrentIndex()
	{
		return currentIndex;
	}
	
	public boolean NameEquals(String n)
	{
		if (n.compareTo(name) == 0)
			return true;
		else
			return false;
	}

	public int BelongsTo()
	{
		return currentIndex;
		/*if (currentIndex == index)
		{
			return true;
		}
		return false;*/
	}
	
	public int WhichSide()
	{
		return currentSide;
	}
	
	public double GetLeaningSide()
	{
		return leaningSide;
	}
	
}
