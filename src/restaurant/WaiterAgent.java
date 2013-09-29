package restaurant;

import agent.Agent;
import restaurant.HostAgent.CustomerState;
import restaurant.gui.WaiterGui;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Waiter Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.

//This is now a Waiter Agent. We are implementing the Host separately. 9/18/13
public class WaiterAgent extends Agent 
{
	//static final int NTABLES = 3;//a global for the number of tables.
	private int NTABLES = 1;
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<MyCustomer> myCustomers
	= new ArrayList<MyCustomer>();
	public Collection<Table> tables;
	private boolean AtFrontDesk = false;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	//Agent Correspondents
	private HostAgent host;
	private CookAgent cook;
	
	
	//Checks for state, since there are only two states, boolean works
	//private boolean bringingCustomer = false; 
	private String name;
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore receivingOrder = new Semaphore(0, true);
	private Semaphore atKitchen = new Semaphore(0, true);
	//private Semaphore atFrontDesk = new Semaphore(0, true);
	
	public WaiterGui waiterGui = null;
	
	public enum myCustomerState 
	{Waiting, Seated, readyToOrder, TakingOrder, OrderReceived, OrderSent, DeliveringMeal, Eating, Leaving, Left};
	
	private class MyCustomer
	{
		public CustomerAgent c;
		int table;
		String choice;
		private myCustomerState state = myCustomerState.Waiting;
		public MyCustomer(CustomerAgent cust, int t)
		{
			c = cust;
			table = t;
		}
	}
	
	

