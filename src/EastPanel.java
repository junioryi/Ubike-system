import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


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
        private int restrict;
        private double mouseX, mouseY;
        private Station station;

        public JTextField inputID;
        public JTextField searchDis;
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
                JLabel welcome = new JLabel("Ubike System");
                welcome.setFont(new Font("Verdana", 1, 20));
                this.add(welcome);

                //
                // Error Message.
                //
                errorLabel = new JLabel("");
                errorLabel.setForeground(Color.red);
                this.add(errorLabel);

                // booleans which check user has click on a
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
                mouseX = -1;
                mouseY = -1;
                searchPanel = new JPanel(new BorderLayout());
                searchPanel.setBorder( new TitledBorder ( new EtchedBorder(), "Search stations"));
                searchPanel.setSize(Constants.EAST_PANEL_WIDTH, 300);
                JPanel searchButtonPanel = new JPanel(new GridLayout(1, 3));
                searchButtonPanel.setSize(100, 100);
                
                searchDis = new JTextField("Distance");
                searchDis.setMaximumSize( new Dimension(200, 25) );
                searchPanel.add(searchDis, BorderLayout.SOUTH);

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

                textArea = new JTextArea("Click the buttons to sort near station by distance,\n available spaces, available bikes.", 13, 20);
                //textArea.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH-20, 400));
                textArea.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
                textArea.setEditable(false);
                JScrollPane scroll = new JScrollPane(textArea,
                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scroll.setPreferredSize(new Dimension(Constants.EAST_PANEL_WIDTH - 20, 220));
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
                        // Mouse click the position.                        
                        mouseX = UbikeSystem.getMouseX();
                        mouseY = UbikeSystem.getMouseY();
                }
                catch (Exception ecp) {
                        errorLabel.setText("Please Click a postion");
                }
                
                try {
                        // If user specify the distance restriction.
                        restrict = Integer.parseInt(searchDis.getText());
                }
                catch (Exception ecp) {
                        restrict = -1;
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
                                        displayStation(station);
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
                                        displayStation(station);
                        }
                }
                else if (mouseX == -1) {
                        // don't sort 
                        return;
                }
                        
                else if ("dis search".equals(e.getActionCommand()))
                {
                        if (mouseX == -1) {
                                // If user didn't click a position, sort cannot be done.
                                textArea.setText("Pleast click your position.");
                        }
                        else {
                                System.out.println("dis search button pushed.");
                                LinkedList<Station> rank = ubikeSystem.findNearest(mouseX, mouseY, restrict);
                                displayList(rank, 0);
                        }
                }
                else if ("space search".equals(e.getActionCommand()))
                {
                        System.out.println("space search button pushed.");
                        LinkedList<Station> rank = ubikeSystem.orderBySpace(mouseX, mouseY, restrict);
                        displayList(rank, 1);
                }
                else if ("bikes search".equals(e.getActionCommand()))
                {
                        System.out.println("bikes search button pushed.");
                        LinkedList<Station> rank = ubikeSystem.orderByBike(mouseX, mouseY, restrict);
                        displayList(rank, 2);
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

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(4);
                /*
                stationPanel = new JPanel();
                stationPanel.setLayout(new BoxLayout(stationPanel,
                            BoxLayout.Y_AXIS));
                */
                String name = station.getName();
                String location = station.getAddress();
                int capacity = station.getCapacity();
                int available = station.getAvailable();
                double x = station.getX();
                double y = station.getY();
                mouseX = station.getGUIx();
                mouseY = station.getGUIy();
                //double x = station.getGUIx();
                //double y = station.getGUIy();

                JLabel stationIdx  = new JLabel("Index: " + station.getIndex());
                JLabel stationName = new JLabel("Name : " + name);
                JLabel stationCap  = new JLabel("Park spaces: " + capacity);
                JLabel stationAva  = new JLabel("Available bikes: " + available);
                JLabel stationPos  = new JLabel("At: " + location);
                JLabel position = new JLabel("Position: " + df.format(x) + ", " + df.format(y));
                stationPanel.add(stationIdx);
                stationPanel.add(stationName);
                stationPanel.add(stationCap);
                stationPanel.add(stationAva);
                stationPanel.add(stationPos);
                stationPanel.add(position);
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
                this.remove(stationPanel);
                this.remove(searchPanel);
                //this.remove(userPanel);
                userPanel = new UserPanel(this);
                this.add(userPanel);
                this.add(stationPanel);
                this.add(buttonPanel);
                this.add(searchPanel);
                //this.remove(buttonPanel);
                //if (stationPanel != null) {
                //        this.remove(stationPanel);
                //}
                //userPanel = new UserPanel(this);
                //this.add(userPanel);
                userIndex = -1;
                station = null;
                hasUser = false;
                hasStation = false;
                this.revalidate();
                this.repaint();
        }
        public void setMousePosition(int x, int y)
        {                
                mouseX = 1.0*x;
                mouseY = 1.0*y;
                textArea.setText("You're at\n x =" + x + "\n y = " + y);
        }
        public void displayList(LinkedList<Station> rank, int which)
        {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                String display = "";
                //System.out.println("mouse at : " + mouseX + ", " + mouseY);
                //display += "Mouse at (" + mouseX + ", " + mouseY + ")";
                for (Station s : rank) {
                        display += s.getName();
                        switch (which) {
                                case(0):
                                        display += ", dis = " + df.format(s.getDistance(mouseX, mouseY));
                                        break;
                                case(1):
                                        // sort by space
                                        display += ", space = " + s.getCapacity();
                                        break;
                                case(2):
                                        // sort by bikes
                                        display += ", bikes = " + s.getAvailable();
                                        break;
                        }
                        display += "\n";
                }
                textArea.setText(display);
        }
                
        private class UserPanel extends JPanel implements ActionListener 
        {
                public EastPanel eastPanel;
                public JPanel userButtonPanel;
                public JButton addUserButton;
                public JButton oldUserButton;
                public JButton reloginButton;
                public JButton depositButton;
                public JTextField inputID;
                public JTextField moneyInput;

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

                        // Deposit button.
                        depositButton = new JButton("Deposit");
                        depositButton.setActionCommand("deposit");
                        depositButton.addActionListener(this);
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
                        else if ("deposit".equals(e.getActionCommand()))
                        {
                                System.out.println("Deposit button pushed.");
                                try {
                                        int money = Integer.parseInt(moneyInput.getText());
                                        ubikeSystem.userDeposit(userIndex, money);
                                        displayUserInfo(userIndex);
                                }
                                catch (Exception excp) {
                                        excp.printStackTrace();
                                        System.out.println("Deposit money exception.");
                                        errorLabel.setText("Please input correct money.");
                                }

                        }

                        EastPanel.this.revalidate();
                        EastPanel.this.repaint();

                }
                public void displayUserInfo(int index)
                {
                        this.removeAll();
                        User user = ubikeSystem.getUser(index);
                        boolean status  = user.getStatus();
                        long rentTime   = user.getRenttime();
                        long returnTime = user.getReturntime();
                        double totalTime  = user.getTotalTime() * 60;
                        int charge      = user.getCharge();
                        JLabel indexLabel  = new JLabel("Hi, user no." + index);
                        JLabel stateLabel  = new JLabel("Is renting: " + status);
                        JLabel momeyLabel  = new JLabel("Money: " + user.getValue());
                        //JLabel startTime   = new JLabel("Rent time: " + rentTime);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        JLabel startTime   = new JLabel("Rent time: ");
                        JLabel returnLabel = new JLabel("Last return: " + returnTime);
                        JLabel totalLabel  = new JLabel("Last rent time: " + totalTime + "s");
                        JLabel chargeLabel = new JLabel("Last charge: " + charge);
                        JLabel rentStation = new JLabel("");
                      
                        this.add(indexLabel);
                        this.add(stateLabel);
                        this.add(momeyLabel);
                        if (status) {
                                this.add(startTime);
                                String stationName = user.getRentStation().toString();
                                rentStation.setText("Rent station: " + stationName);
                                startTime.setText("Rent time: " + dateFormat.format(user.getRentDate()));
                                this.add(totalLabel);
                                this.add(rentStation);
                        }
                        else if (user.getReturnStation() != null){
                                // User return the bike.
                                String stationName = user.getReturnStation().toString();
                                returnLabel.setText("Last Return: " + stationName);
                                this.add(returnLabel);
                                this.add(totalLabel);
                                this.add(chargeLabel);
                        }
                        this.add(reloginButton);

                        moneyInput = new JTextField("How much?");
                        moneyInput.setMaximumSize( new Dimension(200, 25) );

                        this.add(moneyInput);
                        this.add(depositButton);
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

