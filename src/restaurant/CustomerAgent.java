package restaurant;

import restaurant.gui.CustomerGui;
//import restaurant.gui.RestaurantGui;
import agent.Agent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;


/**
 * Restaurant customer agent.
 */
public class CustomerAgent extends Agent 
{
	private String name;
	private int hungerLevel = 5;        // determines length of meal
	Timer timer = new Timer();
	private CustomerGui customerGui;
	private int currentTable;
	private Menu myMenu;
	private String myOrder;
	private Semaphore waitingForWaiter = new Semaphore(0, true);
	//private Semaphore GoingToCashier = new Semaphore(0, true);
	private int DecidingFoodTime = 8000;
	private int SpeakingFoodTime = 2000;
	private int EatingFoodTime = 5000;
	private Check myCheck;
	private double Cash = 20.00; //8.00; //4.00;
	private double Debt = 0.00; //What Do I do with debt
	public boolean DineAndDash = false; // true; //false;
	public boolean WaitingToBeSeated = false;
	
	// agent correspondents
	private WaiterAgent waiter;
	private HostAgent host;
	private CashierAgent cashier;

	//    private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, ChoosingOrder, ReadyToOrder, GivingOrder, SayingOrder, Ordering, WaitingForFood, FoodReceived, Eating, AskedForCheck, WaitingForCheck, CheckReceived, GoingToCashier, Paying, donePaying, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, gotHungry, followMe, seated, doneEating, atCashier, doneLeaving};
	AgentEvent event = AgentEvent.none;

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public CustomerAgent(String name, HostAgent h, CashierAgent cas)
	{
		super();
		this.name = name;
		
		this.host = h;
		this.cashier = cas;
	}

	/**
	 * hack to establish connection to Waiter agent.
	 */
	public void setWaiter(WaiterAgent waiter) 
	{
		this.waiter = waiter;
	}
	
	public void setHost(HostAgent host)
	{
		this.host = host;
	}

	public String getCustomerName() 
	{
		return name;
	}
	
	
	
	
	///// Messages

