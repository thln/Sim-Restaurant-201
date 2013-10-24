package restaurant.test.mock;

import restaurant.Check;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;


public class MockCustomer extends Mock implements Customer 
{
	
	public Cashier cashier;

    public MockCustomer(String name) 
    {
            super(name);

    }

	public EventLog log = new EventLog(); //necessary?
	
	public void HereIsYourCheck(Check ch)
	{
		log.add(new LoggedEvent("Received HereIsYourTotal from cashier. Total = "+ ch.cost));
        if(this.getName().toLowerCase().contains("thief"))
        {
            //test the non-normative scenario where the customer has no money if their name contains the string "theif"
            //cashier.IAmShort(this, 0);
        	cashier.HereIsPayment(ch, 0);

        }
        else if (this.getName().toLowerCase().contains("rich"))
        {
            //test the non-normative scenario where the customer overpays if their name contains the string "rich"
            cashier.HereIsPayment(ch, 20);

    	}
        else
        {
            //test the normative scenario
            cashier.HereIsPayment(ch, ch.cost);
        }
		
	}
	public void HereIsYourChange(double c, double d)
	{
		log.add(new LoggedEvent("Received HereIsYourChange from cashier."));
		if(d>0)
		{
	        log.add(new LoggedEvent("Received YouOweUs from cashier. Debt = "+ d));
		}
		else
		{
			log.add(new LoggedEvent("Received HereIsYourChange from cashier. Change = "+ c));
		}
	}
	
    /*
    @Override
    public void HereIsYourTotal(double total) {
            log.add(new LoggedEvent("Received HereIsYourTotal from cashier. Total = "+ total));

            if(this.name.toLowerCase().contains("thief")){
                    //test the non-normative scenario where the customer has no money if their name contains the string "theif"
                    cashier.IAmShort(this, 0);

            }else if (this.name.toLowerCase().contains("rich")){
                    //test the non-normative scenario where the customer overpays if their name contains the string "rich"
                    cashier.HereIsMyPayment(this, Math.ceil(total));

            }else{
                    //test the normative scenario
                    cashier.HereIsMyPayment(this, total);
            }
    }

    @Override
    public void HereIsYourChange(double total) {
            log.add(new LoggedEvent("Received HereIsYourChange from cashier. Change = "+ total));
    }

    @Override
    public void YouOweUs(double remaining_cost) {
            log.add(new LoggedEvent("Received YouOweUs from cashier. Debt = "+ remaining_cost));
    }
    */
}
