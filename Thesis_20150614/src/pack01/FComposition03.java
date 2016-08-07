package pack01;


public class FComposition03 implements OptimizationFunction{		
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double [] f = {0,0,0,0,0};
		
		//f1 = modified schwefel's function
		for (int i = 0; i < d ; i++)
		{
			double z1 = x[i] + 4.209687462275036e+2;
			if (z1 <= 500 && z1 >= -500)
				f[0] += z1 * Math.sin(Math.pow(Math.abs(z1),0.5));
			else if (z1 > 500)
				f[0] += (500 - (z1 % 500))*Math.sin(Math.sqrt(Math.abs(500 - (z1 % 500))))
					- Math.pow((z1 - 500.0f),2)/(10000.0f*d);
			else
				f[0] += (- 500 + (z1 % 500))*Math.sin(Math.sqrt(Math.abs(-500 + (z1 % 500))))
					- Math.pow((z1 + 500.0f),2)/(10000.0f*d);
			
		}
		f[0] = 418.9829 * d - f[0];
		
		//f2 = rastrigin's function
		for (int i = 0; i < d; i++)
		{
			f[1] += (x[i] * x[i]) - (10 * Math.cos(2 * Math.PI * x[i]));
		}
		f[1]= (10 * d) + f[1];

		
		//f3 = high-conditioned elliptic function
		for (int i = 0; i < d; i++)
		{
			f[2] += Math.pow(Math.pow(10,6),i+(int)(d*0.7)/(d - d*0.7))
					* Math.pow(x[i],2); 
		}
		
		
		//N = 3, p = [10,30,50], a = [0.25,1,1e-7]
		//bias = [0, 100, 200]
		//f4, f1, f2, f3,f1
		
		//F(x) = Sum of (L * [pi * fi(x) + bias i]),
		//where L = shift matrix
		//(WE IGNORE SHIFT, so L is removed)
		
		//double [] p = {10,20,30,40,50};
		double [] a = {0.25, 1, 1e-7};
		double [] b = {0,100,200};
		int n = 3;
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
