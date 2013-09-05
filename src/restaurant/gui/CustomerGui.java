package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.HostAgent;

import java.awt.*;

public class CustomerGui implements Gui{

	private CustomerAgent agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private HostAgent host;
	RestaurantGui gui;

	private static int xCordOffscreen, yCordOffscreen, height, width;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	private Command command=Command.noCommand;

	public static final int xTable = 200;
	public static final int yTable = 250;

	public CustomerGui(CustomerAgent c, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xCordOffscreen = -40;
		yCordOffscreen = -40;
		height = 20;
		width = 20;
		xDestination = -40;
		yDestination = -40;
		//maitreD = m;
		this.gui = gui;
	}

	public void updatePosition() {
		if (xCordOffscreen < xDestination)
			xCordOffscreen++;
		else if (xCordOffscreen > xDestination)
			xCordOffscreen--;

		if (yCordOffscreen < yDestination)
			yCordOffscreen++;
		else if (yCordOffscreen > yDestination)
			yCordOffscreen--;

		if (xCordOffscreen == xDestination && yCordOffscreen == yDestination) {
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
		g.setColor(Color.GREEN);
		g.fillRect(xCordOffscreen, yCordOffscreen, height, width);
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

	public void DoGoToSeat(int seatnumber) {//later you will map seatnumber to table coordinates.
		xDestination = xTable;
		yDestination = yTable;
		command = Command.GoToSeat;
	}

	public void DoExitRestaurant() {
		xDestination = xCordOffscreen;
		yDestination = yCordOffscreen;
		command = Command.LeaveRestaurant;
	}
}
