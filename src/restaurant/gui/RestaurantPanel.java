package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.WaiterAgent;
import restaurant.HostAgent;
import restaurant.CookAgent;
import restaurant.MarketAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel {

    //Host, cook, waiters and customers
    private HostAgent host = new HostAgent("Sarah");
    private CookAgent cook = new CookAgent("Jesse");
    private MarketAgent target = new MarketAgent("Target", cook);
    private MarketAgent walmart = new MarketAgent("Walmart", cook);
    private MarketAgent costco = new MarketAgent("Costco", cook);
    //private WaiterAgent testWaiter = new WaiterAgent("Danny", host, cook);
	//private WaiterGui WaiterGui = new WaiterGui(testWaiter);
 
    private Vector<CustomerAgent> customers = new Vector<CustomerAgent>();
    private Vector<WaiterAgent> waiters = new Vector<WaiterAgent>();

    private JPanel restLabel = new JPanel();
    private ListPanel peoplePanel = new ListPanel(this, "Customers And Waiters"); //previously customerPanel or only for Customers
    private JPanel group = new JPanel();
    
    private int numberOfRows = 1;
    private int numberOfColumns = 2;
    private int mainGap = 20;
    private int subGap = 10;

    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) 
    {
        this.gui = gui;
        
        //Hack for adding Markets
        
        target.startThread();
        walmart.startThread();
        costco.startThread();
        cook.addMarket(target);
        cook.addMarket(walmart);
        cook.addMarket(costco);
        host.startThread();
        cook.startThread();
        
        
        //magic numbers---
        setLayout(new GridLayout(numberOfRows, numberOfColumns, mainGap, mainGap));
        group.setLayout(new GridLayout(numberOfRows, numberOfColumns, subGap, subGap));

        group.add(peoplePanel);

        initRestLabel();
        add(restLabel);
        add(group);
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);
    }

    /**
     * When a customer or waiter is clicked, this function calls
     * updatedInfoPanel() from the main gui so that person's information
     * will be shown
     *
     * @param type indicates whether the person is a customer or waiter
     * @param name name of person
     */
    public void showInfo(String type, String name) {

        if (type.equals("Customers")) 
        {

            for (int i = 0; i < customers.size(); i++) 
            {
                CustomerAgent temp = customers.get(i);
                if (temp.getName() == name)
                {
                    gui.updateInfoPanel(temp);
                }
            }
        }
        else if (type.equals("Waiters"))
        {

            for (int i = 0; i < waiters.size(); i++) 
            {
                WaiterAgent temp = waiters.get(i);
                if (temp.getName() == name)
                {
                    gui.updateInfoPanel(temp);
                }
            }
        }
    }

    /**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     */
    public void addPerson(String type, String name, boolean hungry) 
    {

    	if (type.equals("Customers")) 
    	{
    		CustomerAgent ca = new CustomerAgent(name);	
    		CustomerGui cg = new CustomerGui(ca, gui);

    		gui.animationPanel.addGui(cg);// dw
    		//c.setWaiter(host);
    		ca.setHost(host);
    		ca.setGui(cg);
    		customers.add(ca);
    		if(hungry)
    		{
    			ca.getGui().setHungry();
    		}
    		ca.startThread();
    	}
    	
    	else if (type.equals("Waiters"))
    	{
    		WaiterAgent wa = new WaiterAgent(name,host, cook);
    		WaiterGui wg = new WaiterGui(wa);
    		
    		gui.animationPanel.addGui(wg);
    		wa.setGui(wg);
    		waiters.add(wa);
    		
    		host.newWaiter(wa);
    	
    		wa.startThread();
    	}
    }
    
    public void pauseAllAgents()
    {
    	host.pause();
    	cook.pause();
    	target.pause();
    	walmart.pause();
    	costco.pause();
    	for(CustomerAgent cust : customers)
    	{
    		cust.pause();
    	}
    	for(WaiterAgent wait : waiters)
    	{
    		wait.pause();
    	}
    	
    }
    
    public void restartAllAgents()
    {
    	host.restart();
    	cook.restart();
    	target.restart();
    	walmart.restart();
    	costco.restart();
    	for(CustomerAgent cust : customers)
    	{
    		cust.restart();
    	}
    	for(WaiterAgent wait : waiters)
    	{
    		wait.restart();
    	}
    	
    }
    //public WaiterAgent getWaiter()
    //{
    //	return testWaiter;
    //}
    
    public HostAgent getHost()
    {
    	return host;
    }
    

}
