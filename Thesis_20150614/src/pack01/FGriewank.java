package pack01;

public class FGriewank implements OptimizationFunction{
	
	//Griewank Function (see formula and references at):
	//http://www.sfu.ca/~ssurjano/griewank.html
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
	
		double d = x.length;
		double f1 = 0;
		double f2 = 1;
		for (int i = 0; i < d; i++)
		{
			f1 += (x[i] * x[i])/4000.0;
			f2 *= Math.cos(x[i] / Math.sqrt(i+1));
		}
		
		returnValue = f1 - f2 + 1;
		
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
