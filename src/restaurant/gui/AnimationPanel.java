package restaurant.gui;

import restaurant.WaiterAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {

    private final int WINDOWX = 600;
    private final int WINDOWY = 500;
    private final int XCord = 200;
    private final int YCord = 250;
    private final int Height = 50;
    private final int Width = 50;
    private Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    private WaiterAgent host;
    
    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(20, this );
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );

        //Here is the table
        
        for(int i=1; i <= host.tables.size(); i++)
        {
        	g2.setColor(Color.darkGray);
            g2.fillRect(XCord + 100*(i-1), YCord, Width, Height);
        }
        /*
        g2.setColor(Color.ORANGE);
        g2.fillRect(XCord, YCord, Width, Height);//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(XCord + 2*Width, YCord, Width, Height);//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(XCord + 4*Width, YCord, Width, Height);//200 and 250 need to be table params
        */
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
    }

    public void addGui(CustomerGui gui) {
        guis.add(gui);
    }

    public void addGui(WaiterGui gui) {
        guis.add(gui);
    }
    
    public void setHost(WaiterAgent hostName)
    {
    	host = hostName;
    }
    
}
