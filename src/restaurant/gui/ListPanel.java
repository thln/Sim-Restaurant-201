package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.HostAgent;

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

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public JScrollPane pane2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    private List<JButton> list = new ArrayList<JButton>();
    private JButton addPersonB = new JButton("Add");
    private JPanel NewCustomer = new JPanel();
    private JCheckBox realStateCB;
    private JTextField NameEnter;
    //private Dimension NewCustomerSize = new Dimension(100,100);

    private RestaurantPanel restPanel;
    private String type;

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
        add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

        addPersonB.addActionListener(this);
        addPersonB.setEnabled(false);
        add(addPersonB);
        
        //~~
        
        realStateCB = new JCheckBox();
        realStateCB.setVisible(true);
        realStateCB.setText("Hungry?");
        realStateCB.setEnabled(false); 
        realStateCB.addActionListener(this);

        Dimension paneSize = pane.getSize();
        Dimension NewCustomerSize = new Dimension(paneSize.width,
                (int) (paneSize.height / 2));
        
        //add(NameEnter);
        NewCustomer.setLayout(new BoxLayout((Container) NewCustomer, BoxLayout.X_AXIS));
        NewCustomer.setPreferredSize(NewCustomerSize);
        NewCustomer.setMinimumSize(NewCustomerSize);
        NewCustomer.setMaximumSize(NewCustomerSize);        
        NameEnter = new JTextField("Enter Name Here", 10);
        NameEnter.addKeyListener((KeyListener) this);
        //NewCustomer.addKeyListener(this);
        NewCustomer.add(NameEnter);
        NewCustomer.add(realStateCB);
        pane2.setViewportView(NewCustomer);
        add(pane2);
        //~~~
        
        
        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        pane.setViewportView(view);
        add(pane);
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == addPersonB) {
        	// Chapter 2.19 describes showInputDialog()
            //addPerson(JOptionPane.showInputDialog("Please enter a name:"));
        	addPerson(NameEnter.getText());
        }
        else {
        	// Isn't the second for loop more beautiful?
            /*for (int i = 0; i < list.size(); i++) {
                JButton temp = list.get(i);*/
        	for (JButton temp:list){
                if (e.getSource() == temp)
                    restPanel.showInfo(type, temp.getText());
            }
        }
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
    	if(!(NameEnter.getText().isEmpty()))
    	{
    		realStateCB.setEnabled(true);
    		addPersonB.setEnabled(true);
    	}
    	else
    	{
    		realStateCB.setEnabled(false);
    		addPersonB.setEnabled(false);
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
    public void addPerson(String name) {
        if (name != null) {
            JButton button = new JButton(name);
            button.setBackground(Color.white);

            Dimension paneSize = pane.getSize();
            Dimension buttonSize = new Dimension(paneSize.width - 20,
                    (int) (paneSize.height / 4));
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            view.add(button);
            restPanel.addPerson(type, name, realStateCB.isSelected());//puts customer on list
            restPanel.showInfo(type, name);//puts hungry button on panel
            validate();
        }
    }

}
