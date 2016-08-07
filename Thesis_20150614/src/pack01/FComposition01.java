package pack01;


public class FComposition01 implements OptimizationFunction{		
	//N = 5, p = [10,20,30,40,50], a = [1,1e-6,1e-26,1e-6,1e-6]
	//bias = [0, 100, 200, 300, 400]
	//f4, f1, f2, f3,f1
	
	//F(x) = Sum of (L * [pi * fi(x) + bias i]),
	//where L = shift matrix
	//(WE IGNORE SHIFT, so L is removed)
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double [] f = {0,0,0,0,0};
		
		//f1 = rosenbrock's function
		for (int i = 0; i < d-1; i++)
		{
			f[0] += 100 * Math.pow((x[i]*x[i] - x[i+1]),2)
					+ Math.pow(x[i] - 1, 2);
		}
		
		//f2 = high-conditioned elliptic function
		for (int i = 0; i < d; i++)
		{
			f[1] += Math.pow(Math.pow(10,6),i+(int)(d*0.7)/(d - d*0.7))
					* Math.pow(x[i],2); 
		}
		
		//f3 = bent cigar function
		for (int i = 1; i < d; i++)
		{
			f[2] += x[i]*x[i];
		}	
		f[2] = Math.pow(10, 6)*f[3] + x[0]*x[0];
		
		//f4 = dicsus function
		f[3] = (Math.pow(10, 6))*(Math.pow(x[0],2));
		for (int i = 1; i < d; i++)
		{
			f[3] += Math.pow(x[i],2);
		}

		
		//f5 = high-conditioned elliptic function
		for (int i = 0; i < d; i++)
		{
			f[4] += Math.pow(Math.pow(10,6),i+(int)(d*0.7)/(d - d*0.7))
					* Math.pow(x[i],2); 
		}
		
		
		//N = 5, p = [10,20,30,40,50], a = [1,1e-6,1e-26,1e-6,1e-6]
		//bias = [0, 100, 200, 300, 400]
		//f4, f1, f2, f3,f1
		
		//F(x) = Sum of (L * [pi * fi(x) + bias i]),
		//where L = shift matrix
		//(WE IGNORE SHIFT, so L is removed)
		
		//double [] p = {10,20,30,40,50};
		double [] a = {1, 1e-6, 1e-26, 1e-6, 1e-6};
		double [] b = {0,100,200,300,400};
		int n = 5;
		for (int i = 0; i < n; i++)
		{
			returnValue += a[i]*f[i] + b[i];
		}
		
		if (valuePositive)
			return returnValue;
		else
			return -returnValue;
	}
	
		
	boolean valuePositive = true;
	
	public void ChangeValue(boolean a)
	{
		valuePositive = !valuePositive;
	}
}
