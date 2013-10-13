package restaurant;


import agent.Agent;

import java.util.*;
//import java.util.concurrent.Semaphore;

//import restaurant.CustomerAgent.AgentState;
//import restaurant.WaiterAgent.Table;
//import restaurant.WaiterAgent.MyCustomer;
//import restaurant.WaiterAgent.myCustomerState;

/** 
 * Restaurant HostAgent
  */
//This is the Host Agent

public class HostAgent extends Agent
{
	/***** DATA *****/
	public enum CustomerState 
	{Waiting,Seated, Left};
	
	private class MyCustomer
	{
		public CustomerAgent c;
		private CustomerState state = CustomerState.Waiting;
		public MyCustomer(CustomerAgent cust)
		{
			c = cust;
		}
	}

	public enum WaiterState {Working, Asked, onBreak};
	//public boolean CheckingWaiters = false;
	
	private class MyWaiter
	{
		public WaiterAgent w1;
		//used possibly to go through the waiterlist and reorganize the customers
		public int NumberOfCustomers;
		private WaiterState state = WaiterState.Working;
		//public boolean Working = true;
		public MyWaiter(WaiterAgent w)
		{
			w1 = w;
		}
	}
	
	private class Table 
	{
		CustomerAgent occupiedBy;
		int tableNumber;

		Table(int tableNumber) 
		{
			this.tableNumber = tableNumber;
		}

		void setOccupant(CustomerAgent cust) 
		{
			occupiedBy = cust;
		}

		void setUnoccupied() 
		{
			occupiedBy = null;
		}

		CustomerAgent getOccupant()
		{
			return occupiedBy;
		}

		boolean isOccupied() 
		{
			return occupiedBy != null;
		}

		public String toString() 
		{
			return "table " + tableNumber;
		}
	}
	
	public List<MyCustomer> MyCustomers = new ArrayList<MyCustomer>();
	public List<MyWaiter> MyWaiters = new ArrayList<MyWaiter>();
	public Collection<Table> tables;
	private int NTABLES = 1;
	private int MAXTABLES = 9;
	private int currentWaiter = 0;
	private int currentNumberOfCustomers = 0;
	//private int NWAITERS = 1;
	private String name;
	
	public HostAgent(String name) 
	{
		super();

		this.name = name;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) 
		{
			tables.add(new Table(ix));//how you add to a collections
		}

