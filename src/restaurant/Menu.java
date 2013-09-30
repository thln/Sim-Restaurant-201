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
	
}
