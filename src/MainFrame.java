import javax.swing.*;
import java.io.*;
import java.awt.*;

public class MainFrame extends JFrame implements Constants
{
        //public UbikeSystem ubikeSystem;
        public MainFrame() throws IOException
        {
                //add(ubikeSystem);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(Constants.WIDTH, Constants.HEIGHT);
                setLayout(new BorderLayout());

                EastPanel eastPanel = new EastPanel();
                eastPanel.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH, Constants.HEIGHT));
                getContentPane().add(eastPanel, BorderLayout.EAST);

                // Add Taipei map panel.
                UbikeSystem ubikeSystem = new UbikeSystem(eastPanel);
                ubikeSystem.setPreferredSize(new Dimension(Constants.CENTER_WIDTH, Constants.HEIGHT));
                getContentPane().add(ubikeSystem, BorderLayout.CENTER);

                eastPanel.setSystem(ubikeSystem);

                setTitle("OOP Fianl: Ubike Renting System");
                setResizable(false);
                setVisible(true);
        }
        public static void main(String[] args) throws IOException
        {
                MainFrame mainFrame = new MainFrame();
        }
}

