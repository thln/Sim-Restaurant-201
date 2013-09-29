package restaurant;

import agent.Agent;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.CustomerAgent.AgentState;

public class CookAgent extends Agent {
	
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
	private Timer CookTimer  = new Timer();
	private List <Order> orders = new ArrayList<Order>();
	private Map<String, Integer> RecipeBook  = new HashMap<String, Integer>();
	public String name;
	
	public CookAgent(String name)
	{
		super();
		this.name = name;
		
		RecipeBook.put("Salad", 3000);
		RecipeBook.put("Pizza",5000);
		RecipeBook.put("Chicken",7000);
		RecipeBook.put("Steak",8000);	
	}
	
	
	
	/***** MESSAGES *****/
	public void pleaseCook(String food, int table, WaiterAgent w)
	{
		/////FILL IN
		orders.add(new Order(food, table, w));
		stateChanged();
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
		RecipeBook.get(o.food));
		/////COOOKING TIMER
		//o.state = FoodState.Ready;
		//stateChanged();
	}
	
}
