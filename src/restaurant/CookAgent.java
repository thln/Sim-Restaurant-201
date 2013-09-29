package restaurant;

import agent.Agent;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.CustomerAgent.AgentState;

public class CookAgent extends Agent {
	
	/***** DATA *****/
	public enum FoodState 
	{Pending, Cooking, Ready, Finished};
	
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
	public Timer CookTimer;
	public List <Order> orders = new ArrayList<Order>();
	public Map<String, Integer> RecipeBook;
	private String name;
	
	public CookAgent(String name)
	{
		super();
		this.name = name;
	}
	
	
	
	/***** MESSAGES *****/
	public void pleaseCook(String food, int table, WaiterAgent w)
	{
		/////FILL IN
		orders.add(new Order(food, table, w));
		stateChanged();
	}
	
	public void OrderIsFinished(Order o)
	{
		o.state = FoodState.Ready;
		stateChanged();
	}
	
	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() {
		//////FILL IN HERE
		for( Order order : orders)
		{
			if(order.state == FoodState.Ready)
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
		DoCooking(o);
		o.state = FoodState.Cooking;
		/////COOOKING TIMER
		OrderIsFinished(o);
	}
	
	private void DoCooking(Order o)
	{
		
	}
	
}
