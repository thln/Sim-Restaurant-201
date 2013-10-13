package restaurant.gui;

import restaurant.WaiterAgent;
import restaurant.HostAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {

    private final int WINDOWX = 550; //600; //hardcoded 1/2 the size of restaurant GUI WINDOWX
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
	private static final int yCordKitchen = 540;
    private final int Height = 50;
    private final int Width = 50;
    private final int distanceBetweenTables = 100;
    private final int numTablesPerRow = 3;
    private final int firstRow = 0;
    private final int secondRow = 1;
    private final int thirdRow = 2;
    private final int frameDelay = 10; //20 is standard
    private Image bufferImage;
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
        g2.fillRect(xCordCashier, yCordCashier, Height, Width);
        g2.fillRect(xCordBreakRoom, yCordBreakRoom, Height, Width);
        g2.fillRect(xCordKitchen, yCordKitchen, Height, Width);
        g2.setColor(Color.BLACK);
        g2.drawString("Cashier", xCordCashier, yCordCashier+20);
        g2.drawString("Break Room", xCordBreakRoom, yCordBreakRoom+20);
        g2.drawString("Kitchen", xCordKitchen, yCordKitchen+20);
        
        //Here is the table
        
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
        /*
        g2.setColor(Color.ORANGE);
        g2.fillRect(XCord, YCord, Width, Height);//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(XCord + 2*Width, YCord, Width, Height);//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(XCord + 4*Width, YCord, Width, Height);//200 and 250 need to be table params
        */
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
    
}
