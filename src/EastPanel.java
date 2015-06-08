import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class EastPanel extends JPanel implements Constants
{
        //public static final int width  = Constants.EAST_PANEL_WIDTH;
        //public static final int height = Constants.HEIGHT;

        /**
         * Panel constructor.
         * A panel I would like to show info about the station.
         * When user click on the station label, it will show
         * the info about that station in this panel.
         *
         * TODO: Showing info NOT implemented.
         *
         */
        public EastPanel()
        {
                //setBackground(Color.BLUE);
                
                setLayout(new BorderLayout());
                
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(1, 3));
                
                JButton rentButton = new JButton("借車");
                buttonPanel.add(rentButton);
                JButton returnButton = new JButton("還車");
                buttonPanel.add(returnButton);
                JButton queryButton = new JButton("查詢");
                buttonPanel.add(queryButton);
                
                add(buttonPanel, BorderLayout.SOUTH);


        }

        @Override
        public void paintComponent(Graphics g)
        {
                super.paintComponent(g);
                //g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 60));
                String s = "hello world";
                //g.drawString(s, getWidth()/2 - g.getFontMetrics().stringWidth(s)/2,
                //                getHeight()/2 + g.getFontMetrics().getHeight()/2);
                g.drawString(s, 0, 0+g.getFontMetrics().getHeight());

        }

        /**
         * Testing code for east panel.
         */
        public static void main(String[] args)
        {
                JFrame f = new JFrame();
                f.setSize(500, 500);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setContentPane(new EastPanel());
                f.setVisible(true);
        }
}

