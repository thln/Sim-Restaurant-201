package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.WaiterAgent;

import java.awt.*;

public class CustomerGui implements Gui{

	private CustomerAgent agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private HostAgent host;
	RestaurantGui gui;

	private int xCordCurrent, yCordCurrent;
	private final int height = 20, width = 20;
	private static final int xCordOffScreen = -40;
	private static final int yCordOffScreen = -40;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	private Command command=Command.noCommand;

    public static final int xTable[] = {0, 200, 300, 400, 200, 300, 400, 200, 300, 400};
    public static final int yTable[] = {0, 250, 250, 250, 350, 350, 350, 450, 450, 450};

	public CustomerGui(CustomerAgent c, RestaurantGui gui){ //WaiterAgent m) {
		agent = c;
		//xCordOffScreen, yCordOffScreen
		xCordCurrent = xCordOffScreen;
		yCordCurrent = yCordOffScreen;
		//height = 20;
		//width = 20;
		xDestination = xCordOffScreen;
		yDestination = yCordOffScreen;
		//maitreD = m;
		this.gui = gui;
	}

	public void updatePosition() {
		if (xCordCurrent < xDestination)
			xCordCurrent++;
		else if (xCordCurrent > xDestination)
			xCordCurrent--;

		if (yCordCurrent < yDestination)
			yCordCurrent++;
		else if (yCordCurrent > yDestination)
			yCordCurrent--;

		
		
		if (xCordCurrent == xDestination && yCordCurrent == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		if (agent.getCustomerName().equals("a")) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		g.fillRect(xCordCurrent, yCordCurrent, height, width);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void DoGoToSeat(int seatnumber) 
	{//later you will map seatnumber to table coordinates.
		xDestination = xTable[seatnumber];
		yDestination = yTable[seatnumber];
		command = Command.GoToSeat;
		//System.out.println("Moving to seat: " + seatnumber);
	}

	public void DoExitRestaurant() {
		//xCordOffscreen = -40;
		//yCordOffscreen = -40;
		xDestination = xCordOffScreen;
		yDestination = yCordOffScreen;
		//System.out.println("XCord is " + xCordOffscreen + " YCord is " + yCordOffscreen);
		//xDestination = xCordOffscreen;
		//yDestination = yCordOffscreen;
		command = Command.LeaveRestaurant;
	}
}
