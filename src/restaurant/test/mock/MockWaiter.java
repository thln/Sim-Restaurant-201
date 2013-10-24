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

	public EventLog log = new EventLog(); //necessary? 
	
	public void CanIGetMyCheck(Customer cust)
	{
		
	}
	
	public void ThisIsTheCheck(Customer cust, Check ch)
	{
		
	}
}
