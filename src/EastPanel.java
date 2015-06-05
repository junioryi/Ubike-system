import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class EastPanel extends JPanel implements Constants
{
        //public static final int width  = Constants.EAST_PANEL_WIDTH;
        //public static final int height = Constants.HEIGHT;

        public EastPanel()
        {
                //setBackground(Color.BLUE);
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

        public static void main(String[] args)
        {
                JFrame f = new JFrame();
                f.setSize(500, 500);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setContentPane(new EastPanel());
                f.setVisible(true);
        }
}

