package restaurant.interfaces;

//import restaurant.MarketAgent.Delivery;

public interface Market 
{
	public abstract void INeedMore(String food, int Quantity);
	public abstract void Paid(double cash);
	
}
