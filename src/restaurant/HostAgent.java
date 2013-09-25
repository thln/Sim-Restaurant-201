package restaurant;


import agent.Agent;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.CustomerAgent.AgentState;
//import restaurant.WaiterAgent.Table;

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
	
	private class MyWaiter
	{
		public WaiterAgent w1;
		public int NumberOfCustomers;
//		public MyWaiter(String name)
//		{
//			w1 = new WaiterAgent(name);
//		}
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
	private int NWAITERS = 1;
	private String name;
	
	public void addWaiter(WaiterAgent w1)
	{
		MyWaiters.add(new MyWaiter(w1));
	}
	
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
		if(NTABLES < 4)
		{
			NTABLES++;
			tables.add(new Table(NTABLES));
		}
	}
	
	
	/***** MESSAGES *****/
	public void IWantFood(CustomerAgent cust)
	{
		///////FILL IN HERE
		MyCustomers.add(new MyCustomer(cust));
		stateChanged();
	}
	
	public void TableIsFree(Table t)
	{
		///////FILL IN HERE
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	
	protected boolean pickAndExecuteAnAction() {
		//////FILL IN HERE
		
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
	
	private void seatCustomer(MyCustomer mc, Table t)
	{
		/////FILL IN HERE
		//Add in way to look through waiter list
		
		MyWaiters.get(0).w1.pleaseSeatCustomer(mc.c, t.tableNumber);
		print("Message 2 Sent");
		mc.state = CustomerState.Seated;
	}
	
}
