import javax.swing.*;
import java.io.*;
import java.awt.BorderLayout;

public class MainFrame extends JFrame implements Constants
{
        //public UbikeSystem ubikeSystem;
        public MainFrame() throws IOException
        {
                //add(ubikeSystem);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(1200, Constants.CENTER_WIDTH + Constants.EAST_PANEL_WIDTH);
                setLayout(new BorderLayout());

                JPanel ubikeSystem = new UbikeSystem();
                ubikeSystem.setSize(Constants.CENTER_WIDTH, Constants.HEIGHT);
                getContentPane().add(ubikeSystem, BorderLayout.CENTER);

                JPanel eastPanel = new EastPanel();
                eastPanel.setSize(Constants.EAST_PANEL_WIDTH, Constants.HEIGHT);
                getContentPane().add(eastPanel, BorderLayout.EAST);

                setTitle("OOP Fianl: Ubike Renting System");
                setResizable(false);
                setVisible(true);
        }
        public static void main(String[] args) throws IOException
        {
                MainFrame mainFrame = new MainFrame();
        }
}

