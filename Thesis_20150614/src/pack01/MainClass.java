package pack01;

import java.util.ArrayList;

/* DESCRIPTION:
 * 
 * Began on June 14, 2015
 * 
 * Purpose: to test HDCA against MPCA, and plain CA and GA on standard optimization benchmark functions.
 * 
 * Links:
 * https://archive.org/details/oxford-2005-facebook-matrix 
 * 		(to download facebook dataset facebook100 as matlab files)
 * http://www.sfu.ca/~ssurjano/optimization.html
 * 		(optimization benchmark math functions list)
 * http://www.cs.cmu.edu/afs/cs/project/jair/pub/volume24/ortizboyer05a-html/node6.html
 * 		(optimization benchmark math functions list + acadmeic lectures)
 * 
 *http://web.mysites.ntu.edu.sg/epnsugan/PublicSite/Shared%20Documents/CEC-2014/Definitions%20of%20%20CEC2014%20benchmark%20suite%20Part%20A.pdf
 * 
 * */

public class MainClass {

	public static void main (String [] args)
	{
		System.out.println("Begin simulation.");
		MainClass m = new MainClass();
		
		//http://www.sfu.ca/~ssurjano/optimization.html
		//(look up 'many local minima' functions and 'steep ridges/drops' functions)
		
		int which = 0;
		OptimizationFunction f;
		f = new FDiscus();
		System.out.println("Discus");
		m.RunA(f, which);
		f = new FElliptic();
		System.out.println("Elliptic");
		m.RunA(f, which);
		f = new FAckley();
		System.out.println("Ackley");
		m.RunA(f, which);
		f = new FGriewank();
		System.out.println("Griewank");
		m.RunA(f, which);
		f = new FRastrigin();
		System.out.println("Rastrigin");
		m.RunA(f, which);
		f = new FHybrid01();
		System.out.println("Hybrid01");
		m.RunA(f, which);
		f = new FHybrid05();
		System.out.println("Hyrbrid05");
		m.RunA(f, which);
		f = new FComposition01();
		System.out.println("Composition01");
		m.RunA(f, which);
		f = new FComposition03();
		System.out.println("Composition03");
		m.RunA(f, which);
		f = new FComposition07();
		System.out.println("Composition07");
		m.RunA(f, which);
		System.out.println("DONE!");

		
		/*System.out.println("This is a genetic algorithm:");
		GeneticAlgorithm ga = new GeneticAlgorithm(100,10);
		ga.SetFitness(f);
		for (int i = 0; i < 100; i++)
		{
			ga.Update();
			ga.PrintTopAgent();
		}
		System.out.println("This is end of genetic algorithm.");
		
		System.out.println("This is a Heritage Dynamic Cultural Algorithm:");
		HeritageAlgorithm ha = new HeritageAlgorithm(100,10);
		ha.SetFitness(f);
		for (int i = 0; i < 100; i++)
		{
			ha.Update();
			ha.PrintTopAgent();
		}
		System.out.println("This is end of Heritage Dynamic Cultural Algorithm.");
		
		System.out.println("This is a Heritage Dynamic Cultural Algorithm v02:");
		HeritageAlgorithm_Op02 ha_02 = new HeritageAlgorithm_Op02(100,10);
		ha_02.SetFitness(f);
		for (int i = 0; i < 100; i++)
		{
			ha_02.Update();
			ha_02.PrintTopAgent();
		}
		System.out.println("This is end of Heritage Dynamic Cultural Algorithm v02.");
		
		System.out.println("This is a Heterogeneous MP Cultural Algorithm:");
		HeterogeneousAlgorithm ht = new HeterogeneousAlgorithm(100,10);
		ht.SetFitness(f);
		for (int i = 0; i < 100; i++)
		{
			ht.Update();
			ht.PrintTopAgent();
		}
		System.out.println("This is end of Heterogeneous MP Cultural Algorithm.");
		
		System.out.println("This is a Cultural Algorithm:");
		CulturalAlgorithm ca = new CulturalAlgorithm(100,10);
		ca.SetFitness(f);
		for (int i = 0; i < 100; i++)
		{
			ca.Update();
			ca.PrintTopAgent();
		}
		System.out.println("This is end of Cultural Algorithm.");*/
		
		System.out.println("End simulation.");
	}
	
	
	
	void InitializeGA(int size)
	{
		
	}
	
