package pack01;

	public class FElliptic implements OptimizationFunction{
		
		//Rastrigin Function (see formula and references at):
		//http://www.sfu.ca/~ssurjano/rastr.html
		public double CalculateValue(double [] x)
		{
			double returnValue = 0;
			
			double d = x.length;
			double f1 = 0;
			for (int i = 0; i < d; i++)
			{
				f1 += Math.pow(Math.pow(10, 6),(double)(i)/(double)(x.length))
						* Math.pow(x[i], 2);
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
