package restaurant.interfaces;

import restaurant.Check;
import restaurant.CustomerAgent;
//import restaurant.WaiterAgent.MyCustomer;
//import restaurant.WaiterAgent.WaiterState;
//import restaurant.WaiterAgent.myCustomerState;

public interface Waiter {
	//public abstract void WantGoOnBreak();
	//public abstract void WantToGoOffBreak();
	//public abstract void AllowedToGoOnBreak(boolean answer);
	public abstract void CanIGetMyCheck(Customer cust);
	//public abstract void pleaseSeatCustomer(Customer cust, int table);
	//public abstract void ReadyToOrder(Customer c);
	//public abstract void myChoiceIs(String TheOrder, Customer cust);
	//public abstract void OrderIsReady(String food, int table);
	//public abstract void iAmLeavingTable(Customer cust);
	//public abstract void OutOfFood(int table, String food);
	public abstract void ThisIsTheCheck(Customer cust, Check ch);
	//public abstract void msgAtTable();
	//public abstract void msgAtKitchen();
	//public abstract void msgAtFrontDesk();
	//public abstract void msgNotAtFrontDesk();
	//public abstract void msgAtBreakRoom();
	//public abstract void msgAtCashier();

}
