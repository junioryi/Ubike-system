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
        public static void main(String[] args)
        {
                EventQueue.incokeLater(new Runnable() {
                        public void run()
                        {
                                GUIResource gr = new GUIResource();
                                gr.setVisible(true);
                        }
                });
        }
}
