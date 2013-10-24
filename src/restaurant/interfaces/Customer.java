package restaurant.interfaces;

import restaurant.Check;
//import restaurant.Menu;
//import restaurant.WaiterAgent;

public interface Customer {
	//public abstract void gotHungry();
	//public abstract void followMe(Menu m, WaiterAgent w, int t);
	//public abstract void WhatDoYouWant();
	//public abstract void OutOfChoice(String o);
	//public abstract void HereIsYourOrder(String o);
	public abstract void HereIsYourCheck(Check ch);
	public abstract void HereIsYourChange(double c, double d);
	//public abstract void ImpatientNoMoreSeats();
	//public abstract void msgAnimationFinishedGoToSeat();
	//public abstract void msgAnimationFinishedGoToCashier();
	//public abstract void msgAnimationFinishedLeaveRestaurant();

}
