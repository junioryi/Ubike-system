import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class EastPanel extends JPanel implements Constants, ActionListener
{
        private JPanel buttonPanel;
        public JTextField inputID;
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
                setLayout(new FlowLayout());
                // Display project name.
                JLabel welcome = new JLabel("Ubike System");
                welcome.setFont(new Font("Verdana", 1, 20));
                this.add(welcome);

                buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(2,1));
                // Add new user button.
                JButton addUserButton = new JButton("New User");
                addUserButton.setActionCommand("add user");
                addUserButton.addActionListener(this);
                buttonPanel.add(addUserButton);

                // Old user button.
                JButton oldUserButton = new JButton("Old User");
                oldUserButton.setActionCommand("old user");
                oldUserButton.addActionListener(this);                
                buttonPanel.add(oldUserButton);

                this.add(buttonPanel);

                /*
                setLayout(new BorderLayout());
                
                buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(1, 3));
                
                JButton rentButton = new JButton("借車");
                buttonPanel.add(rentButton);
                JButton returnButton = new JButton("還車");
                buttonPanel.add(returnButton);
                JButton queryButton = new JButton("查詢");
                buttonPanel.add(queryButton);
                
                add(buttonPanel, BorderLayout.NORTH);
                */


        }
        public void actionPerformed(ActionEvent e)
        {
                this.remove(buttonPanel);
                String gotID;
                if ("add user".equals(e.getActionCommand()))
                {
                        System.out.println("add user button pushed");
                        createUser();
                }
                else if ("old user".equals(e.getActionCommand()))
                {
                        System.out.println("old user button pushed");
                        oldUserAction();
                }

                // Repaint the panel.
                this.revalidate();
                this.repaint();

        }
        public void createUser()
        {
                // TODO
        }
        public void oldUserAction()
        {
                JPanel oldUserPanel = new JPanel(new FlowLayout());
                JLabel hint = new JLabel("enter your id: ");
                oldUserPanel.add(hint);

                inputID = new JTextField(8);
                inputID.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("User input: " + inputID.getText());
                        }
                });
                oldUserPanel.add(inputID);

                this.add(oldUserPanel);
        }
                

        @Override
        public void paintComponent(Graphics g)
        {
                super.paintComponent(g);
                /*
                g.setFont(new Font("serif", Font.BOLD, 60));
                String s = "hello world";
                g.drawString(s, 0, 0+g.getFontMetrics().getHeight());
                */

        }
        public static void displayStation(Station station)
        {
                JPanel stationPanel = new JPanel();
                stationPanel.setLayout(new BoxLayout(stationPanel,
                            BoxLayout.Y_AXIS));
                String name = station.getName();
                int capacity = station.getCapacity();
                int available = station.getAvailable();
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

