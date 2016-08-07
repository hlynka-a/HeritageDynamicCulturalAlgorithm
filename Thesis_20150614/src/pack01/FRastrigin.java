package pack01;

public class FRastrigin implements OptimizationFunction{
	
	//Rastrigin Function (see formula and references at):
	//http://www.sfu.ca/~ssurjano/rastr.html
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double f1 = 0;
		for (int i = 0; i < d; i++)
		{
			f1 += (x[i] * x[i]) - (10 * Math.cos(2 * Math.PI * x[i]));
		}
		
		returnValue = (10 * d) + f1;
		
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
