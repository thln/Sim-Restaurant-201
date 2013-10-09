package restaurant;

import agent.Agent;
//import restaurant.Menu;



import java.util.*;
import java.util.concurrent.Semaphore;

//import restaurant.CookAgent.Food;
import restaurant.CustomerAgent.AgentState;

public class MarketAgent extends Agent
{
	/***** DATA *****/
	private Map<String, Integer> FoodInventory = new HashMap<String, Integer>();
	private Timer DeliveringTimer = new Timer();
	private CookAgent Cook;
	private class Delivery
	{
		public String food;
		public int WantedAmount;
		public int PossibleAmount;
		public boolean processed = false;
	}
	private List<Delivery> Deliveries;
	int deliveringTime;
	
	
	/***** MESSAGES *****/
	public void INeedMore(String food, int Quantity)
	{
		//STUB
	}

	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() 
	{
		//////FILL IN HERE
		for(Delivery D : Deliveries)
		{
			//STUB
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	/***** ACTIONS *****/
	private void ProcessItem(Delivery D)
	{
		//STUB
	}
}