	public WaiterAgent(String name, HostAgent h, CookAgent c) 
	{
		super();

		this.name = name;
		this.host = h;
		this.cook = c;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) 
		{
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public String getMaitreDName() 
	{
		return name;
	}

	public String getName() 
	{
		return name;
	}

	public List getWaitingCustomers() 
	{
		return myCustomers;
	}

	public Collection getTables() 
	{
		return tables;
	}
	

	
	
	
	
	///// Messages

	public void msgIWantFood(CustomerAgent cust)
	{
		//necessary anymore?
		//myCustomers.add(cust);
		//stateChanged();
	}
	
	public void pleaseSeatCustomer(CustomerAgent cust, int table)
	{
		myCustomers.add(new MyCustomer(cust,table));
		//cust.setWaiter(this);
		stateChanged();
	}
	
	public void ReadyToOrder(CustomerAgent c)
	{
		for(MyCustomer mc : myCustomers)
		{
			if(mc.c == c)
			{
				mc.state = myCustomerState.readyToOrder;
				stateChanged();
			}
		}
	}

	public void myChoiceIs(String TheOrder, CustomerAgent cust)
	{
		for(MyCustomer mc : myCustomers)
		{
			if(mc.c == cust)
			{
				mc.choice = TheOrder;
				mc.state = myCustomerState.OrderReceived;
				receivingOrder.release();
				stateChanged();
			}
		}
		
	}
	
	public void OrderIsReady(String food, int table)
	{
		for(MyCustomer mc : myCustomers)
		{
			if(mc.table == table && mc.choice == food)
			{
				mc.state = myCustomerState.DeliveringMeal;
				stateChanged();
			}
			
		}
	}
	
	public void iAmLeavingTable(CustomerAgent cust) 
	{
		for(MyCustomer mc : myCustomers)
		{
			if(mc.c == cust)
			{
				mc.state = myCustomerState.Leaving;
			}
		}
	stateChanged();		
	}

	public void msgAtTable() 
	{//from animation
		//print("msgAtTable() called");
		if(atTable.availablePermits() <1 )
		{
		atTable.release();// = true;
		stateChanged();	
		}
	}
	
	public void msgAtKitchen()
	{
		if(atKitchen.availablePermits() <1 )
		{
			atKitchen.release();
			stateChanged();
		}
	}
	
	public void msgAtFrontDesk()
	{
			AtFrontDesk = true;
			stateChanged();
	}
	
	public void msgNotAtFrontDesk()
	{
		AtFrontDesk = false;
		stateChanged();
	}

	
	
	
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() 
	{
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		for(MyCustomer mc : myCustomers)
		{
			if(mc.state == myCustomerState.Waiting)
			{
				seatCustomer(mc);//the action
				return true;//return true to the abstract agent to reinvoke the scheduler.
			}
			
			if(mc.state == myCustomerState.readyToOrder)
			{
				TakeOrder(mc);
				return true;
			}
			
			if(mc.state == myCustomerState.OrderReceived)
			{
				SendOrder(mc);
				return true;
			}
			
			if(mc.state == myCustomerState.DeliveringMeal)
			{
				DeliverMeal(mc);
				return true;
			}
			
			if(mc.state == myCustomerState.Leaving)
			{
				clearTable(mc);
				return true;
			}
			else
			{
				relax();
				return true;
			}
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	
	
	
	
	
	///// Actions

	private void seatCustomer(MyCustomer mc) 
	{
		//AccompanyingCustomer = true;
		//print("permits" + atFrontDesk.availablePermits());
		//waiterGui.GoToFrontDesk();
		//try 
		//{
		//	atFrontDesk.acquire();
		//	atFrontDesk.acquire();
		//} catch (InterruptedException e) 
		//{
		//	e.printStackTrace();
		//}
		//print("permits" + atFrontDesk.availablePermits());	
		//AccompanyingCustomer = false;
		//customer.setCurrentTable(table.tableNumber);
		Menu menuforCust = new Menu();
		mc.c.followMe(menuforCust, this, mc.table);
		DoSeatCustomer(mc.c, mc.table);
		try 
		{
			atTable.acquire();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		//Leaving Customers
		waiterGui.GoToFrontDesk();
		mc.state = myCustomerState.Seated;

	}

	// The animation DoXYZ() routines
	private void DoSeatCustomer(CustomerAgent customer, int table) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		print("Seating " + customer + " at table " + table);
		waiterGui.GoToTable(customer); 

	}

	public void TakeOrder(MyCustomer mc)
	{
		
		DoGoToTable(mc.c);
		try 
		{
			atTable.acquire();
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mc.c.WhatDoYouWant();
		mc.state = myCustomerState.TakingOrder;	
		try 
		{
			receivingOrder.acquire();
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void DoGoToTable(CustomerAgent cust)
	{
		//REMEMBER TO DO THIS
		//AFFECTS GUI!!!!!!!!!
		print("Going to Table" + cust.getCurrentTable() + " Customer " + cust);
		waiterGui.GoToTable(cust);
		
	}
	
	public void SendOrder(MyCustomer mc)
	{
		DoGoToCook(); //REMEMBER TO PASS CHEF AS A PARAMETER
		try 
		{
			atKitchen.acquire();
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waiterGui.GoToFrontDesk();
		cook.pleaseCook(mc.choice, mc.table, this);
		print("Message 7 - Sent Order");
		mc.state = myCustomerState.OrderSent;
	}
	
	private void DoGoToCook() //REMEMBER TO PASS CHEF AS A PARAMETER
	{
		//REMEMBER TO DO THIS
		//AFFECTS GUI!!!!!!
		print("Going to Kitchen");
		waiterGui.GoToKitchen();
	}
	
	public void DeliverMeal(MyCustomer mc)
	{
		DoGoToCook();
		try 
		{
			atKitchen.acquire();
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//print ("At Kitchen " + atTable.toString());
		///Do we need to carry the order
		DoGoToTable(mc.c);
		try 
		{
			atTable.acquire();
			atTable.acquire();
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Do we need to pass in a "food" item
		//print ("At table " + atTable.toString());
		mc.c.HereIsYourOrder(mc.choice);
		print("Message 9 Sent - Delivering Meal");
		mc.state = myCustomerState.Eating;
		waiterGui.GoToFrontDesk();
	}
	
	public void clearTable(MyCustomer mc)
	{
		//No longer using tables in Waiter Agent
		//for (Table table : tables) 
		//{
			//if (table.getOccupant() == mc.c) 
			//{
				print(mc.c + " leaving table " + mc.table);
				//table.setUnoccupied();
				mc.state = myCustomerState.Left;
				host.TableIsFree(mc.table);
				print("Message 11 Sent, " + mc.c + " has left, " + mc.table + " is free");
			//}
		//}
	}
	
	public void relax()
	{
		doRelax();
	}
	
	public void doRelax()
	{
		waiterGui.DoRelax();
	}
	
	
	//utilities

	public void setGui(WaiterGui gui) 
	{
		waiterGui = gui;
	}

	public WaiterGui getGui() 
	{
		return waiterGui;
	}
	
	public void addTables()
	{
		if(NTABLES < 4)
		{
			NTABLES++;
			tables.add(new Table(NTABLES));
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
}

