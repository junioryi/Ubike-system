import javax.swing.*;
import java.io.*;

public class MainFrame extends JFrame
{
        public UbikeSystem ubikeSystem;
        public MainFrame() throws IOException
        {
                ubikeSystem = new UbikeSystem();
                add(ubikeSystem);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(1024, 800);
                setTitle("OOP Fianl: Ubike Renting System");
                setResizable(false);
                setVisible(true);
        }
        public static void main(String[] args) throws IOException
        {
                MainFrame mainFrame = new MainFrame();
        }
}

