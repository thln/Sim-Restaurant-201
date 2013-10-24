package restaurant.interfaces;

import restaurant.Check;
//import restaurant.CustomerAgent;
//import restaurant.WaiterAgent;

public interface Cashier {

	
	public abstract void GiveMeCheck(String choice, Customer cust, Waiter wait);
	public abstract void HereIsPayment(Check ch, double cash);
}
