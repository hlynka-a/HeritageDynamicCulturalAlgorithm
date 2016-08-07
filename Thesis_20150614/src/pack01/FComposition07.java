package pack01;


public class FComposition07 implements OptimizationFunction{		
	public double CalculateValue(double [] x)
	{
		double returnValue = 0;
		
		double d = x.length;
		double [] f = {0,0,0,0,0};
		
		//f1 = hybrid funcction 1 F17
			double f1 = 0;
			double f2 = 0;
			double f3 = 0;
			double f4 = 0;
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
			f[0] = f1 + f2 + f3;
		
		
		
		//f2 = hybrid function 2 F18
			f1 = 0; f2 = 0; f3 = 0; f4 = 0;
			//f1 = bent cigar f2
			for (int i = 0; i < d*0.3; i++)
			{
				f1 += x[i]*x[i];
			}	
			f1 = Math.pow(10, 6)*f1 + x[0]*x[0];
			//f2 = HGBat f12
			double f2Sum = 0;
			double f2Sum2 = 0;
			for (int i = (int)(d*0.3); i < d*0.6; i++)
			{
				f2Sum += x[i];
				f2Sum2 += x[i] * x[i];
			}
			f2 = Math.pow(Math.abs(f2Sum2*f2Sum2 - f2Sum*f2Sum),0.5)
					+ (0.5*f2Sum2 + f2Sum)/(d+0.5);
			//f3 = rastrigin f8
			for (int i = (int)(d*0.6); i < d; i++)
			{
				f3 += (x[i] * x[i]) - (10 * Math.cos(2 * Math.PI * x[i]));
			}
			f3 = (10 * d) + f3;
			f[1] = f1 + f2 + f3;			
		
		//f3 = hybrid function 3 F19, 0.2,0.2,0.3,0.3
			f1 = 0; f2 = 0; f3 = 0; f4 = 0;
			//f1 = griewank f7
			double f11 = 0;
			double f12 = 1;
			for (int i = 0; i < d*0.2; i++)
			{
				f11 += (x[i] * x[i])/4000.0;
				f12 *= Math.cos(x[i] / Math.sqrt(i+1));
			}
			f1 = f11 - f12 + 1;
			//f2 = weierstrass f6
			f11 = 0;
			f12 = 0;
			double f13 = 0;
			for (int i = (int)(d*0.2); i < d*0.4; i++)
			{
				for (int j = 0; j <= 20; j++)
				{
					f11 += Math.pow(0.5,j)*Math.cos
							(2*Math.PI*Math.pow(3, j)*(x[i]+0.5));
				}
				f12 += f11;
			}
			for (int j = 0; j <= 20; j++)
			{
				f13 += Math.pow(0.5,j)*Math.cos
						(2*Math.PI*Math.pow(3, j)*(0.5));
			}
			f2 = f12 - ((int)(d*0.4) - (int)(d*0.2))*f13;
			//f3 = rastrigin f8
			for (int i = (int)(d*0.4); i < d*0.7; i++)
			{
				f3 += (x[i] * x[i]) - (10 * Math.cos(2 * Math.PI * x[i]));
			}
			f3 = (10 * d) + f3;
			//f4 = scaffers f14
			for (int i = (int)(d*0.7); i < d; i++)
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
				f4 += g1;
			}
			f[2] = f1 + f2 + f3 + f4;	
		
		
		//N = 3, p = [10,30,50], a = [0.25,1,1e-7]
		//bias = [0, 100, 200]
		//f4, f1, f2, f3,f1
		
		//F(x) = Sum of (L * [pi * fi(x) + bias i]),
		//where L = shift matrix
		//(WE IGNORE SHIFT, so L is removed)
		
		//double [] p = {10,20,30,40,50};
		double [] a = {1, 1, 1};
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
