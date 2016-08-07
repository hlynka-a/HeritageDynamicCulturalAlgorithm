package pack01;

public class FAckley implements OptimizationFunction {

	// Ackley Function (see formula and references at): 
	// http://www.sfu.ca/~ssurjano/ackley.html
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double a = 20;			//"...recommended variable values are a = 20..."
		double b = 0.2;			//"...recommended variable values are b = 0.2..."
		double c = 2 * Math.PI;	//"...recommended variable values are c = 2*PI..."
		double d = x.length;	//d = dimensions, (x1,x2) = 2 dimensions
		double f1 = 0;
		double f2 = 0;
		for (int i = 0; i < d; i++)
		{
			f1 += x[i] * x[i];
			f2 += Math.cos(c * x[i]);
		}
		
		returnValue = (-a)*(Math.exp(-b*(Math.sqrt((1.0/d)*f1))))
				- Math.exp((1.0/d)*f2)
				+ a + Math.exp(1);
		
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
