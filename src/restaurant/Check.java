package restaurant;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

//import restaurant.CashierAgent.CheckState;

public class Check 
{

	public enum CheckState
	{Created, Pending, CustomerHere, Paying, NotPaidOff, PaidOff};

		public String foodItem;
		public double cost;
		public double cash;
		public CustomerAgent c;
		public WaiterAgent w;
		public CheckState s = CheckState.Created;
		
		public Check(String choice, CustomerAgent cust, WaiterAgent wait)
		{
			//STUB
			foodItem = choice;
			c = cust;
			w = wait;
		}
		
}
