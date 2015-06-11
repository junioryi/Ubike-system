import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;

public class GUIResource
{
        public Image background;
        public StationLabel[] stationResource;
        private UbikeSystem ubikeSystem;
        private JPanel eastPanel;

        public GUIResource(UbikeSystem ubikeSystem, ArrayList<Station> stationList,
                JPanel eastPanel)
        {
                this.ubikeSystem = ubikeSystem;
                this.eastPanel = eastPanel;

                // The background image left down corner is 
                // 25.015761N, 121.487632E
                // up-right
                // 25.112275N, 121.602259E
                ImageIcon backImageIcon = new ImageIcon(
                                this.getClass().getResource("images/Taipei.png"));
                
                background = backImageIcon.getImage();

                stationResource = new StationLabel[ stationList.size() ];
                for (int i = 0; i < stationList.size(); ++i)
                {
                        Station station = (Station)stationList.get(i);
                        
                        ImageIcon ii = new ImageIcon(
                                        this.getClass().getResource("images/greenDot.png"));
                        Image image = ii.getImage();
                        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                        ii = new ImageIcon(newimg);

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

                /** 
                 * Construct label for each station.
                 * Modify each station's location according to the size
                 * of the frame and add the mouse listener.
                 *
                 * @param station a station which has location info.
                 * @param ii ImageIcon, to display the status of station,
                 *              red indicate full, green indicate available
                 *              Not implemented.
                 * @param index the index of the station, can be use by 
                 *              mouse listener.
                 */
                public StationLabel(Station station, ImageIcon ii, int index)
                {
                        super(ii, JLabel.CENTER);
                        mouseController = new MouseController();
                        this.station = station;
                        this.index = index;
                        // Boundary:
                        // 25.015761N, 121.487632E
                        // 25.112275N, 121.602259E
                        double width  = 121.602259 - 121.487632;
                        double height = 25.112275  - 25.015761;
                        double widthScale  = Constants.CENTER_WIDTH/width;
                        double heightScale = Constants.HEIGHT/height;

                        double modifyX = (station.getX() - 121.487632) * widthScale;
                        double modifyY = Constants.HEIGHT - ((station.getY() - 25.015761) * heightScale);
                        //System.out.println("" + (station.getX() - 121.487632) + ", " + modifyX);

                        this.x = (modifyX < Constants.CENTER_WIDTH) ? (int)modifyX : -100;
                        this.y = (modifyY < Constants.HEIGHT) ? (int)modifyY : -100;

                        this.ii = ii;
                        //setBounds(x, y, 10, 10);
                        //setIcon(ii);
                        //setText("test"); // Doesn't show ?
                        setLocation(this.x, this.y);
                        setSize(30, 30);
                        setIcon(ii); 
                        //updateUI();
                        //this.setBounds(x, y, 10, 10);

                }
                @Override
                public void paintComponent(Graphics g)
                {
                        super.paintComponent(g);
                        //System.out.println("hello");
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

