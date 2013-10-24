package restaurant;

import agent.Agent;
//import restaurant.Menu;








import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.Check.CheckState;
//import restaurant.MarketAgent.Delivery;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Waiter;
import restaurant.test.mock.EventLog;
import restaurant.test.mock.LoggedEvent;

//import restaurant.CustomerAgent.AgentState;

public class CashierAgent extends Agent implements Cashier
{
	/***** DATA *****/
	private String name;
	private Menu MenuForReference;
	private double accumulatedRevenue = 0;
	private double accumulatedDebt = 0;
	public EventLog log; //necessary?
	
	/*
	public enum CheckState
	{Created, Pending, Paying, NotPaidOff, PaidOff};
	
	private class Check
	{
		String foodItem;
		double cost;
		double cash;
		CustomerAgent c;
		WaiterAgent w;
		CheckState s = CheckState.Created;
		public Check(String choice, CustomerAgent cust, WaiterAgent wait)
		{
			//STUB
			foodItem = choice;
			c = cust;
			w = wait;
		}
		
	}
	*/
	public List<Check> AllChecks= new ArrayList<Check>(); //private
	
	public CashierAgent(String name)
	{
		super();
		this.name = name;

		MenuForReference = new Menu();
	}
	
	public String getName() 
	{
		return name;
	}
	
	
	/***** MESSAGES *****/
	public void GiveMeCheck(String choice, Customer cust, Waiter wait) //CustomerAgent, WaiterAgent
	{
		//STUB
		AllChecks.add(new Check(choice, cust, wait));
		if(choice.equals("7.98"))
		{
			AllChecks.get(0).cost = 7.98;
		}
		stateChanged();
	}
	
	public void HereIsPayment(Check ch, double cash)
	{
		//ch.s = CheckState.CustomerHere;
//		log.add(new LoggedEvent("Received HereIsMyPayment"));
		//STUB
		for(Check c : AllChecks)
		{
			if(ch == c)
			{
				c.s = CheckState.CustomerHere;
				ch.cash = cash;
				stateChanged();
				return;
			}
		}
	}
	
	
	/***** SCHEDULER *****/
	
	public boolean pickAndExecuteAnAction() 
	{
		//////FILL IN HERE
		for(Check c : AllChecks)
		{
			if(c.s == CheckState.Created)
			{
				c.s = CheckState.Pending;
				ComputeCheck(c);
				return true;
			}
			if(c.s == CheckState.CustomerHere)
			{
				c.s = CheckState.Paying;
				GiveChange(c);
				return true;
			}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	
	/***** ACTIONS *****/
	private void ComputeCheck(Check ch)
	{
		//STUB
		//ch.CalculateCost();
		ch.cost = MenuForReference.GetPrice(ch.foodItem);
		ch.w.ThisIsTheCheck(ch.c, ch);
	}
	
	private void GiveChange(Check ch)
	{
		//STUB
		print("The bill is $" + ch.cost);
		ch.cost -= ch.cash;
		//ch.cash = 0;
		if(ch.cost > 0) //Not Paid Off
		{
			//include debt somehow in HereIsYourChange?
			accumulatedDebt = (ch.cost); //-=, This does not take into account multiple bad people 
			print("The restaurant debt is now " + accumulatedDebt );
			ch.c.HereIsYourChange(0, ch.cost);
			ch.s = CheckState.NotPaidOff;
		}
		else //Has Paid off 
		{
			accumulatedRevenue += (ch.cash + ch.cost);
			print("The restaurant revenue is now " + accumulatedRevenue );
			ch.c.HereIsYourChange(-ch.cost, 0);
			ch.s = CheckState.PaidOff;
		}
		ch.cash = 0;
	}
}
