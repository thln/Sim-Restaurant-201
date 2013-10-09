package restaurant;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class Menu 
{

	
	public String choices[] = {"Steak", "Chicken", "Salad", "Pizza"};
	public double prices[] = {15.99, 10.99, 5.99, 8.99};
	public int size = 4;
	
	
	public String blindPick()
	{	
		Random randomizer = new Random();
		int randomNumber = randomizer.nextInt(size-1);
		//return choices[(int)Math.random()%size];
		return choices[randomNumber];
	}

	public double GetPrice(String Choice)
	{
		double nothing = 0;
		if(Choice.equals("Steak"))
		{
			return prices[0];
		}
		else if(Choice.equals("Chicken"))
		{
			return prices[1];
		}
		else if(Choice.equals("Salad"))
		{
			return prices[2];
		}
		else if(Choice.equals("Pizza"))
		{
			return prices[3];
		}
		
		return nothing;
	}
}
