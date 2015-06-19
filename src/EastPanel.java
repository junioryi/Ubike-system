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
        private EastPanel.UserPanel userPanel;
        private JLabel errorLabel;
        private UbikeSystem ubikeSystem;

        private int userIndex;
        private Station station;

        private JTextField inputID;
        private boolean hasUser;
        private boolean hasStation;

        /**
         * Panel constructor.
         * A panel I would like to show info about the station.
         * When user click on the station label, it will show
         * the info about that station in this panel.
         */
        public EastPanel()
        {
                setLayout(new FlowLayout());
                // Display project name.
                JLabel welcome = new JLabel("Ubike System");
                welcome.setFont(new Font("Verdana", 1, 20));
                this.add(welcome);

                // Display error if necessary.
                errorLabel = new JLabel("");
                errorLabel.setForeground(Color.red);
                this.add(errorLabel);

                hasUser = false;
                hasStation = false;

                userPanel = new UserPanel(this);
                userPanel.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH, Constants.USER_HEIGHT));
                this.add(userPanel);

                buttonPanel = new JPanel(new GridLayout(1, 3));
                // Rent button
                JButton rentButton = new JButton("Rent");
                rentButton.setActionCommand("rent");
                rentButton.addActionListener(this);
                buttonPanel.add(rentButton);
                // Return button
                JButton returnButton = new JButton("Return");
                returnButton.setActionCommand("return");
                returnButton.addActionListener(this);
                buttonPanel.add(returnButton);

                // Only when user has login and choose a station
                // can the user do the renting or returning.
                if (hasUser && hasStation) {
                        this.add(buttonPanel);
                }

        }
        /** 
         * Connect to the system.
         * To send user input info to the system, 
         * renting, returning and so on.
         *
         * @param UbikeSystem, a working system.
         */
        public void setSystem(UbikeSystem us)
        {
                this.ubikeSystem = us;
        }
        public void actionPerformed(ActionEvent e)
        {
                this.remove(buttonPanel);
                String gotID;
                if ("rent".equals(e.getActionCommand()))
                {
                        System.out.println("Rent button pushed");
                        int info = ubikeSystem.rentBike(userIndex, station);
                        switch(info) {
                                case(NOT_ENOUGH):
                                        errorLabel.setText("Not enough momey left.");
                                        break;
                                case(IS_RENTING):
                                        errorLabel.setText("User has rented a bike.");
                                        break;
                                case(NO_BIKE):
                                        errorLabel.setText("There's no bike in station.");
                                        break;
                                case(RENT_SUCCESS):
                                        userPanel.displayUserInfo(userIndex);
                        }
                }
                else if ("return".equals(e.getActionCommand()))
                {
                        System.out.println("Return button pushed");
                        int info = ubikeSystem.returnBike(userIndex, station);
                        switch(info) {
                                case (NO_PLACE):
                                        errorLabel.setText("No space to return.");
                                        break;
                                case (HASNT_RENTED):
                                        errorLabel.setText("User didn't rent a bike.");
                                        break;
                                case (RETURN_SUCCESS):
                                        userPanel.displayUserInfo(userIndex);
                        }
                }

                // Repaint the panel.
                this.revalidate();
                this.repaint();

        }

        @Override
        public void paintComponent(Graphics g)
        {
                super.paintComponent(g);

        }
        public void displayStation(Station station)
        {
                if (stationPanel != null) {
                        this.remove(stationPanel);
                }
                // East panel record the station user chose.
                this.station = station;

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
        public void relogin() 
        {
                userPanel = new UserPanel(this);
                userIndex = -1;
                station = null;
                hasUser = false;
                hasStation = false;
                this.revalidate();
                this.repaint();
        }
                

        private class UserPanel extends JPanel implements ActionListener 
        {
                public EastPanel eastPanel;
                public JButton addUserButton;
                public JButton oldUserButton;
                public JButton reloginButton;
                public JTextField inputID;

                public UserPanel(EastPanel eastPanel) {
                        this.eastPanel = eastPanel;
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

                        // Change user button
                        reloginButton = new JButton("Relogin");
                        reloginButton.setActionCommand("relogin");
                        reloginButton.addActionListener(this);
                }
                public void actionPerformed(ActionEvent e)
                {
                        if ("add user".equals(e.getActionCommand()))
                        {
                                System.out.println("add user button pushed");
                                int id = ubikeSystem.createUser();
                                userIndex = id;
                                displayUserInfo(id);
                                //createUser();
                        }
                        else if ("old user".equals(e.getActionCommand()))
                        {
                                System.out.println("old user button pushed");
                                System.out.println(inputID.getText());
                                try {
                                        int id = Integer.parseInt(inputID.getText());
                                        userIndex = id;
                                        displayUserInfo(id);
                                }
                                catch (Exception excp){
                                        System.out.println("wrong input");
                                        inputID.setText("Wrong input, try again.");
                                }
                        }
                        else if ("relogin".equals(e.getActionCommand()))
                        {
                                System.out.println("Relogin button pushed.");
                                eastPanel.relogin();
                                this.removeAll();
                                this.add(inputID);
                                this.add(oldUserButton);
                                this.add(addUserButton);
                        }

                }
                /*
                public void createUser() 
                {
                        int id = ubikeSystem.createUser();
                        userIndex = id;
                        displayUserInfo(id);
                }
                */
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
                        JLabel momeyLabel  = new JLabel("Money: " + user.getValue());
                        JLabel startTime   = new JLabel("" + rentTime);
                        JLabel returnLabel = new JLabel("" + returnTime);
                        JLabel totalLabel  = new JLabel("Total time: " + totalTime);
                        JLabel rentStation = new JLabel("");
                      
                        this.add(indexLabel);
                        this.add(stateLabel);
                        this.add(momeyLabel);
                        if (status) {
                                String stationName = user.getRentStation().toString();
                                rentStation.setText("Rent station: " + stationName);
                                this.add(totalLabel);
                                this.add(rentStation);
                        }
                        this.add(reloginButton);
                        this.updateUI();
                }
        }


        /**
         * Testing code for east panel.
         * 
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

