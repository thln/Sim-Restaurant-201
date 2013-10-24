package restaurant;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

import restaurant.interfaces.Customer;
import restaurant.interfaces.Waiter;

//import restaurant.CashierAgent.CheckState;

public class Check 
{

	public enum CheckState
	{Created, Pending, CustomerHere, Paying, NotPaidOff, PaidOff};

		public String foodItem;
		public double cost;
		public double cash;
		public Customer c; //CustomerAgent
		public Waiter w; //WaiterAgent
		public CheckState s = CheckState.Created;
		
		public Check(String choice, Customer cust, Waiter wait) //CustomerAgent, WaiterAgent
		{
			//STUB
			foodItem = choice;
			c = cust;
			w = wait;
		}
		
}
