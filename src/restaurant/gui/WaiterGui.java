package restaurant.gui;


import restaurant.CustomerAgent;
import restaurant.WaiterAgent;
import restaurant.WaiterAgent.WaiterState;

import java.awt.*;

public class WaiterGui implements Gui {

    private WaiterAgent agent = null;
    RestaurantGui gui;

    private int xPos = -20, yPos = -20;//default waiter position
    private int height = 20, width = 20;
	private static final int xCordFrontDesk = -20;
	private static final int yCordFrontDesk = -20;
	private static final int xCordKitchen = 540;
	private static final int yCordKitchen = 600;
	private static final int xCordBreakRoom = 100;
	private static final int yCordBreakRoom = 400;
	private static final int xCordCashier = 400;
	private static final int yCordCashier = 100;
	private int tableNumber = 1;
    private int xDestination = -20, yDestination = -20;//default start position
    public boolean waiterIsFree = false;
    private String TheOrder = "";
	private boolean isOnBreak = false; 	//WAITER ON BREAK STUFF ******************************
    
    //static final
    public static final int xTable[] = {0, 200, 300, 400, 200, 300, 400, 200, 300, 400};
    public static final int yTable[] = {0, 250, 250, 250, 350, 350, 350, 450, 450, 450};

    public WaiterGui(WaiterAgent agent, RestaurantGui gui) 
    {
        this.agent = agent;
        this.gui = gui;
    }

    public void updatePosition() 
    {
    	//agent.msgNotAtFrontDesk();
    	
        if (xPos < xDestination)
            xPos++;
        else if (xPos > xDestination)
            xPos--;

        if (yPos < yDestination)
            yPos++;
        else if (yPos > yDestination)
            yPos--;

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable[tableNumber] + width) & (yDestination == yTable[tableNumber] - height)) 
        {
           agent.msgAtTable();
           waiterIsFree = true;
        //   agent.msgNotAtFrontDesk();
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xCordKitchen) & (yDestination == yCordKitchen))
        {
        	agent.msgAtKitchen();
            waiterIsFree = true;
        //	agent.msgNotAtFrontDesk();
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xCordFrontDesk) & (yDestination == yCordFrontDesk) && waiterIsFree)
        {
            waiterIsFree = false;
        	agent.msgAtFrontDesk();
        	System.out.println("I am at front desk.");
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xCordBreakRoom) & (yDestination == yCordBreakRoom))
        {
        	agent.msgAtBreakRoom();
        	//System.out.println("I am at BreakRoom.");
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xCordCashier) & (yDestination == yCordCashier))
        {
        	agent.msgAtCashier();
        	//System.out.println("I am at BreakRoom.");
        }
    }

    public void draw(Graphics2D g) 
    {
        g.setColor(Color.MAGENTA);
    	//WAITER ON BREAK STUFF ******************************
        if(agent.state == WaiterState.OnWayToBreak || agent.state == WaiterState.Relaxing)//(isOnBreak)
        {
        	g.setColor(Color.YELLOW);
        }
        g.fillOval(xPos, yPos, height, width);
        //g.fillRect(xPos, yPos, height, width);
        g.setColor(Color.BLUE);
        g.drawString(TheOrder, xPos, yPos);
    }

    public boolean isPresent() 
    {
        return true;
    }

	//WAITER ON BREAK STUFF ******************************
	public void setOnBreak() 
	{
		//isOnBreak = true;
		agent.WantGoOnBreak();
		//setPresent(true);
	}
	
	//WAITER ON BREAK STUFF ******************************
	public void setOffBreak()
	{
		//isOnBreak = false;
		agent.WantToGoOffBreak();
	}
	
	//WAITER ON BREAK STUFF ******************************
	public void setOffBreakbool()
	{
		isOnBreak = false;
		gui.setWaiterEnabled(agent);
	}
	
	//WAITER ON BREAK STUFF ******************************
	public boolean isOnBreak() 
	{
		return isOnBreak;
	}
    
    public void GoToTable(CustomerAgent customer) 
    {
    	tableNumber = customer.getCurrentTable();
    	//xTable = xTable + 100*(customer.currentTable-1);
        xDestination = xTable[tableNumber] + width;
        yDestination = yTable[tableNumber] - height;
    }

    public void DoDeliver(String order)
    {
    	TheOrder = order;
    }
    
    public void GoToKitchen()
    {
    	xDestination = xCordKitchen;
    	yDestination = yCordKitchen;
    }
    
    public void GoToFrontDesk() 
    {
        xDestination = xCordFrontDesk;
        yDestination = yCordFrontDesk;
    }
    
	//WAITER ON BREAK STUFF ******************************
    public void GoToBreakRoom()
    {
    	xDestination = xCordBreakRoom;
    	yDestination = yCordBreakRoom;
    }
    
    public void GoToCashier()
    {
    	xDestination = xCordCashier;
    	yDestination = yCordCashier;
    }
    
    public void DoRelax()
    {
    	
    }

    public int getXPos() 
    {
        return xPos;
    }

    public int getYPos() 
    {
        return yPos;
    }
}
