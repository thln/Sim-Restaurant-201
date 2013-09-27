package restaurant;

import restaurant.gui.CustomerGui;
import restaurant.gui.RestaurantGui;
import agent.Agent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Restaurant customer agent.
 */
public class CustomerAgent extends Agent {
	private String name;
	private int hungerLevel = 5;        // determines length of meal
	Timer timer = new Timer();
	private CustomerGui customerGui;
	private int currentTable;
	private Menu myMenu;
	private String myOrder;
	
	// agent correspondents
	private WaiterAgent waiter;
	private HostAgent host;

	//    private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, ChoosingOrder, Ordering, WaitingForFood, FoodReceived, Eating, DoneEating, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, gotHungry, followMe, seated, doneEating, doneLeaving};
	AgentEvent event = AgentEvent.none;

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public CustomerAgent(String name){
		super();
		this.name = name;
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
		print("Message 3 Sent");
		print("Following Waiter");
		myMenu = m;
		waiter = w;
		currentTable = t;
		event = AgentEvent.followMe;
		stateChanged();
	}
	
	public void WhatDoYouWant()
	{
		print("Message 5 Sent");
		state = AgentState.Ordering;
		stateChanged();
	}

	public void HereIsYourOrder(String o)
	{
		state = AgentState.FoodReceived;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToSeat() {
		//from animation
		event = AgentEvent.seated;
		stateChanged();
	}
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		event = AgentEvent.doneLeaving;
		stateChanged();
	}

	
	
	
	
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a finite state machine

		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followMe ){
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
		if (state == AgentState.BeingSeated && event == AgentEvent.seated){
			state = AgentState.ChoosingOrder;
			chooseOrder();
			return true;
		}
		if(state == AgentState.Ordering && event == AgentEvent.seated)
		{
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
		
		if (state == AgentState.Eating && event == AgentEvent.doneEating){
			state = AgentState.Leaving;
			leaveTable();
			return true;
		}
		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
			state = AgentState.DoingNothing;
			//no action
			return true;
		}
		return false;
	}

	
	
	
	
	///// Actions

	private void goToRestaurant() {
		Do("Going to restaurant");
		//waiter.msgIWantFood(this);//send our instance, so he can respond to us
		host.IWantFood(this);
	}

	private void SitDown() {
		Do("Being seated. Going to table");
		customerGui.DoGoToSeat(currentTable);//hack; only one table
	}
	
	private void chooseOrder()
	{
		myOrder = myMenu.blindPick();
		waiter.ReadyToOrder(this);
		print("Message 4 Sent");
	}
	
	private void IWantToOrder(String order)
	{
		waiter.myChoiceIs(order, this);
		print("Message 6 Sent");
		state = AgentState.WaitingForFood;
	}

	private void EatFood() {
		Do("Eating Food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done eating, cookie=" + cookie);
				event = AgentEvent.doneEating;
				//isHungry = false;
				stateChanged();
			}
		},
		5000);//getHungerLevel() * 1000);//how long to wait before running task
	}

	private void leaveTable() {
		Do("Leaving.");
		waiter.iAmLeavingTable(this);
		print("Message 10 Sent");
		customerGui.DoExitRestaurant();
	}

	// Accessors, etc.

	public String getName() {
		return name;
	}
	
	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
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
	
	public String toString() {
		return "customer " + getName();
	}

	public void setGui(CustomerGui g) {
		customerGui = g;
	}

	public CustomerGui getGui() {
		return customerGui;
	}
}

