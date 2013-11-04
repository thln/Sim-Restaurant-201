package restaurant.test.mock;

import restaurant.interfaces.Market;

//import restaurant.interfaces.Cashier;

public class MockMarket extends Mock implements Market
{

	public Market market;

    public MockMarket(String name) 
    {
            super(name);

    }
    
	public void INeedMore(String food, int Quantity)
	{
		
	}
	
	public void Paid(double cash)
	{
		log.add(new LoggedEvent("Received payment from cashier. It is "+ cash));
	}
}
