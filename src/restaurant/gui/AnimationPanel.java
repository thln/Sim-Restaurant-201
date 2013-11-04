package restaurant.gui;

//import restaurant.WaiterAgent;
import restaurant.HostAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener 
{

    private final int WINDOWX = 650; //600; //hardcoded 1/2 the size of restaurant GUI WINDOWX **** 
    private final int WINDOWY = 600; //500;
    private final int XCord = 200; //200;
    private final int YCord = 250;
    private final int xCordCorner = 0;
    private final int yCordCorner = 0;
    private final int xCordCashier = 400;
    private final int yCordCashier = 100;
    private final int xCordBreakRoom = 100;
	private final int yCordBreakRoom = 400;
	private static final int xCordKitchen = 500;
	private static final int yCordKitchen = 400;
	private static final int xCordPlatingArea = 500;
	private static final int yCordPlatingArea = 400;
	private static final int xCordCookingArea = 600;
	private static final int yCordCookingArea = 400;
    private final int Height = 50;
    private final int Width = 50;
    private final int distanceBetweenTables = 100;
    private final int numTablesPerRow = 3;
    private final int firstRow = 0;
    private final int secondRow = 1;
    private final int thirdRow = 2;
    private final int frameDelay = 10; //20 is standard
    private List <String> Plated = new ArrayList<String>();
    private List <String> Cooking = new ArrayList<String>();
    // Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    private HostAgent host;
    
    public AnimationPanel() 
    {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(frameDelay, this );
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) 
	{
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(xCordCorner, yCordCorner, WINDOWX, WINDOWY);

        g2.setColor(Color.CYAN);
        g2.fillRect(xCordCashier, yCordCashier, Width, Height);// Height, Width);
        g2.fillRect(xCordBreakRoom, yCordBreakRoom, Width, Height);
        g2.setColor(Color.lightGray);
        g2.fillRect(xCordKitchen, yCordKitchen, Width*3, Height*4);
        g2.setColor(Color.CYAN);
        g2.fillRect(xCordPlatingArea, yCordPlatingArea, Width, Height *4);
        g2.fillRect(xCordCookingArea, yCordCookingArea, Width, Height *4);
        g2.setColor(Color.BLACK);
        g2.drawString("Cashier", xCordCashier, yCordCashier+20);
        g2.drawString("Break Room", xCordBreakRoom, yCordBreakRoom+20);
        g2.drawString("Ready", xCordPlatingArea + 5, yCordKitchen+20);
        g2.drawString("Kitchen", xCordKitchen + 55, yCordKitchen+20);
        g2.drawString("Grills", xCordCookingArea + 5, yCordKitchen+20);
        
        //Draw Orders and Cooking
        for(int i=0; i < Cooking.size(); i++)
        {
        	g2.drawString(Cooking.get(i), xCordCookingArea +5, yCordCookingArea + (20*(i+2)));
        }
        for(int i=0; i < Plated.size(); i++)
        {
        	g2.drawString(Plated.get(i), xCordPlatingArea +5, yCordPlatingArea + (20*(i+2)));
        }        
        
        //Here are the tables
        for(int i=0; i < host.tables.size(); i++)
        {
        	if(i<(numTablesPerRow*1))
        	{
	        	g2.setColor(Color.darkGray);
	            g2.fillRect(XCord + distanceBetweenTables*(i-(numTablesPerRow*firstRow)), YCord+(distanceBetweenTables*firstRow), Width, Height);
        	}
        	else if(i<(numTablesPerRow*2))
        	{
	        	g2.setColor(Color.darkGray);
	            g2.fillRect(XCord + distanceBetweenTables*(i-(numTablesPerRow*secondRow)), YCord+(distanceBetweenTables*secondRow), Width, Height);        		
        	}
        	else if(i<(numTablesPerRow*3))
        	{
	        	g2.setColor(Color.darkGray);
	            g2.fillRect(XCord + distanceBetweenTables*(i-(numTablesPerRow*thirdRow)), YCord+(distanceBetweenTables*thirdRow), Width, Height);
        	}
        }
        
        for(Gui gui : guis) 
        {
            if (gui.isPresent()) 
            {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) 
        {
            if (gui.isPresent()) 
            {
                gui.draw(g2);
            }
        }
    }

    public void addGui(CustomerGui gui) 
    {
        guis.add(gui);
    }

    public void addGui(WaiterGui gui) 
    {
        guis.add(gui);
    }
    
    public void setHost(HostAgent hostName)
    {
    	host = hostName;
    }
    
    public void addToCooking(String food)
    {
    	Cooking.add(food);
    }
    
    public void addToPlated(String food)
    {
    	Cooking.remove(food);
    	Plated.add(food);
    }
    
    public void removeFromPlated(String food)
    {
    	Plated.remove(food);
    }
}