	public void gotHungry() 
	{//from animation
		print("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}

	public void followMe(Menu m, WaiterAgent w, int t) 
	{
		WaitingToBeSeated = false;
		print("Message 3 Sent - Following Waiter");
		myMenu = m;
		waiter = w;
		currentTable = t;
		event = AgentEvent.followMe;
		stateChanged();
	}
	
	public void WhatDoYouWant()
	{
		print("Message 5 Sent - Ordering Food");
		waitingForWaiter.release();
		//state = AgentState.GivingOrder;
		stateChanged();
	}
	
	public void OutOfChoice(String o)
	{
		myMenu.FoodMenu.get(o).setUnavailable();
		print("Okay, I will reorder.");
		state = AgentState.ReadyToOrder;
		stateChanged();
		/*
		print("Okay, I will leave.");
		//Hack, should reorder
		state = AgentState.Eating;
		event = AgentEvent.doneEating;
		stateChanged();	
		*/	
		
	}

	public void HereIsYourOrder(String o)
	{
		state = AgentState.FoodReceived;
		stateChanged();
	}
	
	//Cashier Stuff
	public void HereIsYourCheck(Check ch)
	{
		myCheck = ch;
		customerGui.DoOrder("Check");
		state = AgentState.CheckReceived;
		stateChanged();
	}
	
	//Cashier stuff, Getting Change
	public void HereIsYourChange(double c, double d)
	{
		Cash = c;
		Debt = d; //+=
		print("I am receiving $" + c + " as change and I have $" + d + " as my debt");
		print("I have $" + Cash + " and $" + Debt + " as debt");
		//What happens if you have a debt?
		state = AgentState.donePaying;
		stateChanged();
	}
	
	public void ImpatientNoMoreSeats()
	{
		print("It's a full restaurant, I think I'm just going to leave.");
		state = AgentState.donePaying;
		event = AgentEvent.atCashier;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToSeat() 
	{
		//from animation
		event = AgentEvent.seated;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToCashier()
	{
		event = AgentEvent.atCashier;
		stateChanged();
	}
	
	public void msgAnimationFinishedLeaveRestaurant() 
	{
		//from animation
		event = AgentEvent.doneLeaving;
		stateChanged();
	}
	
	
	
	
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() 
	{
		//	CustomerAgent is a finite state machine
		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry )
		{
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followMe )
		{
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
		if (state == AgentState.BeingSeated && event == AgentEvent.seated)
		{
			state = AgentState.ChoosingOrder;
			choosingOrderTime();
			return true;
		}
		if(state == AgentState.ReadyToOrder && event == AgentEvent.seated)
		{
			state = AgentState.GivingOrder;
			chooseOrder();
			return true;
		}
		if(state == AgentState.GivingOrder && event == AgentEvent.seated)
		{
			state = AgentState.SayingOrder;
			givingOrderTime();
			return true;
		}
		if(state == AgentState.Ordering && event == AgentEvent.seated)
		{
			state = AgentState.WaitingForFood;
			IWantToOrder(myOrder);
			return true;
		}
		if(state == AgentState.FoodReceived && event == AgentEvent.seated)
		{
			state = AgentState.Eating;
			EatFood();
			return true;
		}
		//NEED TO GO THROUGH Seated, ChoosingOrder, Ordering, waitingforFood, then go to Eating
		if (state == AgentState.Eating && event == AgentEvent.doneEating)
		{
			///change from here
			state = AgentState.AskedForCheck;
			AskForCheck();
			return true;
			/*
			state = AgentState.Leaving;
			leaveTable();
			return true;
			*/
		}
		
		if(state == AgentState.CheckReceived && event == AgentEvent.doneEating)
		{
			state = AgentState.GoingToCashier;
			HeadToCashier();
			return true;
			
		}
		
		if(state == AgentState.GoingToCashier && event == AgentEvent.atCashier)
		{
			state = AgentState.Paying;
			PayForFood();
			return true;
			
		}
		
		if(state == AgentState.donePaying && event == AgentEvent.atCashier)
		{
			state = AgentState.Leaving;
			leaveTheRestaurant();
			return true;
		}
		
		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving)
		{
			state = AgentState.DoingNothing;
			event = AgentEvent.none;
			//no action
			return true;
		}
		return false;
	}

	
	
	
	
	///// Actions

	private void goToRestaurant() 
	{
		Do("Going to restaurant");
		//waiter.msgIWantFood(this);//send our instance, so he can respond to us
		host.IWantFood(this);
		WaitingToBeSeated = true;
	}

	private void SitDown() 
	{
		Do("Being seated. Going to table");
		customerGui.DoGoToSeat(currentTable);//hack; only one table
	}
	
	private void choosingOrderTime()
	{
		timer.schedule(new TimerTask() 
		{
			public void run() 
			{
				state = AgentState.ReadyToOrder;
				stateChanged();
			}
		},
		DecidingFoodTime);
		
	}
	
	private void chooseOrder()
	{
		myOrder = myMenu.blindPick();
		if((Cash<myMenu.FoodMenu.get("Salad").price) && !DineAndDash)
		{
			print("Oh man, I can't even afford a salad. I should leave.");
			myOrder = "";
			customerGui.DoOrder("");
			waiter.iAmLeavingTable(this);
			state = AgentState.donePaying;
			event = AgentEvent.atCashier;
			stateChanged();
			return;
		}
		if((Cash<myMenu.FoodMenu.get("Pizza").price) && !DineAndDash && !(myMenu.FoodMenu.get("Salad").Available))
		{
			print("I only have enough for a salad. But they're out! I'll leave.");
			myOrder = "";
			customerGui.DoOrder("");
			waiter.iAmLeavingTable(this);
			state = AgentState.donePaying;
			event = AgentEvent.atCashier;
			stateChanged();
			return;
		}		
		if((Cash<myMenu.FoodMenu.get("Pizza").price) && !DineAndDash && (myMenu.FoodMenu.get("Salad").Available))
		{
			print("I only have enough for a salad. I'll get that.");
			myOrder = "Salad";
		}
		
		
		waiter.ReadyToOrder(this);
		
		
		//Hack to Check different foods
		if(name.equals("Salad"))
		{
			myOrder = "Salad";
		}
		if(name.equals("Pizza"))
		{
			myOrder = "Pizza";
		}
		if(name.equals("Chicken"))
		{
			myOrder = "Chicken";
		}
		if(name.equals("Steak"))
		{
			myOrder = "Steak";
		}
		
		print("Message 4 Sent - Chosen Order");
		try 
		{
			waitingForWaiter.acquire();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void givingOrderTime()
	{
		timer.schedule(new TimerTask() 
		{
			public void run() 
			{
				state = AgentState.Ordering;
				stateChanged();
			}
		},
		SpeakingFoodTime);
	}
	
	private void IWantToOrder(String order)
	{
				state = AgentState.WaitingForFood;
				waiter.myChoiceIs(order, this);
				customerGui.DoOrder(order+"?");
				print("Message 6 Sent - Gave Order: " + order);
				stateChanged();
	}

	private void EatFood() 
	{
		customerGui.DoOrder(myOrder);
		Do("Eating Food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() 
		{
			Object cookie = 1;
			public void run() 
			{
				print("Done eating, cookie=" + cookie);
				event = AgentEvent.doneEating;
				//isHungry = false;
				stateChanged();
			}
		},
		EatingFoodTime);//getHungerLevel() * 1000);//how long to wait before running task
	}
	
	private void AskForCheck()
	{
		customerGui.DoOrder("Check?");
		waiter.CanIGetMyCheck(this);
	}
	
	private void HeadToCashier()
	{
		currentTable = 0;
		myMenu = null;
		myOrder = "Check";
		waiter.iAmLeavingTable(this);
		customerGui.GoToCashier();
	}
	
	private void PayForFood()
	{
		myOrder = "";
		customerGui.DoOrder("");
		if(Debt > 0)
		{
			myCheck.cost += Debt;
			print("I have a debt. I will add it to the bill.");
		}
		print("I am giving the cashier $" + Cash);
		cashier.HereIsPayment(myCheck, Cash);
		Cash = 0;
	}

	private void leaveTheRestaurant() 
	{
		//customerGui.DoOrder("");
		//Breaking up this method
		//currentTable = 0;
		//myMenu = null;
		//myOrder = "";
		Do("Leaving.");
		//waiter.iAmLeavingTable(this);
		print("Message 10 Sent - I am leaving");
		customerGui.DoExitRestaurant();
	}

	// Accessors, etc.

	public String getName() 
	{
		return name;
	}
	
	public int getHungerLevel() 
	{
		return hungerLevel;
	}

	public void setCash(double number)
	{
		Cash = number;
	}
	
	public void setHungerLevel(int hungerLevel) 
	{
		this.hungerLevel = hungerLevel;
		//could be a state change. Maybe you don't
		//need to eat until hunger lever is > 5?
	}

	public void setCurrentTable(int table)
	{
		currentTable = table;
	}
	
	public int getCurrentTable()
	{
		return currentTable;
	}
	
	public String toString() 
	{
		return "customer " + getName();
	}

	public void setGui(CustomerGui g) 
	{
		customerGui = g;
	}

	public CustomerGui getGui() 
	{
		return customerGui;
	}
}

