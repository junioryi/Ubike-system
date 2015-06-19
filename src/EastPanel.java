import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.util.*;


public class EastPanel extends JPanel implements Constants, ActionListener
{
        private JPanel buttonPanel;
        private JPanel stationPanel;
        private UbikeSystem ubikeSystem;
        public JTextField inputID;

        //private JButton addUserButton;
        //private JButton oldUserButton;

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

                JPanel userPanel = new UserPanel();
                this.add(userPanel);

                buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(2,1));
                /*
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
                */

                //this.add(buttonPanel);

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
        public void setSystem(UbikeSystem us)
        {
                this.ubikeSystem = us;
        }
        public void actionPerformed(ActionEvent e)
        {
                this.remove(buttonPanel);
                String gotID;
                if ("add user".equals(e.getActionCommand()))
                {
                        System.out.println("add user button pushed");
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
        public void displayStation(Station station)
        {
                if (stationPanel != null) {
                        this.remove(stationPanel);
                }
                stationPanel = new JPanel();
                stationPanel.setLayout(new BoxLayout(stationPanel,
                            BoxLayout.Y_AXIS));
                String name = station.getName();
                int capacity = station.getCapacity();
                int available = station.getAvailable();

                JLabel stationName = new JLabel(name);
                JLabel stationCap  = new JLabel("Park spaces: " + capacity);
                JLabel stationAva  = new JLabel("Available bikes: " + available);
                stationPanel.add(stationName);
                stationPanel.add(stationCap);
                stationPanel.add(stationAva);
                this.remove(buttonPanel);
                this.add(stationPanel);
                this.add(buttonPanel);
                this.revalidate();
                this.repaint();
        }

        private class UserPanel extends JPanel implements ActionListener 
        {
                public JButton addUserButton;
                public JButton oldUserButton;
                public JTextField inputID;

                public UserPanel() {
                        setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH, Constants.USER_HEIGHT));
                        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                        setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

                        JLabel hint = new JLabel("Enter your id: ");
                        this.add(hint);
                        inputID = new JTextField(8);
                        inputID.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        System.out.println("User input: " + inputID.getText());
                                }
                        });
                        this.add(inputID);

                        oldUserButton = new JButton("Old User");
                        oldUserButton.setActionCommand("old user");
                        oldUserButton.addActionListener(this);
                        this.add(oldUserButton);

                        addUserButton = new JButton("New User");
                        addUserButton.setActionCommand("add user");
                        addUserButton.addActionListener(this);
                        this.add(addUserButton);
                }
                public void actionPerformed(ActionEvent e)
                {
                        if ("add user".equals(e.getActionCommand()))
                        {
                                System.out.println("add user button pushed");
                                createUser();
                        }
                        else if ("old user".equals(e.getActionCommand()))
                        {
                                System.out.println("old user button pushed");
                                System.out.println(inputID.getText());
                                try {
                                        int id = Integer.parseInt(inputID.getText());
                                        displayUserInfo(id);
                                }
                                catch (Exception excp){
                                        System.out.println("wrong input");
                                        inputID.setText("Wrong input, try again.");
                                }
                        }

                }
                public void createUser() 
                {
                        int userIndex = ubikeSystem.createUser();
                        displayUserInfo(userIndex);
                }
                public void displayUserInfo(int index)
                {
                        this.removeAll();
                        User user = ubikeSystem.getUser(index);
                        boolean status  = user.getStatus();
                        long rentTime   = user.getRenttime();
                        long returnTime = user.getReturntime();
                        long totalTime  = user.getTotalTime();
                        JLabel indexLabel  = new JLabel("Hi, user no." + index);
                        JLabel stateLabel  = new JLabel("Is renting: " + status);
                        JLabel startTime   = new JLabel("" + rentTime);
                        JLabel returnLabel = new JLabel("" + returnTime);
                        JLabel totalLabel  = new JLabel("Total time: " + totalTime);
                      
                        this.add(indexLabel);
                        this.add(stateLabel);
                        if (status) {
                                this.add(totalLabel);
                        }
                        this.updateUI();
                }
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

