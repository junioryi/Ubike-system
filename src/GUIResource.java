import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class GUIResource
{
        public Image background;
        public StationLabel[] stationResource;
        private UbikeSystem ubikeSystem;

        public GUIResource(UbikeSystem ubikeSystem, ArrayList<Station> stationList)
        {
                this.ubikeSystem = ubikeSystem;

                // The background image left down corner is 
                // 25.015761N, 121.487632E
                ImageIcon backImageIcon = new ImageIcon(
                                this.getClass().getResource("images/Taipei.png"));
                
                background = backImageIcon.getImage();

                stationResource = new StationLabel[ stationList.size() ];
                for (int i = 0; i < stationList.size(); ++i)
                {
                        Station station = (Station)stationList.get(i);
                        ImageIcon ii = new ImageIcon(
                                        this.getClass().getResource("images/greenDot.png"));

                        stationResource[i] = new StationLabel(station, ii, i);


                }
        }

        public void setStationLocation(int index, int x, int y)
        {
                stationResource[index].setLocation(x, y);
        }
        public void enableMouseListener(int index)
        {
                stationResource[index].enableMouseListener();
        }
        public void disableMouseListener(int index)
        {
                stationResource[index].disableMouseListener();
        }

        private class StationLabel extends JLabel
        {
                private MouseController mouseController;
                private int x, y;
                private int index;
                private int capacity;
                private int available;
                private ImageIcon ii;
                private Station station;

                public StationLabel(Station station, ImageIcon ii, int index)
                {
                        super();
                        mouseController = new MouseController();
                        this.station = station;
                        this.index = index;
                        this.x = (int)station.getX();
                        this.y = (int)station.getY();
                        this.ii = ii;
                        setBounds(x, y, 10, 10);
                        //setIcon(ii);
                        updateUI();
                        //System.out.println("x, y: " + x + ", " + y);
                        //this.setBounds(x, y, 10, 10);

                }
                @Override
                public void paintComponent(Graphics g)
                {
                        super.paintComponent(g);
                        System.out.println("hello");
                        g.setColor(Color.yellow);
                        g.drawOval(x, y, 70, 70);
                        g.fillOval(x, y, 70, 70);
                        ui.update(g, this);
                        //super.paintComponent(g);

                        //g.drawImage(ii, x, y, 10, 10, this);
                }
                public void enableMouseListener()
                {
                        addMouseListener(mouseController);
                }
                public void disableMouseListener()
                {
                        removeMouseListener(mouseController);
                }
                class MouseController extends MouseAdapter
                {
                        public void mouseClicked(MouseEvent e)
                        {
                                // show the information about 
                                // the station.
                        }
                }
        }
}
