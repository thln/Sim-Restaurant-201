package restaurant;

import agent.Agent;
//import restaurant.Menu;







import java.util.*;
//import java.util.concurrent.Semaphore;

//import restaurant.CookAgent.Order;




//import restaurant.CookAgent.Food;
//import restaurant.CookAgent.state;
//import restaurant.CookAgent.Food;
//import restaurant.CustomerAgent.AgentState;

public class MarketAgent extends Agent
{
	/***** DATA *****/
	private Map<String, Food> FoodInventory = new HashMap<String, Food>();
	private Timer DeliveringTimer = new Timer();
	private CookAgent Cook;
	private String name;
	private enum DeliveryState
	{RequestReceived, Processing, ReadyToDeliver, Delivered};
	private boolean Deliver = false;
	
	private class Delivery
	{
		public String food;
		public int WantedAmount;
		public int PossibleAmount;
		//public boolean processed = false;
		public DeliveryState state = DeliveryState.RequestReceived;
		public Delivery(String f, int Q)
		{
			food = f;
			WantedAmount = Q;
			
		}
	}
	
	private class Food
	{
		public String name;
		public int Inventory; // = 10;
		public Food(String f, int I)
		{
			name = f;
			Inventory = I;
		}
		
		public void UseFood()
		{
			if(Inventory != 0)
			{
			Inventory--;
			}
		}
	
	}		
	private List<Delivery> Deliveries = new ArrayList<Delivery>();
	int deliveringTime = 5000;
	
	public MarketAgent(String name, CookAgent c)
	{
		super();
		this.name = name;
		
		Cook = c;
		
		//add in food

		//MenuForReference = new Menu();
		Food Salad = new Food("Salad", 10);
		Food Pizza = new Food("Pizza", 10);
		Food Chicken = new Food("Chicken", 10);
		Food Steak = new Food("Steak", 10);
		
		FoodInventory.put("Salad", Salad);
		FoodInventory.put("Pizza", Pizza);
		FoodInventory.put("Chicken", Chicken);
		FoodInventory.put("Steak", Steak);
	}
	
	
	
	
	/***** MESSAGES *****/
	public void INeedMore(String food, int Quantity)
	{
		//STUB
		print("Request Received.");
		Deliveries.add(new Delivery(food, Quantity));
		stateChanged();
	}

	
	
	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() 
	{
		//////FILL IN HERE
		for(Delivery D : Deliveries)
		{
			//STUB
			if(D.state == DeliveryState.RequestReceived)
			{
				D.state = DeliveryState.Processing;
				ProcessItem(D);
				return true;
			}
			
			if(D.state == DeliveryState.Processing && Deliver)
			{
				D.state = DeliveryState.Delivered;
				DeliverItem(D);
				return true;
			}
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
		//D.processed = true;
		for(int i=0; i <D.WantedAmount; i++)
		{
			FoodInventory.get(D.food).UseFood(); // = FoodInventory.get(D.food)-1:
			D.PossibleAmount++;
			if(FoodInventory.get(D.food).Inventory == 0)
			{
				break;
			}
		}
		
		print("We can give you " + D.PossibleAmount + " " + D.food);
		Cook.WeCanSupply(D.food, D.PossibleAmount);
		
		DeliveringTimer.schedule(new TimerTask() 
		{
			public void run() 
			{
				//Cook.deliverFood(D.food, D.PossibleAmount);
				//D.state = DeliveryState.ReadyToDeliver;
				//FIX
				Deliver = true;
				stateChanged();
			}
		},
		deliveringTime);
	}
	
	private void DeliverItem(Delivery D)
	{
		Deliver = false;
		print("Delivered: " + D.PossibleAmount + " " + D.food);
		Cook.deliverFood(D.food, D.PossibleAmount);
	}
}
