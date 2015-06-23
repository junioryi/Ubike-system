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
        private JPanel searchPanel;
        private EastPanel.UserPanel userPanel;
        private JLabel errorLabel;
        private UbikeSystem ubikeSystem;
        private JTextArea textArea;

        private int userIndex;
        private Station station;

        public JTextField inputID;
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
                //
                // Welcom Panel
                //
                setLayout(new FlowLayout());
                // Display project name.
                JLabel welcome = new JLabel("Ubike System");
                welcome.setFont(new Font("Verdana", 1, 20));
                this.add(welcome);

                //
                // Error Message.
                //
                errorLabel = new JLabel("");
                errorLabel.setForeground(Color.red);
                this.add(errorLabel);

                // booleans which check user has click a
                // station and login.
                hasUser = false;
                hasStation = false;

                //
                // User Panel
                //
                userPanel = new UserPanel(this);
                userPanel.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH, Constants.USER_HEIGHT));
                this.add(userPanel);

                //
                // Station Panel
                //
                stationPanel = new JPanel();
                stationPanel.setLayout(new BoxLayout(stationPanel, BoxLayout.Y_AXIS));
                stationPanel.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH, 150));
                stationPanel.setBorder( new TitledBorder( new EtchedBorder(), "Station info: "));
                this.add(stationPanel);

                //
                // Rent & Return Panel
                //
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
                this.add(buttonPanel);

                //
                // Search Panel
                //
                searchPanel = new JPanel(new BorderLayout());
                searchPanel.setBorder( new TitledBorder ( new EtchedBorder(), "Search stations"));
                searchPanel.setSize(Constants.EAST_PANEL_WIDTH, 300);
                JPanel searchButtonPanel = new JPanel(new GridLayout(1, 3));
                searchButtonPanel.setSize(100, 100);
                
                // Search by distance.
                JButton disButton = new JButton("Dis");
                disButton.setSize(20, 20);
                disButton.setPreferredSize(new Dimension(20, 20));
                disButton.setActionCommand("dis search");
                disButton.addActionListener(this);
                searchButtonPanel.add(disButton);
                // Search by available parking spaces.
                JButton spaceButton = new JButton("space");
                spaceButton.setActionCommand("space search");
                spaceButton.addActionListener(this);
                searchButtonPanel.add(spaceButton);
                // Search by available bikes.
                JButton bikesButton = new JButton("bikes");
                bikesButton.setActionCommand("bikes search");
                bikesButton.addActionListener(this);
                searchButtonPanel.add(bikesButton);
               
                searchPanel.add(searchButtonPanel, BorderLayout.PAGE_START);

                textArea = new JTextArea("Click the buttons to sort near station by distance,\n available spaces, available bikes.", 20, 20);
                textArea.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH-20, 400));
                textArea.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
                textArea.setEditable(false);
                JScrollPane scroll = new JScrollPane(textArea,
                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                searchPanel.add(scroll, BorderLayout.CENTER);

                this.add(searchPanel);
        }
        /**
         * Repaint all the panel in east panel.
         */
        public void repaintAll()
        {
                // TODO
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
                //this.remove(buttonPanel);
                String gotID;
                try {
                        int mouseX = UbikeSystem.getMouseX();
                        int mouseY = UbikeSystem.getMouseY();
                }
                catch (Exception ecp) {
                        errorLabel.setText("Please Click a postion");
                }
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
                else if ("dis search".equals(e.getActionCommand()))
                {
                        System.out.println("dis search button pushed.");

                }
                else if ("space search".equals(e.getActionCommand()))
                {
                        System.out.println("space search button pushed.");
                }
                else if ("bikes search".equals(e.getActionCommand()))
                {
                        System.out.println("bikes search button pushed.");
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
                // Reset error message.
                errorLabel.setText("");

                stationPanel.removeAll();
                // Remove last station label.
                /*
                if (stationPanel != null) {
                        this.remove(stationPanel);
                }
                */
                // East panel record the station user chose.
                this.station = station;

                /*
                stationPanel = new JPanel();
                stationPanel.setLayout(new BoxLayout(stationPanel,
                            BoxLayout.Y_AXIS));
                */
                String name = station.getName();
                int capacity = station.getCapacity();
                int available = station.getAvailable();

                JLabel stationName = new JLabel(name);
                JLabel stationCap  = new JLabel("Park spaces: " + capacity);
                JLabel stationAva  = new JLabel("Available bikes: " + available);
                stationPanel.add(stationName);
                stationPanel.add(stationCap);
                stationPanel.add(stationAva);
                //this.remove(buttonPanel);
                //this.add(stationPanel);
                //this.add(buttonPanel);
                this.revalidate();
                this.repaint();
        }
        public void relogin() 
        {
                this.remove(userPanel);
                this.remove(buttonPanel);
                if (stationPanel != null) {
                        this.remove(stationPanel);
                }
                userPanel = new UserPanel(this);
                this.add(userPanel);
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
                        // connect to east panel.
                        this.eastPanel = eastPanel;
                        setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH, Constants.USER_HEIGHT));
                        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                        setBorder( new TitledBorder ( new EtchedBorder(), "User Info"));

                        JLabel hint = new JLabel("Enter your id: ");
                        this.add(hint);
                        inputID = new JTextField(8);
                        inputID.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        System.out.println("User input: " + inputID.getText());
                                }
                        });
                        inputID.setMaximumSize( new Dimension(200, 25) );
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
                        // Reset error message.
                        errorLabel.setText("");
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
                                        if (id > ubikeSystem.getUserNum()) {
                                                errorLabel.setText("wrong id.");
                                        }
                                        else {
                                                errorLabel.setText("");
                                                userIndex = id;
                                                displayUserInfo(id);
                                        }
                                }
                                catch (Exception excp){
                                        excp.printStackTrace();
                                        System.out.println("wrong input");
                                        errorLabel.setText("Wrong input, try again.");
                                }
                        }
                        else if ("relogin".equals(e.getActionCommand()))
                        {
                                System.out.println("Relogin button pushed.");
                                this.removeAll();
                                this.add(inputID);
                                this.add(oldUserButton);
                                this.add(addUserButton);
                                eastPanel.relogin();
                        }

                        EastPanel.this.revalidate();
                        EastPanel.this.repaint();

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
                        JLabel startTime   = new JLabel("Rent time: " + rentTime);
                        JLabel returnLabel = new JLabel("Last return: " + returnTime);
                        JLabel totalLabel  = new JLabel("Total time: " + totalTime);
                        JLabel rentStation = new JLabel("");
                      
                        this.add(indexLabel);
                        this.add(stateLabel);
                        this.add(momeyLabel);
                        if (status) {
                                this.add(startTime);
                                String stationName = user.getRentStation().toString();
                                rentStation.setText("Rent station: " + stationName);
                                this.add(totalLabel);
                                this.add(rentStation);
                        }
                        else if (user.getReturnStation() != null){
                                // User return the bike.
                                String stationName = user.getReturnStation().toString();
                                returnLabel.setText("Last Return: " + stationName);
                                this.add(returnLabel);
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

