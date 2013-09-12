package restaurant.gui;


import restaurant.CustomerAgent;
import restaurant.HostAgent;

import java.awt.*;

public class HostGui implements Gui {

    private HostAgent agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int height = 20, width = 20;
	private static final int xCordOffScreen = -20;
	private static final int yCordOffScreen = -20;
	private int tableNumber = 1;
    private int xDestination = -20, yDestination = -20;//default start position

    //static final
    public static final int xTable = 200;
    public static final int yTable = 250;

    public HostGui(HostAgent agent) {
        this.agent = agent;
    }

    public void updatePosition() {
        if (xPos < xDestination)
            xPos++;
        else if (xPos > xDestination)
            xPos--;

        if (yPos < yDestination)
            yPos++;
        else if (yPos > yDestination)
            yPos--;

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + (100*(tableNumber -1)) + width) & (yDestination == yTable - height)) {
           agent.msgAtTable();
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, height, width);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(CustomerAgent customer) {
    	tableNumber = customer.getCurrentTable();
    	//xTable = xTable + 100*(customer.currentTable-1);
        xDestination = xTable + (100*(tableNumber -1)) + 20;
        yDestination = yTable - 20;
    }

    public void DoLeaveCustomer() {
        xDestination = xCordOffScreen;
        yDestination = yCordOffScreen;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
