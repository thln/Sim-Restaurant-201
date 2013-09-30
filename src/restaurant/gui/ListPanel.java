package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.WaiterAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class ListPanel extends JPanel implements ActionListener, KeyListener {

	public JScrollPane customerPane1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public JScrollPane customerPane2 =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public JScrollPane waiterPane1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public JScrollPane waiterPane2 =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel customerView = new JPanel();
    private JPanel waiterView = new JPanel();
    private JPanel NewCustomerInfo = new JPanel();
    private JPanel ButtonManager = new JPanel();
    private JPanel CustomerTab = new JPanel();
    private JPanel WaiterTab = new JPanel();
    private JTabbedPane tabbedPeopleMenu = new JTabbedPane();
    private List<JButton> customerList = new ArrayList<JButton>();
    private List<JButton> waiterList = new ArrayList<JButton>();
    private JButton addPerson = new JButton("Add Person");
    private JButton addTables = new JButton("Add Table");
    private JButton pauseButton = new JButton("Pause");
    private JCheckBox newCustomerHungerCB;
    private JTextField waiterNameEnter;
    private JTextField customerNameEnter;
    private boolean pauseState = false;
    
    //Important Dimensions Used
    Dimension NameEnterDimensions = new Dimension(250, 20);
    Dimension NamePersonDimensions = new Dimension(250, 50);
    Dimension PeopleListDimensions = new Dimension(250,300);
    private int textFieldLength = 10;
    
    private RestaurantPanel restPanel;
    private String type;
    private String customersType = "Customers";
    private String waitersType = "Waiters";

    /**
     * Constructor for ListPanel.  Sets up all the gui
     *
     * @param rp   reference to the restaurant panel
     * @param type indicates if this is for customers or waiters
     */
    public ListPanel(RestaurantPanel rp, String type) {
        restPanel = rp;
        this.type = type;

        setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
        //add(new JLabel(type)); //"<html><pre><u>" + type + "</u><br></pre></html>"));

        
        
        /*
        additionalTables.addActionListener(this);
        add(additionalTables);
        
        addPersonB.addActionListener(this);
        addPersonB.setEnabled(false);
        add(addPersonB);
        */
        //~~
        
        
        
        //For CustomerTab
        CustomerTab.setLayout(new BoxLayout((Container) CustomerTab, BoxLayout.Y_AXIS));
        
        newCustomerHungerCB = new JCheckBox();
        newCustomerHungerCB.setVisible(true);
        newCustomerHungerCB.setText("Hungry?");
        newCustomerHungerCB.setEnabled(false); 
        newCustomerHungerCB.addActionListener(this);

        /*
        Dimension paneSize = pane.getSize();
        Dimension NameEnterDimensions = new Dimension(250, 20);
        Dimension NamePersonDimensions = new Dimension(250, 50);
        Dimension PeopleListDimensions = new Dimension(250,300);
        //(paneSize.width, (int) (paneSize.height / 2));
        */
        
        //add(NameEnter);
        NewCustomerInfo.setLayout(new BoxLayout((Container) NewCustomerInfo, BoxLayout.X_AXIS));
        //NewCustomerInfo.setPreferredSize(NewCustomerSize);
        //NewCustomerInfo.setMinimumSize(NewCustomerSize);
        //NewCustomerInfo.setMaximumSize(NewCustomerSize);        
        customerNameEnter = new JTextField("Enter Name Here", textFieldLength);
        customerNameEnter.setMaximumSize(NameEnterDimensions);
        customerNameEnter.addKeyListener((KeyListener) this);
        //NewCustomer.addKeyListener(this);
        NewCustomerInfo.add(customerNameEnter);
        NewCustomerInfo.add(newCustomerHungerCB);
        customerPane1.setViewportView(NewCustomerInfo);
        customerPane1.setMaximumSize(NamePersonDimensions);
        CustomerTab.add(customerPane1);
        
        
        customerView.setLayout(new BoxLayout((Container) customerView, BoxLayout.Y_AXIS));
        customerPane2.setViewportView(customerView);
        //pane.setMaximumSize(PeopleListDimensions);
        customerPane2.setPreferredSize(PeopleListDimensions);
        CustomerTab.add(customerPane2);
        //add(CustomerTab);
        
        
        
        //Waiter Tab
        WaiterTab.setLayout(new BoxLayout((Container) WaiterTab, BoxLayout.Y_AXIS));
        waiterNameEnter = new JTextField("Enter Name Here", textFieldLength);
        waiterNameEnter.setMaximumSize(NameEnterDimensions);
        waiterNameEnter.addKeyListener((KeyListener) this);
        waiterPane1.setViewportView(waiterNameEnter);
        waiterPane1.setMaximumSize(NamePersonDimensions);
        WaiterTab.add(waiterPane1);
        
        waiterView.setLayout(new BoxLayout((Container) waiterView, BoxLayout.Y_AXIS));
        waiterPane2.setViewportView(waiterView);
        waiterPane2.setPreferredSize(PeopleListDimensions);
        WaiterTab.add(waiterPane2);
        
        
        
        //Tabbed Menu
        tabbedPeopleMenu.addTab("Customers", CustomerTab);
        tabbedPeopleMenu.addTab("Waiters", WaiterTab);
        add(tabbedPeopleMenu);
        //tabbedPeopleMenu.getSelectedIndex()
        
        //Button Panel
        ButtonManager.setLayout(new BoxLayout((Container) ButtonManager, BoxLayout.X_AXIS));
        addPerson.addActionListener(this);
        addPerson.setEnabled(false);
        ButtonManager.add(addPerson);
        
        addTables.addActionListener(this);
        ButtonManager.add(addTables);
        
        pauseButton.addActionListener(this);
        ButtonManager.add(pauseButton);
        
        add(ButtonManager);
    
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
    public void actionPerformed(ActionEvent e) 
    {
    	if (e.getSource() == addPerson) 
    	{
        	// Chapter 2.19 describes showInputDialog()
            //addPerson(JOptionPane.showInputDialog("Please enter a name:"));
    		if(tabbedPeopleMenu.getSelectedIndex() == 0)
    		{
    			addPerson(customerNameEnter.getText());
    		}
    		else if(tabbedPeopleMenu.getSelectedIndex() == 1)
    		{
    			addPerson(waiterNameEnter.getText());
    		}
        }
    	else if (e.getSource() == addTables)
    	{
    		restPanel.getHost().addTables();	
    	}
    	//Add in a PauseButton action here here*********
    	else if (e.getSource() == pauseButton)
    	{
    		if(!pauseState)
    		{
    			pauseState = true;
    			restPanel.pauseAllAgents();
    			pauseButton.setText("Restart");
    		}
    		else if(pauseState)
    		{
    			pauseState = false;
    			restPanel.restartAllAgents();
    			pauseButton.setText("Pause");
    		}
    	}
        else 
        {
        	// Add in waiterList here in the future
        	for (JButton temp:customerList)
        	{
                if (e.getSource() == temp)
                    restPanel.showInfo("Customers", temp.getText());
            }
        }
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
    	if(!(customerNameEnter.getText().isEmpty()))
    	{
    		newCustomerHungerCB.setEnabled(true);
    		addPerson.setEnabled(true);
    	}
    	else
    	{
    		newCustomerHungerCB.setEnabled(false);
    		addPerson.setEnabled(false);
    	}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
	@Override
    public void keyPressed(KeyEvent e)
    {

    }

    /**
     * If the add button is pressed, this function creates
     * a spot for it in the scroll pane, and tells the restaurant panel
     * to add a new person.
     *
     * @param name name of new person
     */
    public void addPerson(String name) 
    {
        if (name != null) 
        {
        	//Customer
        	if(tabbedPeopleMenu.getSelectedIndex() == 0)
        	{
	            JButton button = new JButton(name);
	            button.setBackground(Color.white);
	            customerList.add(button);
	
	            Dimension paneSize = customerPane2.getSize();
	            Dimension buttonSize = new Dimension(paneSize.width - 20,
	                    (int) (paneSize.height / 4));
	            button.setPreferredSize(buttonSize);
	            button.setMinimumSize(buttonSize);
	            button.setMaximumSize(buttonSize);
	            button.addActionListener(this);
	            customerList.add(button);
	            customerView.add(button);
	            //type = "Customers";
	            //tabbedPeopleMenu.getTitleAt(tabbedPeopleMenu.getSelectedIndex())
	            restPanel.addPerson(customersType, name, newCustomerHungerCB.isSelected());//puts customer on list
	            restPanel.showInfo(customersType, name);//puts hungry button on panel
	            validate();
        	}
        	//Waiter
        	else if(tabbedPeopleMenu.getSelectedIndex() == 1)
        	{
	            JButton button = new JButton(name);
	            button.setBackground(Color.white);
	
	            Dimension paneSize = waiterPane2.getSize();
	            Dimension buttonSize = new Dimension(paneSize.width - 20,
	                    (int) (paneSize.height / 4));
	            button.setPreferredSize(buttonSize);
	            button.setMinimumSize(buttonSize);
	            button.setMaximumSize(buttonSize);
	            button.addActionListener(this);
	            waiterList.add(button);
	            waiterView.add(button);
	            //type = "Waiters";
	            //tabbedPeopleMenu.getTitleAt(tabbedPeopleMenu.getSelectedIndex())
	            restPanel.addPerson(waitersType, name, false);//puts customer on list
	            restPanel.showInfo(waitersType, name);//puts hungry button on panel
	            validate();
        	}
        }
    }

}
