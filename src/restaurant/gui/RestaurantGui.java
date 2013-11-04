package restaurant.gui;
//package components;

import restaurant.CustomerAgent;
import restaurant.WaiterAgent;
//import javax.swing.JTextField;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener { //, KeyListener {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    private JPanel interfacePanel;
    private JPanel infoPanel;
    private JLabel infoLabel; //part of infoPanel
    private JPanel personalPanel; //info panel
    private JCheckBox customerHungerCB;//part of infoLabel
    private JCheckBox waiterBreakCB; // part of infoLabel

    //Numbers used
    private int WINDOWX = 1300; //450; 1.8 multiplier *****
    private int WINDOWY = 600; //350; 1.8 multiplier
    private int height = 50;
    private int width = 50;
    
    private int numberOfRows = 1;
    private int numberOfColumns = 2;
    private int horGap = 30;
    private int verGap = 0;
    
    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() 
    {
    	
    	animationPanel.setHost(restPanel.getHost());
    	
    	setBounds(height, width, WINDOWX, WINDOWY);

        setLayout(new BorderLayout());
        
        //Setting up interfacePanel
        interfacePanel = new JPanel();
        interfacePanel.setLayout(new BorderLayout());
        interfacePanel.setBounds(height, width, WINDOWX/2, WINDOWY);
        
        Dimension restDim = new Dimension(WINDOWX/2, (int) (WINDOWY * .4)); //(WINDOWY * .6));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
        interfacePanel.add(restPanel, BorderLayout.CENTER);
        //Add in animationPanel and and change the sizing (variable in AnimationPanel)
        
        
        
        // Now, setup the info panel
        Dimension infoDim = new Dimension(WINDOWX/2, (int) (WINDOWY * .25));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        
        customerHungerCB = new JCheckBox();
        customerHungerCB.setVisible(false);
        customerHungerCB.addActionListener(this);
        
        waiterBreakCB = new JCheckBox();
        waiterBreakCB.setVisible(false);
        waiterBreakCB.addActionListener(this);

        //Fix Magic Numbers
        infoPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns, horGap, verGap));
        
        infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
        infoPanel.add(infoLabel); 
        //JTextField NameEnter = new JTextField("Enter Name Here", 20);
        //infoPanel.add(NameEnter);
        infoPanel.add(customerHungerCB);
        infoPanel.add(waiterBreakCB);
        interfacePanel.add(infoPanel, BorderLayout.PAGE_START);
        
        
        
        //personal info
        //Dimension personalDim = new Dimension(WINDOWX/2, (int) (WINDOWY* .15));
        personalPanel = new JPanel();
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Henry Nguyen");
        personalPanel.add(nameLabel);
        ImageIcon image = new ImageIcon("C:/Users/Alan/Pictures/2012 - 2013 College Freshmen/ID/dog.jpg");
        JLabel imagelabel = new JLabel(image);
        personalPanel.add(imagelabel);
        interfacePanel.add(personalPanel, BorderLayout.PAGE_END); 
        
        
        //Overall Window
        add(interfacePanel, BorderLayout.WEST);
        
        add(animationPanel, BorderLayout.CENTER); 
    }
    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    public void updateInfoPanel(Object person) 
    {
        //customerHungerCB.setVisible(true);
        currentPerson = person;

        if (person instanceof CustomerAgent) 
        {
        	waiterBreakCB.setVisible(false);
            customerHungerCB.setVisible(true);
            CustomerAgent customer = (CustomerAgent) person;
            customerHungerCB.setText("Hungry?");
          //Should checkmark be there? 
            customerHungerCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            customerHungerCB.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            infoLabel.setText(
               "<html><pre>     Name: " + customer.getName() + " </pre></html>");
        }
        else if (person instanceof WaiterAgent)
        {
        	//WAITER ON BREAK STUFF ******************************
            customerHungerCB.setVisible(false);
        	waiterBreakCB.setVisible(true);
        	WaiterAgent waiter = (WaiterAgent) person;
        	waiterBreakCB.setText("On Break?");
        	//DOES THIS WORK***?
        	waiterBreakCB.setSelected(waiter.isOnBreak());
        	waiterBreakCB.setEnabled(!waiter.isRequestingBreak());
            infoLabel.setText(
                    "<html><pre>     Name: " + waiter.getName() + " </pre></html>");
        }
        infoPanel.validate();
    }
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    public void actionPerformed(ActionEvent e) 
    {
    	
    	//WAITER ON BREAK STUFF ******************************
    	//Perhaps change waiter on break boolean here?
        if (e.getSource() == customerHungerCB) 
        {
            if (currentPerson instanceof CustomerAgent) 
            {
                CustomerAgent c = (CustomerAgent) currentPerson;
                c.getGui().setHungry();
                customerHungerCB.setEnabled(false);
            }
        }
        
        if(e.getSource() == waiterBreakCB)
        {
        	if(currentPerson instanceof WaiterAgent)
        	{
        		WaiterAgent w = (WaiterAgent) currentPerson;
        		w.WantGoOnBreak();
        		waiterBreakCB.setEnabled(false);
        	}
        }
    }
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    public void setCustomerEnabled(CustomerAgent c) 
    {
        if (currentPerson instanceof CustomerAgent) 
        {
            CustomerAgent cust = (CustomerAgent) currentPerson;
            if (c.equals(cust)) 
            {
                customerHungerCB.setEnabled(true);
                customerHungerCB.setSelected(false);
            }
        }
    }
    
	//WAITER ON BREAK STUFF ******************************
    // Needs a way to re-enable the Checkbox
    public void setWaiterEnabled(WaiterAgent w)
    {
    	if(currentPerson instanceof WaiterAgent)
    	{
    		WaiterAgent wait = (WaiterAgent) currentPerson;
    		if(w.equals(wait))
    		{
    			waiterBreakCB.setEnabled(true);
    			waiterBreakCB.setSelected(false);
    		}
    	}
    }
    /**
     * Main routine to get gui started
     */
    public static void main(String[] args) 
    {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("Henry's csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
