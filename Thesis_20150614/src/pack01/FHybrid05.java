package pack01;


public class FHybrid05 implements OptimizationFunction{		
	//N = 5, p = [0.1,0.2,0.2,0.2,0.3], f14,f12,f4,f9,f1
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double f1 = 0;
		double f2 = 0;
		double f3 = 0;
		double f4 = 0;
		double f5 = 0;
		
		//f1 = scaffer's f6 function
		for (int i = 0; i < d*0.1; i++)
		{
			double x1 = x[i];
			double y1 = 0;
			if (i < d*0.1 - 1)
				y1 = x1 + 1;
			else
				y1 = 0;
			double g1 = 0.5 
					+ (Math.pow(Math.sin(Math.sqrt(x1*x1 + y1*y1)),2) - 0.5)
					/ Math.pow((1 + 0.001*(x1*x1 + y1*y1)),2);
			f1 += g1;
		}
		
		//f2 = HGBat function
		double f2Sum = 0;
		double f2Sum2 = 0;
		for (int i = (int)(d*0.1); i < d*0.3; i++)
		{
			f2Sum += x[i];
			f2Sum2 += x[i] * x[i];
		}
		f2 = Math.pow(Math.abs(f2Sum2*f2Sum2 - f2Sum*f2Sum),0.5)
				+ (0.5*f2Sum2 + f2Sum)/(d+0.5);

		//f3 = Rosenbrock's Function
		for (int i = (int)(d * 0.3); i < d * 0.5 - 1; i++)
		{
			f3 += 100 * Math.pow((x[i]*x[i] - x[i+1]),2)
					+ Math.pow(x[i] - 1, 2);
		}
		
		//f4 = modified schwefel's function,
		for (int i = (int)(d * 0.5); i < d *0.7; i++)
		{
			double z1 = x[i] + 4.209687462275036e+2;
			if (z1 <= 500 && z1 >= -500)
				f4 += z1 * Math.sin(Math.pow(Math.abs(z1),0.5));
			else if (z1 > 500)
				f4 += (500 - (z1 % 500))*Math.sin(Math.sqrt(Math.abs(500 - (z1 % 500))))
					- Math.pow((z1 - 500.0f),2)/(10000.0f*d);
			else
				f4 += (- 500 + (z1 % 500))*Math.sin(Math.sqrt(Math.abs(-500 + (z1 % 500))))
					- Math.pow((z1 + 500.0f),2)/(10000.0f*d);
			
		}
		f4 = 418.9829 * d - 41;
		
		//f5 = high-conditioned elliptic function
		for (int i = (int)(d*0.7); i < d; i++)
		{
			f5 += Math.pow(Math.pow(10,6),i+(int)(d*0.7)/(d - d*0.7))
					* Math.pow(x[i],2); 
		}
		
		
		returnValue = f1 + f2 + f3 + f4 + f5;
		
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
