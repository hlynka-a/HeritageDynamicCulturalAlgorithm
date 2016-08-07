package pack01;

public class FDiscus implements OptimizationFunction{
	
	//Rastrigin Function (see formula and references at):
	//http://www.sfu.ca/~ssurjano/rastr.html
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double f1 = 0;
		
		f1 = (Math.pow(10, 6))*(Math.pow(x[0],2));
		
		for (int i = 1; i < d; i++)
		{
			f1 += Math.pow(x[i],2);
		}
		
		returnValue = f1;
		
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
