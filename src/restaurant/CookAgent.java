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
	
	
	/***** MESSAGES *****/
	public void pleaseCook(String food, int table, WaiterAgent w)
	{
		/////FILL IN
	}
	
	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() {
		//////FILL IN HERE

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	
	/***** ACTIONS *****/
	public void PlateIt(Order o)
	{
		/////FILL IN HERE
	}
	
	public void CookIt(Order o)
	{
		/////FILL IN HERE
	}
	
}