	void UpdateGA()
	{
		
	}
	
	void RunA(OptimizationFunction f, int whichA)
	{
		double [][][] results = new double[10][100][2];
		double sd1 = 0;
		double sd2 = 0;
		//initialize
		for (int j = 0; j < 10; j++)
		{
			for (int i = 0; i < 100; i++)
			{
				results[j][i][0] = 0;
				results[j][i][1] = 0;
			}
		}
		
		if (whichA == 0)
		{
			System.out.println("This is a GA algorithm:");
			GeneticAlgorithm ga = new GeneticAlgorithm(100,10);
			//run 10 times
			ga.SetFitness(f);
			for (int j = 0; j < 10; j++)
			{
				ga = new GeneticAlgorithm(100,10);
				ga.SetFitness(f);
				for (int i = 0; i < 100; i++)
				{
					//if (i % 10 == 0)
					//	f.ChangeValue(false);
					ga.Update();
					double r [] = ga.PrintTopAgent();
					results[j][i] = r;
				}
			}
		}
		else if (whichA == 1)
		{
			System.out.println("This is a CA algorithm:");
			CulturalAlgorithm ga = new CulturalAlgorithm(100,10);
			//run 10 times
			ga.SetFitness(f);
			for (int j = 0; j < 10; j++)
			{
				ga = new CulturalAlgorithm(100,10);
				ga.SetFitness(f);
				for (int i = 0; i < 100; i++)
				{
					//if (i % 10 == 0)
					//	f.ChangeValue(false);
					ga.Update();
					double r [] = ga.PrintTopAgent();
					results[j][i] = r;
				}
			}
		}
		else if (whichA == 2)
		{
			System.out.println("This is a HMPCA algorithm:");
			HeterogeneousAlgorithm ga = new HeterogeneousAlgorithm(100,10);
			//run 10 times
			ga.SetFitness(f);
			for (int j = 0; j < 10; j++)
			{
				ga = new HeterogeneousAlgorithm(100,10);
				ga.SetFitness(f);
				for (int i = 0; i < 100; i++)
				{
					//if (i % 10 == 0)
					//	f.ChangeValue(false);
					ga.Update();
					double r [] = ga.PrintTopAgent();
					results[j][i] = r;
					/*if (r[0] < -10E10 || r[0] > 10E10)
					{
						System.out.println("Weird one = " + j + " _ "  + i + " = "+ r[0]);
					}*/
				}
			}
		}
		else
		{
			System.out.println("This is a Heritage algorithm:");
			HeritageAlgorithm_Op02 ga = new HeritageAlgorithm_Op02(100,10);
			//run 10 times
			ga.SetFitness(f);
			for (int j = 0; j < 10; j++)
			{
				ga = new HeritageAlgorithm_Op02(100,10);
				ga.SetFitness(f);
				for (int i = 0; i < 100; i++)
				{
					//if (i % 10 == 0)
					//	f.ChangeValue(false);
					ga.Update();
					double r [] = ga.PrintTopAgent();
					results[j][i] = r;
				}
			}
		}
		
		//output average
		double [][] average = new double[100][2];
		for (int j = 0; j < 10; j++)
		{
			for (int i = 0; i < 100; i++)
			{
				average[i][0] += results[j][i][0];
				average[i][1] += results[j][i][1];
			}
		}
		for (int i = 0; i < 100; i++)
		{
			average[i][0] = average[i][0] / 10.0f;
			average[i][1] = average[i][1] / 10.0f;
			sd1 = 0; sd2 = 0;
			for (int j = 0; j < 10; j++)
			{
				sd1 += Math.pow((results[j][i][0] - average[i][0]),2);
				sd2 += Math.pow((results[j][i][1] - average[i][1]),2);
			}
			sd1 = Math.sqrt(0.1*sd1);
			sd2 = Math.sqrt(0.1*sd2);
			System.out.println(average[i][0] + "\t" + sd1 + "\t" + average[i][1] + "\t" + sd2);
		}
		
		
		if (whichA == 0)
		{
			System.out.println("This is the end of GA algorithm:");
		}
		else if (whichA == 1)
		{
			System.out.println("This is the end of CA algorithm:");
		}
		else if (whichA == 2)
		{
			System.out.println("This is the end of HMPCA algorithm:");
		}
		else
		{
			System.out.println("This is the end of Heritage algorithm:");
		}

		
	}
	

	

}
