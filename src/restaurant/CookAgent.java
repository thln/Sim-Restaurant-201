package restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import restaurant.gui.AnimationPanel;
import restaurant.interfaces.Market;
import agent.Agent;

public class CookAgent extends Agent 
{
	
	/***** DATA *****/
	public enum OrderState 
	{Pending, Cooking, Finished, NeedMore};
	
	public enum state
	{Cooking, Plating, Ordering};
	state S = state.Cooking;
	
	private class Order
	{
		public WaiterAgent w;
		public String food;
		public int table;
		OrderState state = OrderState.Pending;
		
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
		public boolean isLow = false;
		//Hack
		//Add in a max size
		public int MaximumSize = 5;
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
	
	private AnimationPanel animPanel;
	private Timer CookTimer  = new Timer();
	private List <Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	private List <Market> markets = Collections.synchronizedList(new ArrayList<Market>());
	private Map<String, Integer> RecipeBook  = new HashMap<String, Integer>();
	private Map<String, Food> FoodInventory = new HashMap<String, Food>();
	private String name;
	private int CurrentMarket = 0;
//	private String name;
	
	public CookAgent(String name)
	{
		super();
		this.name = name;
		
		RecipeBook.put("Salad",3000);
		RecipeBook.put("Pizza",5000);
		RecipeBook.put("Chicken",7000);
		RecipeBook.put("Steak",8000);
		
		Food Salad = new Food("Salad", 10, 1, 3000);
		Food Pizza = new Food("Pizza", 10, 1, 5000);
		Food Chicken = new Food("Chicken", 10, 1, 7000);
		Food Steak = new Food("Steak", 10, 1, 8000);
		
		FoodInventory.put("Salad", Salad);
		FoodInventory.put("Pizza", Pizza);
		FoodInventory.put("Chicken", Chicken);
		FoodInventory.put("Steak", Steak);
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setAnimationPanel(AnimationPanel aP)
	{
		this.animPanel = aP;
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
		if(quantity < FoodInventory.get(food).OrderSize)
		{
			//What to do here?
			print("Looks like we have to order more.");
			FoodInventory.get(food).OrderSize -= quantity;
			S = state.Ordering;
			stateChanged();
		}
		else
		{
			print ("Alright, cool. We don't have to order more.");
			FoodInventory.get(food).OrderSize = 10;
			FoodInventory.get(food).isLow = false;
		}
	}
	
	public void deliverFood(String food, int quantity)
	{
		//STUB
		FoodInventory.get(food).amount += quantity;
		print("Awesome, now I have " + FoodInventory.get(food).amount + " " + food );
	}
	
	public void PickedUpOrder(String food)
	{
		animPanel.removeFromPlated(food);
	}
	
	
	
	
	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() {
		//////FILL IN HERE
		
		if(S == state.Ordering)
		{
			CurrentMarket++;
			if(CurrentMarket == 3)
			{
				CurrentMarket = 0;
			}
			if(FoodInventory.get("Steak").isLow)
			{
				//CurrentMarket++;
				OrderFood("Steak");
				//return true;
			}
			if(FoodInventory.get("Chicken").isLow)
			{
				//CurrentMarket++;
				OrderFood("Chicken");
				//return true;
			}
			if(FoodInventory.get("Pizza").isLow)
			{
				//CurrentMarket++;
				OrderFood("Pizza");
				//return true;
			}
			if(FoodInventory.get("Salad").isLow)
			{
				//CurrentMarket++;
				OrderFood("Salad");
				//return true;
			}
			return true;
		}
			
		synchronized(orders)
		{
			for( Order order : orders)
			{
				if(order.state == OrderState.Cooking && S == state.Plating)
				{
					PlateIt(order);
					return true;
				}
				
				if(order.state == OrderState.Pending)
				{
					CookIt(order);
					return true;
				}
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
		o.state = OrderState.Finished;
	}
	
	private void DoPlate(Order o)
	{
		////Animate plate?
		animPanel.addToPlated(o.food);
	}
	
	public void CookIt(Order o)
	{
		
		//Out of Food
		if(FoodInventory.get(o.food).amount == 0)
		{
			orders.remove(o);
			print("We are out of " + o.food);
			o.w.OutOfFood(o.table, o.food);
			return;
		}
		
		FoodInventory.get(o.food).UseFood();
		print("Using " + o.food +", inventory is now " + FoodInventory.get(o.food).amount);
		
		//Check Food Inventory for low
		if(FoodInventory.get(o.food).amount <= FoodInventory.get(o.food).low)
		{
			FoodInventory.get(o.food).isLow = true;
			OrderFood(o.food);
		}
	
		o.state = OrderState.Cooking;
		animPanel.addToCooking(o.food);
		CookTimer.schedule(new TimerTask() 
		{
			public void run() 
			{
				//o.state = FoodState.Ready;
				S = state.Plating;
				stateChanged();
			}
		},
		FoodInventory.get(o.food).cookingtimer);
	}
	
	public void OrderFood(String foodItem)
	{
		S = state.Cooking;
		print("We are low on " + foodItem + ". Let's order " + FoodInventory.get(foodItem).OrderSize + " more from " + markets.get(CurrentMarket) + "!");
		//Implement a mechanism to choose between markets
		markets.get(CurrentMarket).INeedMore(foodItem, FoodInventory.get(foodItem).OrderSize);
	}
	
	public void addMarket(MarketAgent m)
	{
		markets.add(m);
	}
		
	public void setLow()
	{
		FoodInventory.get("Steak").amount = 2;
		FoodInventory.get("Chicken").amount = 2;
		FoodInventory.get("Pizza").amount = 2;
		FoodInventory.get("Salad").amount = 2;
	}
	
	public void setEmpty()
	{
		FoodInventory.get("Steak").amount = 0;
		FoodInventory.get("Chicken").amount = 0;
		FoodInventory.get("Pizza").amount = 0;
		FoodInventory.get("Salad").amount = 0;	
		FoodInventory.get("Steak").isLow = true;
		OrderFood("Steak");
		FoodInventory.get("Chicken").isLow = true;
		OrderFood("Chicken");
		FoodInventory.get("Pizza").isLow = true;
		OrderFood("Pizza");
		FoodInventory.get("Salad").isLow = true;
		OrderFood("Salad");
	}
}
