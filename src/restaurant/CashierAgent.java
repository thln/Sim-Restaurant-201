package restaurant;

import agent.Agent;
//import restaurant.Menu;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.CustomerAgent.AgentState;

public class CashierAgent extends Agent 
{
	/***** DATA *****/
	private String name;
	private Menu MenuForReference;
	
	public enum CheckState
	{Created, Pending, Paying, NotPaidOff, PaidOff};
	
	private class Check
	{
		String foodItem;
		double cost;
		double cash;
		CustomerAgent c;
		WaiterAgent w;
		CheckState s;
		public Check(String choice, CustomerAgent cust, WaiterAgent wait)
		{
			//STUB
		}
		
	}
	
	List<Check> AllChecks;
	
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
	}
	
	public void HereIsPayment(Check ch, double cash)
	{
		//STUB
	}
	
	
	/***** SCHEDULER *****/
	
	protected boolean pickAndExecuteAnAction() 
	{
		//////FILL IN HERE
		for(Check c : AllChecks)
		{
			//STUB
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	
	/***** ACTIONS *****/
	private void ComputerCheck(Check ch)
	{
		//STUB
	}
	
	private void GiveChange(Check ch)
	{
		//STUB
	}
}
