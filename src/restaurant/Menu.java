package restaurant;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

//import restaurant.CookAgent.Food;

//import restaurant.CookAgent.Food;

public class Menu 
{

	
	public String choices[] = {"Steak", "Chicken", "Salad", "Pizza"};
	public double prices[] = {15.99, 10.99, 5.99, 8.99};
	public int size = 4;
	public class Food
	{
		public String type;
		public boolean Available = true;
		public double price;
		
		public Food(String name, double cost)
		{
			type = name;
			price = cost;
		}
		
		public void setAvailable()
		{
			Available = true;
		}
		
		public void setUnavailable()
		{
			Available = false;
		}
	}
	
	public Map<String, Food> FoodMenu = new HashMap<String, Food>();
	
	
	public Menu()
	{
		Food Salad = new Food("Salad", 5.99);
		Food Pizza = new Food("Pizza", 8.99);
		Food Chicken = new Food("Chicken", 10.99);
		Food Steak = new Food("Steak", 15.99);
		
		FoodMenu.put("Salad", Salad);
		FoodMenu.put("Pizza", Pizza);
		FoodMenu.put("Chicken", Chicken);
		FoodMenu.put("Steak", Steak);
		
	}
	
	public String blindPick()
	{	
		Random randomizer = new Random();
		int randomNumber = randomizer.nextInt(size-1);
		//return choices[(int)Math.random()%size];
		if(FoodMenu.get(choices[randomNumber]).Available)
		{
			return choices[randomNumber];
		}
		else
		{
			return blindPick();
		}
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
