package pwar;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main_Class {

	//set of countries, cities, each with belief space
	//set heritage of cities, such that cities under same countries are connected
	
	//set all cities to be on "side" 0 or side 1
	//set all cities to have "weight" between 0.0 and 1.0
	//set all cities to have a "turnmoil" value between 0.0 and 1.0, as mutation value
	
	//set individuals to have heritage for each city (a set, if A and used to be B, half B and heritage of B and add A)
	//use total of individuals each year to help make decisions, to set new values for the cities
	
	ArrayList<Population> populations = new ArrayList<Population>();
	ArrayList<Population> agentPopulation = new ArrayList<Population>();
	
	public static void main (String [] args)
	{
		
		System.out.println("Beginning of simulation.");
		
		Main_Class m = new Main_Class();
		
		
		//run 10 times, then output the average
		
		double [][][] averageSim = new double[41][37][40];
		
		for (int i = 0; i < 41; i++)
			for (int j = 0; j < 37; j++)
				for (int k = 0; k < 40; k++)
					averageSim[i][j][k] = 0;
		
		for (int j = 0; j < 10; j++)
		{
			System.out.println("Running again - " + j);
		
			m.Initialize();
			  
			m.InitializeAgents();
			
			for (int i = 0; i < 41; i++)
			{
				System.out.print("" + (450 - i) + " ");
				double [][] newSim = m.UpdateYear(450 - i);
				for (int x = 0; x < newSim.length; x++)
				{
					for (int y = 0; y < newSim[0].length; y++)
					{
						averageSim[i][x][y] += newSim[x][y];
						if (Double.isNaN(newSim[x][y]))
						{
							System.out.println("Error occured at " + i + ", " + x + " " + y);
							return;
						}
					}
				}
			}
			System.out.println("");
		}
		
		for (int i = 0; i < 41; i++)
		{
			for (int j = 0; j < 37; j++)
			{
				for (int k = 0; k < 40; k++)
				{
					averageSim[i][j][k] = averageSim[i][j][k] * 0.10f;
				}
			}
		}
		
		System.out.println("Now printing to file...");
		
		//print out data to file
		try {
			PrintWriter writer = new PrintWriter("data.txt");
			
			for (int i = 0; i < 41; i++)
			{
				writer.println("Year " + (450 - i));
				writer.print("a \t");
				for (int j = 0; j < 37; j++)
				{
					writer.print("" + m.populations.get(j).name.charAt(0) + "\t");
				}
				writer.println("");
				for (int j = 0; j < 37; j++)
				{
					writer.print("" + m.populations.get(j).name + "\t");
					for (int k = 0; k < 40; k++)
					{
						writer.print("" + averageSim[i][j][k] + "\t");
					}
					writer.println("");
					//System.out.print("" + populations.get(i).lastPopulationNumber + "\t" + populations.get(i).currentSide + "\t" + populations.get(i).mutation + "\n");
				}
				writer.println("");
			}
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong...");
			e.printStackTrace();
		}
		
		
		System.out.println("End of simulation.");


	}
	
	public void Initialize()
	{
		populations.clear();
		Population.populationIndex = 0;
		Population.individualIndex = 0;
		//add countries
		populations.add(new Population("Peloponnesus"));		//0
		populations.add(new Population("Aegina"));				//1
		populations.add(new Population("Boeotia"));				//2
		populations.add(new Population("Euboea"));				//3
		populations.add(new Population("Melos"));				//4
		populations.add(new Population("Corcyra"));				//5
		populations.add(new Population("Macedonia"));			//6
		populations.add(new Population("Thrace"));				//7
		populations.add(new Population("Troad"));				//8
		populations.add(new Population("Lesbos"));				//9
		populations.add(new Population("Chios"));				//10
		populations.add(new Population("Ionia"));				//11
		populations.add(new Population("Samos"));				//12
		populations.add(new Population("Kos"));					//13
		populations.add(new Population("Rhodes"));				//14
		populations.add(new Population("Sicily"));				//15
		
		//add cities
		populations.add(new Population("Sparta"));				//16
		populations.add(new Population("Sphakteria"));			//17
		populations.add(new Population("Corinth"));				//18
		populations.add(new Population("Megara"));				//19
		populations.add(new Population("Delium"));				//20
		populations.add(new Population("Plataea"));				//21
		populations.add(new Population("Thebes"));				//22
		populations.add(new Population("Athens"));				//23
		populations.add(new Population("Attica"));				//24
		populations.add(new Population("Amphipolis"));			//25
		populations.add(new Population("Scione"));				//26
		populations.add(new Population("Potidaea"));			//27
		populations.add(new Population("Hellespont"));			//28
		populations.add(new Population("Cyzicus"));				//29
		populations.add(new Population("Mytilene"));			//30
		populations.add(new Population("Erythrae"));			//31
		populations.add(new Population("Klazornenae"));			//32
		populations.add(new Population("Miletus"));				//33
		populations.add(new Population("Knidos"));				//34
		populations.add(new Population("Syracuse"));			//35
		populations.add(new Population("Pylos"));	 			//36
		
		//add city heritages
		
		populations.get(16).UpdateHeritage(0);
		populations.get(17).UpdateHeritage(0);
		populations.get(18).UpdateHeritage(0);
		populations.get(19).UpdateHeritage(0);
		
		populations.get(20).UpdateHeritage(2);
		populations.get(21).UpdateHeritage(2);
		populations.get(22).UpdateHeritage(2);
		populations.get(23).UpdateHeritage(2);
		populations.get(24).UpdateHeritage(2);
		
		populations.get(25).UpdateHeritage(6);

		populations.get(26).UpdateHeritage(7);
		populations.get(27).UpdateHeritage(7);
		
		populations.get(28).UpdateHeritage(8);
		populations.get(29).UpdateHeritage(8);
		
		populations.get(30).UpdateHeritage(9);
		
		populations.get(31).UpdateHeritage(10);
		populations.get(32).UpdateHeritage(10);
		
		populations.get(33).UpdateHeritage(12);
		
		populations.get(34).UpdateHeritage(13);
		
		populations.get(35).UpdateHeritage(15);
		
		populations.get(36).UpdateHeritage(0);
		
	}

	public void InitializeAgents()
	{
		agentPopulation.clear();
		//population numbers estimated from
		//http://www.ancientgreekbattles.net/Pages/47932_Population.htm
		//scaled down ( 1000 => 1 agent )
		
		//Sparta had roughly 16,000, Athens had roughly 315,000
		//no information on rest, we will assume 10,000 per city
		//S = 16, A = 315, rest = 10, total = 681
		for (int i = 0; i < 681; i++)
			agentPopulation.add(new Population(""));
		
		//add city heritages
		
		for (int i = 0; i < 315; i++)
		{
			agentPopulation.get(i).UpdateHeritage(populations.get(23).GetHeritage());
			agentPopulation.get(i).UpdateCurrentIndex(23);
		}
		
		for (int i = 315; i < 332; i++)
		{
			agentPopulation.get(i).UpdateHeritage(populations.get(16).GetHeritage());
			agentPopulation.get(i).UpdateCurrentIndex(16);
		}
		
		int counter = 0;
		int currentIndex = 0;
		for (int i = 332; i < agentPopulation.size(); i++)
		{
			counter++;
			if (counter > 100)
			{
				counter = 0;
				currentIndex++;
				if (currentIndex == 23 || currentIndex == 16)
					currentIndex++;
			}
			agentPopulation.get(i).UpdateHeritage(populations.get(currentIndex).GetHeritage());
			agentPopulation.get(i).UpdateCurrentIndex(currentIndex);
		}
		

	}
	
	public double[][] PrintData()
	{
		double [][] getData = new double[37][40];
		
		//System.out.print("\t");
		for (int i = 0; i < populations.size(); i++)
		{
			//System.out.print("" + populations.get(i).name.charAt(0) + "\t");
		}
		//System.out.println("");
		for (int i = 0; i < populations.size(); i++)
		{
			//System.out.print("" + populations.get(i).name + "\t");
			for (int j = 0; j < populations.size(); j++)
			{
				//System.out.print("" + populations.get(i).GetFriendship()[j] + "\t");
				getData[i][j] = populations.get(i).GetFriendship()[j];
			}
			getData[i][37] = populations.get(i).lastPopulationNumber;
			getData[i][38] = populations.get(i).leaningSide;
			getData[i][39] = populations.get(i).mutation;
			//System.out.print("" + populations.get(i).lastPopulationNumber + "\t" + populations.get(i).currentSide + "\t" + populations.get(i).mutation + "\n");
		}
		return getData;
	}
	
	public double[][] UpdateYear(int currentYear)
	{
		//populations must have:
		//- A : which side the country is on (0 or 1, -1 for undecided)
		//- B : weight of which side country is leaning towards to decide switching sides (0.0 to 1.0, 0.5 for undecided)
		//- C : weight of 'friend' value per country (-1.0 to 1.0, 0.0 for neutral)
		//- D : mutation to represent civil unrest, used at individual level, adds randomly to individual's C value when calculating B
		// A <= B at important decisions, B <= sum of individual's heritage of C and D, C <= experiences during each year
		// assumed that Athens and Sparta's values for A remain as they are
		
		// for countries, their values also consider that of their children cities, cities consider their individuals.
		// individuals consider previous generation's belief space of both city and countries, 
		
		//individuals must have:
		//- just heritage. Their migrations will help diversify C in population.
		
		/* Why not just use plain MPCA?
		 * 
		 * If using MPCA, we can still use migration and population belief spaces, but migration would be entirely 
		 * for population size, would not carry any current ties to other countries. More difficult to show
		 * reasons for internal rebellion / social-unrest. Individuals would be irrelavent in MPCA, since our 
		 * example does not consider population size as a factor in decisions, only the heritage of the individuals. 
		 * MPCA would only consider actions occurred against populations, not asking why those actions occurred.
		 * 
		 * */
		
		// - : tied to same side, X : against (reduced friendship), <-> : join, ^ : attack (strongly reduced friendship), 
		// { : become part of (city moves part of population to it, heritage of individuals and city change) 
		// change in friendship: 0.5 for help, 1.0 in attack/protection
		
		//EVERY YEAR: randomly choose 0.02% individuals for selection, 
		// combine heritages and randomly assign new city IF two parents are on non-conflicting sides, AND if new city does not conflict with side
		// ALSO randomly choose 0.01% individuals to randomly die
		// (stats taken from http://www.ecology.com/birth-death-rates/ )
		// ALSO update population friend-rates, change sides based on local population's friend-rate
		// (by default, individuals will automatically update all related populations in heritage, 
		//	HUGE PLUS to use of individuals with heritage, else, how would populations update their parents?)
		// ALSO update 'mutation' rate based on number of deaths compared to previous year
		
		
		if (currentYear == 450)
		{
			//450 : Corcyra - Corinth, Athens and Sparta assumed to be on opposite sides
			UpdateFriendship("Corcyra","Corinth", 1.0);
			UpdateFriendship("Potidaea","Corinth",1.0);				//433
			UpdateFriendship("Athens","Plataea",1.0);				//431
			UpdateFriendship("Athens","Lesbos",1.0);				//428
			UpdateFriendship("Athens","Mytilene",1.0);				//428
			FindPopulationByName("Athens").UpdateCurrentSide(1);
			FindPopulationByName("Sparta").UpdateCurrentSide(0);
		}
		
		if (currentYear == 436)
		{
			//436 : Athens X Attica, Athens <-> Thrace, Athens <-> Macedonia, Athens <-> Aegina
			UpdateFriendship("Athens", "Attica", -0.5);
			UpdateFriendship("Athens", "Thrace", 0.5);
			UpdateFriendship("Athens", "Macedonia", 0.5);
			UpdateFriendship("Athens", "Aegina", 0.5);
			// Athens ^ Amphipolis, Athens { Amphipolis
			UpdateFriendship("Athens", "Amphipolis", -1.0);
			Takeover("Athens","Amphipolis");
		}
		
		if (currentYear == 434)
		{
			//434 : Athens <-> Corcyra, Athens X Corinth, Athens & Corcyra ^ Corinth
			FindPopulationByName("Corcyra").UpdateCurrentSide(1);
			UpdateFriendship("Athens", "Corinth", -0.5);
			Attack("Corcyra","Corinth");
			Attack("Athens","Corinth");
		}
		
		if (currentYear == 433)
		{
			//433 : Poridaea is part of Corinth (moved to 450)
		}
		
		if (currentYear == 432)
		{
			//432 : Athens asks for <-> Potidaea, by requiring Potidaea  be <-> Macedonia and X Corinth. Potidaea refuses.
			//!! why did Potidaea refuse to join Athens?
			UpdateFriendship("Athens","Potidaea",-0.5);
			// Potidaea asks Sparta for help, Sparta agrees. Potidaea and Sparta attack Athens
			//!! why did Sparta agree to help Potidaea?
			UpdateFriendship("Potidaea","Sparta",1.0);
			Attack("Sparta","Athens");
			Attack("Potidaea","Athens");
			// Athens X Megara. Sparta disagrees with Athens for this, says this would be cause of war. 
			//!! was Athen's bad relationship with Megara cause for war?
			UpdateFriendship("Athens","Megara",-0.5);
		}
		
		if (currentYear == 431)
		{
			//431 : Athens - Plataea (moved to 450), Boeotia and Thebes ^ Plataea, Plataea wins and kills 180 Thebes hostages.
			Attack("Thebes","Plataea");
			// Plataea joins Athens, Thebes joins Sparta
			Takeover("Athens","Plataea");
			Takeover("Sparta","Thebes");
			//("In general, everyone prefers Sparta")
			//!! can we show this to be true?
			// Peloponnesus ^ Athens, Athens ^ Peloponnesus / Megara / Aegina, Athens { Peloponnesus / Megara / Aegina
			Attack("Peloponnesus","Athens");
			Attack("Athens","Peloponnesus");
			Attack("Athens","Megara");
			Attack("Athens","Aegina");
			Takeover("Athens","Peloponnesus");
			Takeover("Athens","Megara");
			Takeover("Athens","Aegina");
			//Athens <-> Thrace / Macedonia
			UpdateFriendship("Athens","Thrace",0.5);
			UpdateFriendship("Athens","Macedonia",0.5);
		}
		
		if (currentYear == 430)
		{
			//430 : Athens } Potidacia, Sparta } Attica
			Takeover("Athens", "Potidaea");
			Takeover("Sparta","Attica");
			//Athens has plague, 25% die
			Kill("Athens", 0.25);
		}
		
		if (currentYear == 429)
		{
			//429 : Peloponnesus ^ Plataea, Athens had plague, 25% die
			Attack("Peloponnesus","Plataea");
			Kill("Athens",0.25);
		}
		
		if (currentYear == 428)
		{
			//428 : Lesbos "revolts" against Athens
			//!! why did Lesbos revolt?
			Attack("Lesbos","Athens");
			//Lesbos <-> Mytilene, but Athens - Mytilene. Mytilene <-> Sparta, but Sparta came late. Athens } Mytilene 
			UpdateFriendship("Lesbos","Mytilene",1.0);
			UpdateFriendship("Mytilene","Sparta",1.0);
			Takeover("Athens","Mytilene");
		}
		
		if (currentYear == 427)
		{
			//427 : Sparta { Plataea. Corcyra in civil turmoil.
			Takeover("Sparta","Plataea");
			// Athens has plague, 25% die. 
			Kill("Athens",0.25);
		}
		
		if (currentYear == 425)
		{
			//425 : Athens { Pylos. Sparta { Sphakteria. 
			Takeover("Athens","Pylos");
			Takeover("Sparta","Sphakteria");
			// Athens ^ Sphakteria, Athens { Sphakteria, Athens has Sparta hostages
			Attack("Athens","Sphakteria");
			Takeover("Athens","Sphakteria");
		}
		
		if (currentYear == 424)
		{
			//424 :  Athens ^ Boeotia, Athens ^ Delium, Athens ^ Thebes, Athens { Delium
			Attack("Athens","Boeotia");
			Attack("Athens","Delium");
			Attack("Athens","Thebes");
			Takeover("Athens","Delium");
			// Sparta ^ Amphipolis, Sparta } Amphipolis
			Attack("Sparta","Amphipolis");
			Takeover("Sparta","Amphipolis");
		}
		
		if (currentYear == 423)
		{
			//423 : Scione ^ Athens, Athens } Scione
			Attack("Scione","Athens");
			Takeover("Athens","Scione");
		}
		
		if (currentYear == 422)
		{
			//422 : Athens ^ Amphipolis but loses
			Attack("Athens","Amphipolis");
		}
		
		if (currentYear == 421)
		{
			//421 : Athens and Sparta call truce
			//!! Why would they call truce?
			// Athens ^ Scione / Melos / Mytilene, Athens { Scione / Melos / Mytilene
			Attack("Athens","Scione");
			Attack("Athens","Melos");
			Attack("Athens","Mytilene");
			Takeover("Athens","Scione");
			Takeover("Athens","Melos");
			Takeover("Athens","Mytilene");
			// Sparta <-> Syracuse, Sparta <-> Attica
			UpdateFriendship("Sparta","Syracuse",0.5);
			UpdateFriendship("Sparta","Attica",0.5);
		}
		
		if (currentYear == 414)
		{
			//414 : Athens ^ Syracuse, fails, Attacks again, fails. Superstition caused loss of many Athens
			Attack("Athens","Syracuse");
			Attack("Athens","Syracuse");
			Kill("Athens",0.05);
			//!! how did superstition cause Athens to lose? 
		}
		
		
		if (currentYear == 410)
		{
			//410 : Athens ^ Cyzius, Athens { Cyzius
			// Euboea, Lesbos, Chios revolt against Athens and ask for Sparta's help to attack Athens
			UpdateFriendship("Euboea","Sparta",1.0);
			UpdateFriendship("Lesbos","Sparta",1.0);
			UpdateFriendship("Chios","Sparta",1.0);
			Attack("Euboea","Athens");
			Attack("Lesbos","Athens");
			Attack("Chios","Athens");
			// Sparta ^ Ionia. Erythrae, Klazornenae, Miletus revolt and attack Athens
			Attack("Sparta","Ionia");
			Attack("Erythrae","Athens");
			Attack("Klazornenae","Athens");
			Attack("Miletus","Athens");
			// Athens regains Lesbos and Klazornenae. Knidos, Rhodes revolt and attack Athens
			Takeover("Athens","Lesbos");
			Takeover("Athens","Klazornenae");
			Attack("Knidos","Athens");
			Attack("Rhodes","Athens");
			//!! Why did everyone start revolting against Athens?
		}
		
		UpdateGeneration();
		
		return PrintData();
	}
	
	
	
	public void UpdateGeneration()
	{
		//EVERY YEAR: randomly choose 10% individuals for selection, 
		// combine heritages and randomly assign new city IF two parents are on non-conflicting sides, AND if new city does not conflict with side
		// ALSO randomly choose 4% individuals to randomly die
		// (estimated from Internet, in Egypt around same time average death age was 25 (4 out of 100 die each year statistically)
		// , average fertialty was 5 per family (4 * 1/2 * 5 = 10 births per year for each death)
		
		Random rand = new Random();
		
		ArrayList<Population> newIndividuals = new ArrayList<Population>();
		
		for (int i = 0; i < agentPopulation.size() * 0.1; i++)
		{
			Population newI = new Population("");
			Population parent1 = agentPopulation.get(rand.nextInt(agentPopulation.size()));
			Population parent2 = agentPopulation.get(rand.nextInt(agentPopulation.size()));
			
			double [] heritage1 = parent1.GetHeritage();
			double [] heritage2 = parent2.GetHeritage();
			double [] newHeritage = new double[heritage1.length];
			double leaning1 = 0, leaning2 = 0, newLeaning = 0;
			for (int j = 0; j < heritage1.length; j++)
			{
				newHeritage[j] = heritage1[j] + heritage2[j];
				leaning1 += populations.get(j).GetLeaningSide() * heritage1[j];
				leaning2 += populations.get(j).GetLeaningSide() * heritage2[j];
			}		
			newI.UpdateHeritage(newHeritage);
			
			int newCity = 0;
			newLeaning = (leaning1 + leaning2) * 0.5f;
			for (int j = 0; j < 10; j++)
			{
				//CHANGE: instead of random city, pick random individual from population and use their current city index
				//newCity = rand.nextInt(heritage1.length);
				newCity = agentPopulation.get(rand.nextInt(agentPopulation.size())).currentIndex;
				//arbitrary threshold: if new random city is almost on the same side as me, set as new city
				if (Math.abs(populations.get(newCity).GetLeaningSide() - newLeaning) < 0.25f)	
				{
					break;
				}
			}
			newI.UpdateHeritage(newCity);
			newI.UpdateCurrentIndex(newCity);
			newIndividuals.add(newI);
		}
		
		int removePopulations = (int)(agentPopulation.size() * 0.04);
		for (int i = 0; i < removePopulations; i++)
		{
			agentPopulation.remove(rand.nextInt(agentPopulation.size()));
		}
		for (int i = 0; i < newIndividuals.size(); i++)
		{
			agentPopulation.add(newIndividuals.get(i));
		}
		
		
		// ALSO update population friend-rates, change sides based on local population's friend-rate
		// (by default, individuals will automatically update all related populations in heritage, 
		//	HUGE PLUS to use of individuals with heritage, else, how would populations update their parents?)
		// ALSO update 'mutation' rate based on number of deaths compared to previous year
		double [][] newFriendship = new double[populations.size()][populations.size()];
		int [] totalPopulationList = new int [populations.size()];
		double [] leaningList = new double[populations.size()];
		for (int i = 0; i < populations.size(); i++)
		{
			int totalPopulation = 1;
			double [] newFriendshipLocal = new double[populations.size()];
			for (int j = 0; j < populations.size(); j++)
			{
				newFriendshipLocal[j] = 0;
			}
			for (int j = 0; j < agentPopulation.size(); j++)
			{
				if (agentPopulation.get(j).GetCurrentIndex() == i)
				{
					totalPopulation++;
					for (int k = 0; k < populations.size(); k++)
					{
						for (int l = 0; l < populations.size(); l++)
						{
							newFriendshipLocal[k] += agentPopulation.get(j).GetHeritage()[l]*populations.get(l).GetFriendship()[k];
						}
					}
				}
			}
			for (int j = 0; j < populations.size(); j++)
			{
				newFriendshipLocal[j] = newFriendshipLocal[j] / totalPopulation;
			}
			//add effect of other 'friends' and their relationships: enemy of my friend is my enemy
			for (int j = 0; j < populations.size(); j++)
			{
				double friendsFriends = 0;
				double totalFriends = 0;
				for (int k = 0; k < populations.size(); k++)
				{
					friendsFriends += populations.get(i).GetFriendship()[k] * populations.get(k).GetFriendship()[j];
					totalFriends += Math.abs(populations.get(i).GetFriendship()[k] * populations.get(k).GetFriendship()[j]);
				}
				if (totalFriends != 0.0)
					friendsFriends = friendsFriends / totalFriends;
				newFriendshipLocal[j] = (newFriendshipLocal[j]*0.75f + friendsFriends*0.25f);
			}
			newFriendship[i] = newFriendshipLocal;
			totalPopulationList[i] = totalPopulation;
			leaningList[i] = populations.get(i).GetLeaningSide();
		}
		for (int i = 0; i < populations.size(); i++)
		{
			populations.get(i).ReplaceFriendship(newFriendship[i]);
			populations.get(i).UpdateLeaningSide(leaningList);
			populations.get(i).UpdateMutation(totalPopulationList[i]);
		}
		
		
	}
	
	public void Kill(String p1, double factor)
	{
		Random rand = new Random();
		for (int i = 0; i < agentPopulation.size(); i++)
		{
			if (agentPopulation.get(i).BelongsTo() == FindPopulationByName(p1).GetCurrentIndex() && rand.nextFloat() <= factor)
			{
				agentPopulation.remove(i);
			}
		}
	}
	
	public void Attack(String p1, String p2)
	{
		UpdateFriendship(p1,p2,-1.0);
		
		//remove approx. 10% of the population on both sides
		Random rand = new Random();
		
		double pop1 = 0, pop2 = 0;
		for (int i = 0; i < agentPopulation.size(); i++)
		{
			int belongTo1 = agentPopulation.get(i).BelongsTo();
			if (
					belongTo1 == FindPopulationByName(p1).GetCurrentIndex()
					||  
					(populations.get(belongTo1).WhichSide() == FindPopulationByName(p1).WhichSide() 
					&& populations.get(belongTo1).WhichSide() != -1)
					)
				pop1++;
			else if (belongTo1 == FindPopulationByName(p2).GetCurrentIndex()
					||  (populations.get(belongTo1).WhichSide() == FindPopulationByName(p2).WhichSide() && populations.get(belongTo1).WhichSide() != -1))
				pop2++;
		}
		
		double randNumFactor = Math.min(pop1*0.5f, pop2*0.5f);
		
		for (int i = 0; i < agentPopulation.size(); i++)
		{
			int belongTo = agentPopulation.get(i).BelongsTo();
			if ((belongTo == FindPopulationByName(p1).GetCurrentIndex()
					||  (populations.get(belongTo).WhichSide() == FindPopulationByName(p1).WhichSide() && populations.get(belongTo).WhichSide() != -1))
				&& pop1 > 0 && rand.nextFloat() <= randNumFactor / pop1)
			{
				agentPopulation.remove(i);
			}
			else if ((belongTo == FindPopulationByName(p2).GetCurrentIndex()
					||  (populations.get(belongTo).WhichSide() == FindPopulationByName(p2).WhichSide() && populations.get(belongTo).WhichSide() != -1)) 
				&& pop2 > 0 && rand.nextFloat() <= randNumFactor / pop2)
			{
				agentPopulation.remove(i);
			}
		}
	}
	
	public void Takeover(String p1, String p2)
	{
		//add p1's heritage to p2
		//replace half of p2's individuals with individuals from p1

		FindPopulationByName(p2).UpdateHeritage(FindPopulationByName(p1).GetHeritage());
		
		int numOfPopulation = 0;
		for (int i = 0; i < agentPopulation.size(); i++)
		{
			if (agentPopulation.get(i).BelongsTo() == FindPopulationByName(p2).GetCurrentIndex())
			{
				numOfPopulation++;
			}
		}
		
		Collections.shuffle(agentPopulation);
		
		int removePopulation = 0;
		
		for (int i = 0; i < agentPopulation.size(); i++)
		{
			if (agentPopulation.get(i).BelongsTo() == FindPopulationByName(p2).GetCurrentIndex() && removePopulation < numOfPopulation * 0.5)
			{
				agentPopulation.remove(i);
				removePopulation++;
			}
		}
		removePopulation = 0;
		for (int i = 0; i < agentPopulation.size(); i++)
		{
			if (agentPopulation.get(i).BelongsTo () == FindPopulationByName(p1).GetCurrentIndex() && removePopulation < numOfPopulation * 0.5)
			{
				agentPopulation.get(i).UpdateCurrentIndex(FindPopulationByName(p2).GetCurrentIndex());
				//update heritage when agent moves, still stronger to old population even though it now belongs here
				agentPopulation.get(i).UpdateHeritage(FindPopulationByName(p2).GetCurrentIndex());
				agentPopulation.get(i).UpdateHeritage(FindPopulationByName(p1).GetCurrentIndex());
				removePopulation++;
			}
		}
	}
	
	public void UpdateFriendship(String p1, String p2, double value)
	{
		FindPopulationByName(p1).UpdateFriendship(FindPopulationByName(p2).GetCurrentIndex(), value);
		FindPopulationByName(p2).UpdateFriendship(FindPopulationByName(p1).GetCurrentIndex(), value);
		
	}
	
	public Population FindPopulationByName(String name)
	{
		Population returnPopulation = null;
		
		for (int i = 0; i < populations.size(); i++)
		{
			if (populations.get(i).NameEquals(name))
				returnPopulation = populations.get(i);
		}
		
		return returnPopulation;
	}
}
