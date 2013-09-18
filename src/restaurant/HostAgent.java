package restaurant;


import agent.Agent;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.CustomerAgent.AgentState;

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
	}
	
	public String getName() 
	{
		return name;
	}

	public Collection getTables() 
	{
		return tables;
	}
	
	
	/***** MESSAGES *****/
	public void IWantFood(CustomerAgent cust)
	{
		///////FILL IN HERE
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

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	/***** ACTIONS *****/
	
	private void seatCustomer(MyCustomer mc, Table t)
	{
		/////FILL IN HERE
	}
	
}
