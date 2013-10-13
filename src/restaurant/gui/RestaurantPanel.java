package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.HostAgent.CustomerState;
import restaurant.WaiterAgent;
import restaurant.HostAgent;
import restaurant.CookAgent;
import restaurant.MarketAgent;
import restaurant.CashierAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel // implements KeyListener 
{
    //Host, cook, waiters and customers
    private HostAgent host = new HostAgent("Sarah");
    private CookAgent cook = new CookAgent("Jesse");
    private CashierAgent cashier = new CashierAgent("David");
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
        
        //addKeyListener((KeyListener) this);
        addBindings();
        //peoplePanel.addBindings();
        
        //Hack for adding Markets
        
        target.startThread();
        walmart.startThread();
        costco.startThread();
        cook.addMarket(target);
        cook.addMarket(walmart);
        cook.addMarket(costco);
        host.startThread();
        cook.startThread();
        cashier.startThread();
        
        
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
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$4.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

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
    		CustomerAgent ca = new CustomerAgent(name, host, cashier);	
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
    		WaiterAgent wa = new WaiterAgent(name,host, cook, cashier);
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
    	cashier.pause();
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
    	cashier.restart();
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
    
    protected void addBindings() 
    {
        String stringCtrlI = "CTRL I";
        String stringCtrlB = "CTRL B";
        String stringCtrlO = "CTRL O";
        String stringCtrlP = "CTRL P";
        String stringCtrlL = "CTRL L";
        String stringCtrlE = "CTRL E";
        Action keyCtrlI = new AbstractAction()
        {
             public void actionPerformed(ActionEvent e)
             {
            	 System.out.println("Ctrl+I : Any customers waiting to be seated while there is a full restaurant will leave.");
            	 if(host.FullRestaurant())
        			{
        				for(CustomerAgent cust : customers)
        				{
        					if(cust.WaitingToBeSeated)
        					{
        						cust.ImpatientNoMoreSeats();
        					}
        				}
        			}
             }
        };
        
        Action keyCtrlB = new AbstractAction()
        {
             public void actionPerformed(ActionEvent e)
             {
            	 System.out.println("Ctrl+B : Any customer waiting to be seated will now dine and dash/eat regardless of cost.");
            	 for(CustomerAgent cust : customers)
 				{
 					if(cust.WaitingToBeSeated)
 					{
 						cust.setCash(0.00);
 						cust.DineAndDash = true;
 					}
 				}
             }
        };
        
        Action keyCtrlO = new AbstractAction()
        {
             public void actionPerformed(ActionEvent e)
             {
            	 System.out.println("Ctrl+O : Any customer waiting to be seated will now have $0, and will not be able to buy any item.");
            	 for(CustomerAgent cust : customers)
 				{
 					if(cust.WaitingToBeSeated)
 					{
 						cust.setCash(0.00);
 					}
 				}
             }
        };
        
        Action keyCtrlP = new AbstractAction()
        {
             public void actionPerformed(ActionEvent e)
             {
            	 System.out.println("Ctrl+P : Any customer waiting to be seated will now have $8, and will only be able to afford a salad.");
            	 for(CustomerAgent cust : customers)
 				{
 					if(cust.WaitingToBeSeated)
 					{
 						cust.setCash(8.00);
 					}
 				}
             }
        };
        
        Action keyCtrlL = new AbstractAction()
        {
             public void actionPerformed(ActionEvent e)
             {
            	 System.out.println("Ctrl+L : The inventory of the cook will be set to right before low (2). When a customer orders, the chef will begin to purchase more foods. ");
            	 //Do Stuff
            	 cook.setLow();
             }
        };
        Action keyCtrlE = new AbstractAction()
        {
             public void actionPerformed(ActionEvent e)
             {
            	 System.out.println("Ctrl+E : The inventory of the cook will be emptied. When a customer orders, the chef will begin to purchase more foods. ");
            	 //Do Stuff
            	 cook.setEmpty();
             }
        };
        
        getInputMap(this.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK), stringCtrlI); 
        getInputMap(this.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK), stringCtrlB);
        getInputMap(this.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK), stringCtrlO);
        getInputMap(this.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK), stringCtrlP);
        getInputMap(this.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK), stringCtrlL);
        getInputMap(this.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK), stringCtrlE);

        
        getActionMap().put(stringCtrlI, keyCtrlI);
        getActionMap().put(stringCtrlB, keyCtrlB);
        getActionMap().put(stringCtrlO, keyCtrlO);
        getActionMap().put(stringCtrlP, keyCtrlP);
        getActionMap().put(stringCtrlL, keyCtrlL);
        getActionMap().put(stringCtrlE, keyCtrlE);

    }
}
