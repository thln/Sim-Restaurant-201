package agent;
import java.util.Random;

public class Menu {

	
	public String choices[] = {"Steak", "Chicken", "Salad", "Pizza"};
	public double prices[] = {15.99, 10.99, 5.99, 8.99};
	public int size = 4;
	
	public String blindPick()
	{
		Random rand = new Random();
		
		return choices[rand.nextInt(size)];
	}
	
}
