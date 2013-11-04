package restaurant.test.mock;

import restaurant.Check;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Waiter;

public class MockWaiter extends Mock implements Waiter 
{
	public Cashier cashier;

    public MockWaiter(String name) 
    {
            super(name);

    }
	
	public void CanIGetMyCheck(Customer cust)
	{
		
	}
	
	public void ThisIsTheCheck(Customer cust, Check ch)
	{
		log.add(new LoggedEvent("Received Check from cashier. Customer = "+ cust + " and check cost is : " + ch.cost));
	}
}
