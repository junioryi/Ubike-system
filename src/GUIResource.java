import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIResource
{
        public Image background;
        public StationLabel[] stationResource;
        private UbikeSystem ubikeSystem;

        public GUIResource(UbikeSystem ubikeSystem)
        {
                this.ubikeSystem = ubikeSystem;
                ImageIcon backImageIcon = new ImageIcon(
                                this.getClass().getResource("images/TaipeiMap.bmp"));
                background = backImageIcon.getImage();
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
                private double x, y;
                private Station station;

                public StationLabel(ImageIcon ii, Station station,
                                double x, double y)
                {
                        super(ii);
                        mouseController = new MouseController();
                        this.station = station;
                        this.x = x;
                        this.y = y;
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
