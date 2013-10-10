package restaurant;

import agent.Agent;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.CustomerAgent.AgentState;

public class CookAgent extends Agent 
{
	
	/***** DATA *****/
	public enum FoodState 
	{Pending, Cooking, Finished};
	
	public enum state
	{Cooking, Plating};
	state S = state.Cooking;
	
	private class Order
	{
		public WaiterAgent w;
		public String food;
		public int table;
		FoodState state = FoodState.Pending;
		
		public Order(String foodchoice, int tablenumber, WaiterAgent waiter)
		{
			w = waiter;
			food = foodchoice;
			table = tablenumber;
		}
	}
	
	private class Food
	{
		public int amount;
		public int low;
		//Hack
		//Add in a max size
		public int OrderSize = 10;
		public int cookingtimer;
		public String type;
		
		public Food(String type1, int amount1, int low1, int cookingtimer1)
		{
			type = type1;
			amount = amount1;
			low = low1;
			cookingtimer = cookingtimer1;
		}
		
		public void UseFood()
		{
			amount--;
		}
		
		public void AddFood(int number)
		{
			amount=+number;
		}
	}
	
	private Timer CookTimer  = new Timer();
	private List <Order> orders = new ArrayList<Order>();
	private List <MarketAgent> markets = new ArrayList<MarketAgent>();
	private Map<String, Integer> RecipeBook  = new HashMap<String, Integer>();
	private Map<String, Food> FoodInventory = new HashMap<String, Food>();
	private String name;
//	private String name;
	
	public CookAgent(String name)
	{
		super();
		this.name = name;
		
		RecipeBook.put("Salad",3000);
		RecipeBook.put("Pizza",5000);
		RecipeBook.put("Chicken",7000);
		RecipeBook.put("Steak",8000);
		
		Food Salad = new Food("Salad", 2, 1, 3000);
		Food Pizza = new Food("Pizza", 2, 1, 5000);
		Food Chicken = new Food("Chicken", 2, 1, 7000);
		Food Steak = new Food("Steak", 2, 1, 8000);
		
		FoodInventory.put("Salad", Salad);
		FoodInventory.put("Pizza", Pizza);
		FoodInventory.put("Chicken", Chicken);
		FoodInventory.put("Steak", Steak);
	}
	
	
	
	/***** MESSAGES *****/
	public void pleaseCook(String food, int table, WaiterAgent w)
	{
		/////FILL IN
		orders.add(new Order(food, table, w));
		stateChanged();
	}
	
	public void WeCanSupply(String food, int quantity)
	{
		//STUB
		//Fix, order from other market if necessary
		print ("Alright, cool.");
	}
	
	public void deliverFood(String food, int quantity)
	{
		//STUB
		FoodInventory.get(food).amount += quantity;
		print("Awesome, now I have " + FoodInventory.get(food).amount + " " + food );
	}
	
	
	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() {
		//////FILL IN HERE
		for( Order order : orders)
		{
			if(order.state == FoodState.Cooking && S == state.Plating)
			{
				PlateIt(order);
				return true;
			}
			
			if(order.state == FoodState.Pending)
			{
				CookIt(order);
				return true;
			}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	
	/***** ACTIONS *****/
	public void PlateIt(Order o)
	{
		/////FILL IN HERE
		S = state.Cooking;
		DoPlate(o);
		o.w.OrderIsReady(o.food, o.table);
		print("Message 8 Sent, Food is Ready" + name);
		/////Why is the print coming from a "CookAgent address"
		o.state = FoodState.Finished;
	}
	
	private void DoPlate(Order o)
	{
		////Animate plate?
	}
	
	public void CookIt(Order o)
	{
		/////FILL IN HERE
		
		//Out of Food
		if(FoodInventory.get(o.food).amount == 0)
		{
			orders.remove(o);
			print("We are out of " + o.food);
			o.w.OutOfFood(o.table, o.food);
			return;
		}
		
		/*
		if(FoodInventory.get(o.food).amount == FoodInventory.get(o.food).low)
		{
			print("We are low on " + o.food + ". Let's order " + FoodInventory.get(o.food).OrderSize + " more!");
			markets.get(0).INeedMore(o.food, FoodInventory.get(o.food).OrderSize);
		}*/
		
		FoodInventory.get(o.food).UseFood();
		print("Using " + o.food +", inventory is now " + FoodInventory.get(o.food).amount);
		
		if(FoodInventory.get(o.food).amount == FoodInventory.get(o.food).low)
		{
			print("We are low on " + o.food + ". Let's order " + FoodInventory.get(o.food).OrderSize + " more!");
			markets.get(0).INeedMore(o.food, FoodInventory.get(o.food).OrderSize);
		}
		
		//Check Food Inventory for low
		o.state = FoodState.Cooking;
		CookTimer.schedule(new TimerTask() 
		{
			public void run() 
			{
				//o.state = FoodState.Ready;
				S = state.Plating;
				stateChanged();
			}
		},
		//RecipeBook.get(o.food));
		FoodInventory.get(o.food).cookingtimer);
		/////COOOKING TIMER
		//o.state = FoodState.Ready;
		//stateChanged();
	}
	
	
	public void addMarket(MarketAgent m)
	{
		markets.add(m);
	}
	
}
