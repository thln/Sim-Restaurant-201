package restaurant.gui;


import restaurant.CustomerAgent;
import restaurant.WaiterAgent;

import java.awt.*;

public class WaiterGui implements Gui {

    private WaiterAgent agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int height = 20, width = 20;
	private static final int xCordFrontDesk = -20;
	private static final int yCordFrontDesk = -20;
	private static final int xCordKitchen = 540;
	private static final int yCordKitchen = 600;
	private int tableNumber = 1;
    private int xDestination = -20, yDestination = -20;//default start position

    //static final
    public static final int xTable[] = {0, 200, 300, 400, 200, 300, 400, 200, 300, 400};
    public static final int yTable[] = {0, 250, 250, 250, 350, 350, 350, 450, 450, 450};

    public WaiterGui(WaiterAgent agent) 
    {
        this.agent = agent;
    }

    public void updatePosition() 
    {
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
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xCordKitchen) & (yDestination == yCordKitchen))
        {
        	agent.msgAtKitchen();
        }
    }

    public void draw(Graphics2D g) 
    {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, height, width);
    }

    public boolean isPresent() 
    {
        return true;
    }

    public void GoToTable(CustomerAgent customer) 
    {
    	tableNumber = customer.getCurrentTable();
    	//xTable = xTable + 100*(customer.currentTable-1);
        xDestination = xTable[tableNumber] + width;
        yDestination = yTable[tableNumber] - height;
    }

    public void GoToKitchen()
    {
    	xDestination = xCordKitchen;
    	yDestination = yCordKitchen;
    }
    
    public void DoLeaveCustomer() 
    {
        xDestination = xCordFrontDesk;
        yDestination = yCordFrontDesk;
    }
    
    public void DoLeaveKitchen()
    {
        xDestination = xCordFrontDesk;
        yDestination = yCordFrontDesk;    	
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
