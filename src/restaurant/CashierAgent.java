package restaurant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
//import restaurant.Menu;
import java.util.List;

import restaurant.Check.CheckState;
//import restaurant.MarketAgent.Delivery;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Market;
import restaurant.interfaces.Waiter;
import restaurant.test.mock.EventLog;
import agent.Agent;

//import restaurant.CustomerAgent.AgentState;

public class CashierAgent extends Agent implements Cashier
{
	/***** DATA *****/
	private String name;
	private Menu MenuForReference;
	public double accumulatedRevenue = 35.00;
	public double accumulatedCosts = 0.0;
	public double profits = 35.00;
	final DecimalFormat df = new DecimalFormat(); 
	private boolean free = true;
	public EventLog log; //necessary?
	
	public class MarketBill
	{
		public String foodItem;
		public double finalTotal;
		public Market market;
		MarketBill(String s, double c, Market m)
		{
			foodItem = s;
			finalTotal = c;
			market = m;
		}
	}
	
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
	
	public List<Check> AllChecks= Collections.synchronizedList(new ArrayList<Check>()); //private
	public List<MarketBill> MarketBills = Collections.synchronizedList(new ArrayList<MarketBill>());
	//private List<MyCustomer> customers = 
	//	    Collections.synchronizedList(new ArrayList<MyCustomer>()); example
	
	public CashierAgent(String name)
	{
		super();
		this.name = name;

		MenuForReference = new Menu();
		df.setMaximumFractionDigits(2);
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
		//log.add(new LoggedEvent("Received HereIsMyPayment"));
		//STUB
		synchronized(AllChecks)
		{
			for(Check c : AllChecks)
			{
				if(ch == c)
				{
					free = true;
					c.s = CheckState.CustomerHere;
					ch.cash = cash;
					stateChanged();
					return;
				}
			}
		}
	}
	
	public void MarketCost(String foodName, double cost, Market m)
	{
		print("I am adding a bill for " + cost + " and I have " + profits);
		MarketBills.add(new MarketBill(foodName, cost, m));
		stateChanged();
	}
	
	
	/***** SCHEDULER *****/
	public boolean pickAndExecuteAnAction() 
	{
		synchronized(MarketBills)
		{
			for(MarketBill mb : MarketBills)
			{
				if(profits > mb.finalTotal)
				{
					MarketBills.remove(mb);
					PayMarket(mb);
					return true;
				}
			}
		}
		
		//////FILL IN HERE
		synchronized(AllChecks)
		{
			for(Check c : AllChecks)
			{
				if(c.s == CheckState.Created)
				{
					ComputeCheck(c);
					return true;
				}
				if(c.s == CheckState.CustomerHere)
				{
					GiveChange(c);
					return true;
				}
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
		print("Calculating check for " + ch.c + " " + ch.w);
		ch.s = CheckState.Pending;
		if(!(ch.foodItem.equals("7.98")))
		{
			ch.cost = MenuForReference.GetPrice(ch.foodItem);
		}
		ch.w.ThisIsTheCheck(ch.c, ch);
	}
	
	private void GiveChange(Check ch)
	{
		//STUB
		ch.s = CheckState.Paying;
		print("The bill is $" + ch.cost);
		ch.cost -= ch.cash;
		//ch.cash = 0;
		if(ch.cost > 0) //Not Paid Off
		{
			//include debt somehow in HereIsYourChange?
			accumulatedCosts = (ch.cost); //-=, This does not take into account multiple bad people 
			profits -= (ch.cost);
			profits = Double.parseDouble(df.format(profits));
			accumulatedCosts = Double.parseDouble(df.format(accumulatedCosts));
			print("The restaurant debt is now " + accumulatedCosts );
			ch.c.HereIsYourChange(0, ch.cost);
			ch.s = CheckState.NotPaidOff;
		}
		else //Has Paid off 
		{
			accumulatedRevenue += (ch.cash + ch.cost);
			profits += (ch.cash + ch.cost);
			profits = Double.parseDouble(df.format(profits));
			accumulatedRevenue = Double.parseDouble(df.format(accumulatedRevenue));
			print("The restaurant revenue is now " + accumulatedRevenue + " and profits are " + profits );
			ch.c.HereIsYourChange(-ch.cost, 0);
			ch.s = CheckState.PaidOff;
		}
		ch.cash = 0;
		if(free)
		{
			free = false;
			stateChanged();
		}
	}
	private void PayMarket(MarketBill mb)
	{
		print("PayMarket runs.");
		print("I am paying for the bill " + mb.finalTotal + " and I have " + profits);
		mb.market.Paid(mb.finalTotal);
		accumulatedCosts += mb.finalTotal;
		profits -= mb.finalTotal;
		print("I now have " + profits);
		
	}
}
