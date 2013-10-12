package restaurant;

import agent.Agent;
//import restaurant.Menu;


import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.Check.CheckState;
//import restaurant.MarketAgent.Delivery;

//import restaurant.CustomerAgent.AgentState;

public class CashierAgent extends Agent 
{
	/***** DATA *****/
	private String name;
	private Menu MenuForReference;
	
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
	List<Check> AllChecks= new ArrayList<Check>();;
	
	public CashierAgent(String name)
	{
		super();
		this.name = name;

		MenuForReference = new Menu();
	}
	
	
	
	/***** MESSAGES *****/
	public void GiveMeCheck(String choice, CustomerAgent cust, WaiterAgent wait)
	{
		//STUB
		AllChecks.add(new Check(choice, cust, wait));
		stateChanged();
	}
	
	public void HereIsPayment(Check ch, double cash)
	{
		
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
	
	protected boolean pickAndExecuteAnAction() 
	{
		//////FILL IN HERE
		for(Check c : AllChecks)
		{
			if(c.s == CheckState.Created)
			{
				c.s = CheckState.Pending;
				ComputeCheck(c);
			}
			if(c.s == CheckState.CustomerHere)
			{
				c.s = CheckState.Paying;
				GiveChange(c);
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
		ch.cash = 0;
		if(ch.cost > 0) //Not Paid Off
		{
			//include debt somehow in HereIsYourChange?
			ch.c.HereIsYourChange(0, ch.cost);
			ch.s = CheckState.NotPaidOff;
		}
		else //Has Paid off 
		{
			ch.c.HereIsYourChange(-ch.cost, 0);
			ch.s = CheckState.PaidOff;
		}
	}
}
