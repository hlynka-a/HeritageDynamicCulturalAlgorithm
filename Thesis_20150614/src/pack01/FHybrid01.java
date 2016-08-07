package pack01;


public class FHybrid01 implements OptimizationFunction{		
	//N = 3, p = [0.3,0.3,0.4], f9, f8, f1
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double f1 = 0;
		double f2 = 0;
		double f3 = 0;
		
		//f1 = modified schwefel's function,
		for (int i = 0; i < d *0.3; i++)
		{
			double z1 = x[i] + 4.209687462275036e+2;
			if (z1 <= 500 && z1 >= -500)
				f1 += z1 * Math.sin(Math.pow(Math.abs(z1),0.5));
			else if (z1 > 500)
				f1 += (500 - (z1 % 500))*Math.sin(Math.sqrt(Math.abs(500 - (z1 % 500))))
					- Math.pow((z1 - 500.0f),2)/(10000.0f*d);
			else
				f1 += (- 500 + (z1 % 500))*Math.sin(Math.sqrt(Math.abs(-500 + (z1 % 500))))
					- Math.pow((z1 + 500.0f),2)/(10000.0f*d);
			
		}
		f1 = 418.9829 * d - f1;
			
		//f2 = rastrigin's function,
		for (int i = (int)(d*0.3); i < d*0.6; i++)
		{
			f2 += Math.pow(x[i],2) - 10*Math.cos(2*Math.PI*x[i]) + 10;
		}
		
		//f3 = high-conditioned elliptic function
		for (int i = (int)(d*0.6); i < d; i++)
		{
			f3 += Math.pow(Math.pow(10,6),i+(int)(d*0.6)/(d - d*0.6))
					* Math.pow(x[i],2); 
		}
		
		
		returnValue = f1 + f2 + f3;
		
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