		//make waiters
		/*
		for (int ix = 1; ix <= NWAITERS; ix++) 
		{
			MyWaiters.add(new MyWaiter("Joe"));//how you add to a collections
		}
		*/
		
	}
	
	public String getName() 
	{
		return name;
	}

	public Collection getTables() 
	{
		return tables;
	}
	
	public void addTables()
	{
		//ONLY 9 TABLES ALLOWED ATM
		if(NTABLES < MAXTABLES)
		{
			NTABLES++;
			tables.add(new Table(NTABLES));
			print("Number of tables " + NTABLES);
		}
	}
	
	
	/***** MESSAGES *****/
	public void newWaiter(WaiterAgent wa)
	{
		MyWaiters.add(new MyWaiter(wa));
		print(MyWaiters.size() + " Number of Waiters");
		stateChanged();
	}
	
	//WAITER ON BREAK STUFF ******************************
	public void CanIGoOnBreak(WaiterAgent wa)
	{
		//Pass in to check new waiter
		for(MyWaiter mw : MyWaiters)
		{
			if(mw.w1 == wa)
			{
				mw.state = WaiterState.Asked;
				stateChanged();
			}
		}
	}

	//WAITER ON BREAK STUFF ******************************
	public void BackToWork(WaiterAgent wa)
	{
		for(MyWaiter mw : MyWaiters)
		{
			if(mw.w1 == wa)
			{
				mw.state = WaiterState.Working;
				//mw.Working = true;
				stateChanged();
			}
		}
		
	}
	
	public void IWantFood(CustomerAgent cust)
	{
		///////FILL IN HERE
		MyCustomers.add(new MyCustomer(cust));
		print("Message 1 Sent - Someone's hungry");
		stateChanged();
	}
	
	public void TableIsFree(int t, WaiterAgent w)
	{
		///////FILL IN HERE
		for(Table table : tables)
		{
			if(table.tableNumber == t)
			{
				for(MyCustomer mc : MyCustomers)
				{
					if(mc.c == table.getOccupant())
					{
						for(MyWaiter mw : MyWaiters)
						{
							if(mw.w1 == w)
							{
								mw.NumberOfCustomers--;
								updateNumberOfCustomers();
								mc.state = CustomerState.Left;
								table.setUnoccupied();
								stateChanged();
							}
						}
					}
					
				}
			}
		}
	}
	
	
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	
	protected boolean pickAndExecuteAnAction() 
	{
		//////FILL IN HERE
		//WAITER ON BREAK STUFF ******************************
		for(MyWaiter mw : MyWaiters)
		{
			if(mw.state == WaiterState.Asked)
			{
				CheckWaiters(mw);
				return true;
			}
		}
		
		
		for(MyCustomer mc : MyCustomers)
		{
			//Checking all MyCustomers in list for anyone who is waiting
			if(mc.state == CustomerState.Waiting)
			{
				for(Table table : tables)
				{
					//Checking all Tables in list for any empty tables
					if(!(table.isOccupied()))
					{
						//if there is an empty table and waiting customer, seat customer
						seatCustomer(mc, table);
						return true;
					}
				}
			}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	/***** ACTIONS *****/
	//WAITER ON BREAK STUFF ******************************
	private void CheckWaiters(MyWaiter mw)
	{
		for(MyWaiter waiter : MyWaiters)
		{
			if(waiter.state == WaiterState.Working)
			{
				mw.w1.AllowedToGoOnBreak(true);
				mw.state = WaiterState.onBreak;
				//should I?
				//stateChanged();
				return;
			}
		}
		
		mw.w1.AllowedToGoOnBreak(false);
		mw.state = WaiterState.Working;
		stateChanged();
	}
	
	private void seatCustomer(MyCustomer mc, Table t)
	{
		/////FILL IN HERE
		//Add in way to look through waiter list
		if(MyWaiters.isEmpty())
		{
			//print("We currently have no waiters available.");
		}
	
		else 
		{
			
			if(MyWaiters.get(currentWaiter).w1.AtFrontDesk && MyWaiters.get(currentWaiter).state == WaiterState.Working)
			{
			MyWaiters.get(currentWaiter).NumberOfCustomers++;
			updateNumberOfCustomers();
			MyWaiters.get(currentWaiter).w1.pleaseSeatCustomer(mc.c, t.tableNumber);
			print("Message 2 Sent " + mc.c.getName() + " " + mc.state);
			//print("currentWaiter number : " + currentWaiter + " and size of waiterlist : " + MyWaiters.size());
				mc.state = CustomerState.Seated;
				for(Table table : tables)
				{
					if(table == t)
					{
						table.setOccupant(mc.c);
					}
				}
			}
			//keeps track of current waiter, if currentwaiter is last waiter on list, resets to first waiter
			//automatically divide the customers among the number of waiters
			currentWaiter++;
			if(currentWaiter == MyWaiters.size())
			{
				currentWaiter = 0;
			}
		}
	
		stateChanged();
	}
	
	private void updateNumberOfCustomers()
	{
		currentNumberOfCustomers = 0;
		for(MyWaiter mw : MyWaiters)
		{
			currentNumberOfCustomers += mw.NumberOfCustomers;
		}
		print("The current Number of Customers is " + currentNumberOfCustomers);
	}

	public boolean FullRestaurant()
	{		
		if(currentNumberOfCustomers == NTABLES)
		{
			return true;
		}
		
		return false;
	}
}
