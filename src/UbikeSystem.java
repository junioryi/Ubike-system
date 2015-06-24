import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

public class UbikeSystem extends JPanel implements ActionListener
{
        private GUIResource gui;
        private KeyController keyController;
        private EastPanel eastPanel;
        private static ArrayList<User> userList;
	private static ArrayList<Station> stationList;
	private static Scanner scanner;
        public static int userNum;
        private static int mouseX;
        private static int mouseY;

        /**
         * JPanel constructor.
         * Read the ID file and station info file into arraylist,
         * and use GUIResource to construct label for each 
         * station. After constructor, print them on frame.
         */
        public UbikeSystem(EastPanel eastPanel) throws IOException
        {
                this.eastPanel = eastPanel;
                userNum = 0;

                File finID = new File("RFIDCard.txt");
                userList = inputUser(finID);

                File fin = new File("ubike.csv");
                stationList = inputStation(fin);

                setLayout(null);

                gui = new GUIResource(this, stationList, eastPanel);
                //setFocusable(true);
                //keyController = new KeyController();
                drawStation();

                this.addMouseListener(new MouseAdapter()
                {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                                mouseX = e.getX();
                                mouseY = e.getY();
                                System.out.println(mouseX + ", " + mouseY);
                                setMousePosition(mouseX, mouseY);
                        }
                });
        }

        void drawStation()
        {
                for (int i = 0; i < stationList.size(); ++i)
                {
                        add(gui.stationResource[i]);
                }
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
                repaint();
        }
        @Override
        public void paintComponent(Graphics g)
        {
                super.paintComponent(g);
                g.drawImage(gui.background, 0, 0, 
                        Constants.CENTER_WIDTH, Constants.HEIGHT, this);
        }
        public void setMousePosition(int x, int y)
        {

                eastPanel.setMousePosition(x, y);

                // Change back to 121.xxxx, 25.xxx
                double width  = 121.632369 - 121.474561;
                double height = 25.090168 - 25.001412;
                double widthScale  = Constants.CENTER_WIDTH/width;
                double heightScale = Constants.HEIGHT/height;
                //double newX = (x/widthScale) + 121.474561;
                //double newY = (Constants.HEIGHT - y)/heightScale + 25.001412;
                double newX = (x - 121.474561) * widthScale;
                double newY = Constants.HEIGHT - (y - 25.001412) * heightScale;
                //eastPanel.setMousePosition(newX, newY);
        }
        public static int getMouseX() 
        {
                return mouseX;
        }
        public static int getMouseY()
        {
                return mouseY;
        }
        /**
         * Not in use for now.
         */
        private class KeyController extends KeyAdapter
        {
                public KeyController()
                {
                        super();
                }
        }

	public static void main(String[] args) {
		ArrayList<Rental> rentalList = new ArrayList<Rental>();

		System.out.println("\nInput users... ");
		//ArrayList<User> userList = new ArrayList<User>();
		userList = new ArrayList<User>();
		try {
			File finID = new File("RFIDCard.txt");
			userList = inputUser(finID);
			System.out.println("Users input completed.\n");
		} catch (FileNotFoundException e) {
			System.out.println("ID file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}

		// System.out.println(userList.get(0));

		System.out.println("Input stations...");
		//ArrayList<Station> stationList = new ArrayList<>();
		stationList = new ArrayList<>();
		try {
			File fin = new File("ubike.csv");
			stationList = inputStation(fin);
			System.out.println("Stations input completed.\n");
		} catch (FileNotFoundException e) {
			System.out.println("ID file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}

		System.out.println("\nChoose an action: ");
		System.out.println("1. Rent bike");
		System.out.println("2. Return bike");
		System.out.println("3. User add money");
		System.out.println("4. Find nearest station");
		System.out.println("5. List station by bikes numbers\n");
		scanner = new Scanner(System.in);
		int action = scanner.nextInt();
		
		String strID,name;
		long id;
		String[] splitedLine;
		
		Station station = null;
		Iterator<Station> s_itr = stationList.iterator();
		User user = null;
		Iterator<User> u_itr = userList.iterator();
		
		switch(action){
			case 1:
				System.out.println("Rent bike: Please enter user ID and station name.");
				strID=scanner.next();
				splitedLine = strID.split("-");
				id = Long.parseLong(splitedLine[0], 16);
				name=scanner.next();
				while (s_itr.hasNext()) {
					Station s = s_itr.next();
					if (s.getName() == name) {
						station=s;
						break;
					}
				}				
				rentbike(id, station,userList,rentalList);
				
				break;
			case 2:
				System.out.println("Return bike: Please enter user ID and station name.");
				strID=scanner.next();
				splitedLine = strID.split("-");
				id = Long.parseLong(splitedLine[0], 16);
				name=scanner.next();
				while (s_itr.hasNext()) {
					Station s = s_itr.next();
					if (s.getName() == name) {
						station=s;
						break;
					}
				}
				while (u_itr.hasNext()) {
					User u = u_itr.next();
					if (u.getUserID() == id) {
						user=u;
						break;
					}
				}
				returnbike(user,station,rentalList);
				break;
			case 3:
				System.out.println("User add money: Please enter user ID and charge.");
				strID=scanner.next();
				splitedLine = strID.split("-");
				id = Long.parseLong(splitedLine[0], 16);
				int charge=scanner.nextInt();
				while (u_itr.hasNext()) {
					User u = u_itr.next();
					if (u.getUserID() == id) {
						user=u;
						break;
					}
				}

				System.out.printf("Value before charged = %d\n",user.getValue() );	
				user.setValue(user.getValue()+charge);
				System.out.printf("Value after charged = %d",user.getValue() );	
				break;
			case 4:
				System.out.println("Find nearest station:");
				
				break;
			case 5:
				System.out.println("List station by bikes numbers");
				
				break;

                        default:
                                System.out.println("Incorrect choice.");

                                break;
		}

	}
        public int getUserNum() 
        {
                return userNum;
        }
        /**
         * Create a new user.
         *
         * @return index, the new user's index.
         */
        public int createUser()
        {
                User newUser = new User(userNum, 0, "new user add by gui");
                userList.add(newUser);
                userNum += 1;
                return newUser.getIndex();
        }
        public User getUser(int index)
        {
                if (index > userList.size()) {
                        System.out.println("error: user index out of bound.");
                }
                return (User)userList.get(index);
        }


	public static ArrayList<User> inputUser(File userFin) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(userFin));
		ArrayList<User> userList = new ArrayList<User>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] splitedLine = line.split("-");
			long id = Long.parseLong(splitedLine[0], 16);
			User user = new User(userNum, id, line);
			userList.add(user);
                        userNum += 1;
		}
		br.close();
		return userList;
	}

	public static ArrayList<Station> inputStation(File stationFin)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(stationFin));
		ArrayList<Station> stationList = new ArrayList<Station>();
		String line = null;
		line = br.readLine(); // Skip first line.
		while ((line = br.readLine()) != null) {
			String[] splited = line.split(",");
			// index, name, x, y, capacity, available, address
			int index = Integer.parseInt(splited[0].substring(3));
			String name = splited[1].substring(1);
			double x = Double.parseDouble(splited[2].substring(1));
			double y = Double.parseDouble(splited[3].substring(1));
			int capacity = Integer.parseInt(splited[4].substring(1));
			int available = Integer.parseInt(splited[5].substring(1));
			String address = splited[6].substring(1);

			Station station = new Station(index, name, x, y, capacity,
					available, address);
			stationList.add(station);
		}
		br.close();
		return stationList;
	}

	public static void getStationInfo(Station station) {
		System.out.printf("站點名稱%s", station.getName());
		System.out.printf("可借車輛數%d", station.getAvailable());
		System.out.printf("空位數%d", station.getCapacity());
		System.out.printf("站點地址%s", station.getAddress());
		// System.out.printf("與該站點相近的%d個站點名稱 距離 可借車輛 空位數", n);
	}

	public static void getUserInfo(User user) {

		System.out.printf("餘額:%d\n", user.getValue());
		if (user.isused) {
			System.out.println("借車中");
		} else {
			System.out.println("非借車中");
		}
	}
        public int rentBike(int userID, Station station)
        {
                User user = userList.get(userID);
                if (user.getValue() < 5) {
                        return Constants.NOT_ENOUGH;
                }
                else if (user.isused) {
                        return Constants.IS_RENTING;
                }
                else if (station.getAvailable() == 0) {
                        return Constants.NO_BIKE;
                }
                else {
                        user.setRenttime(System.currentTimeMillis());
			user.isused = true;
			user.setRentstation(station);
			user.setTimes(user.getTimes() + 1);
			station.setCapacity(station.getCapacity()+1);
			station.setAvailable(station.getAvailable()-1);
			//rentalList.add(new Rental(user, station));
			System.out.printf("借成功,站點名稱:%s,剩餘車輛:%d,剩餘空位:%d", station.getName(),
					station.getAvailable(), station.getCapacity());
                        return Constants.RENT_SUCCESS;
		}                
        }


	public static void rentbike(long id, Station station,
			ArrayList<User> userList, ArrayList<Rental> rentalList) {
		User user = null;
		Iterator<User> itr = userList.iterator();
		boolean notfind = true;
		while (itr.hasNext()) {
			User u = itr.next();
			if (u.getUserID() == id) {
				notfind = false;
				user = u;
				break;
			}
		}

		if (notfind) {
			System.out.println("沒註冊");
		} else if (user.getValue() < 5) {
			System.out.println("餘額不足");
		} else if (user.isused == true) {
			System.out.println("正在借車中");
		} else if (station.getAvailable() == 0) {
			System.out.println("沒車");
		} else {

			user.setRenttime(System.currentTimeMillis());
			user.isused = true;
			user.setRentstation(station);
			user.setTimes(user.getTimes() + 1);
			station.setCapacity(station.getCapacity()+1);
			station.setAvailable(station.getAvailable()-1);
			rentalList.add(new Rental(user, station));
			System.out.printf("借成功,站點名稱:%s,剩餘車輛:%d,剩餘空位:%d", station.getName(),
					station.getAvailable(), station.getCapacity());
		}

	}

        public int returnBike(int userIndex, Station station)
        {
                User user = userList.get(userIndex);
                if (station.getCapacity() == 0) {
                        return Constants.NO_PLACE;
                }
                else if (!user.isused) {
                        return Constants.HASNT_RENTED;
                }
                else {
			int charge;
			user.setReturntime(System.currentTimeMillis());
                        user.setReturnStation(station);
			long deltatime = (System.currentTimeMillis() - user.getRenttime()) / 1000 / 60;
			user.setTotalTime(user.getTotalTime() + deltatime);
			if (deltatime < 30) {// 30分鐘內
				charge = 5;
			} else if (deltatime < 240) {// 4小時內
				if (deltatime % 30 == 0) {
					charge = (int) (10 * deltatime / 30);
				} else {
					charge = (int) (10 * deltatime / 30) + 1;
				}
			} else if (deltatime < 480) {// 8小時內
				if (deltatime % 30 == 0) {
					charge = (int) (20 * deltatime / 30);
				} else {
					charge = (int) (20 * deltatime / 30) + 1;
				}
			} else {// 逾8小時
				if (deltatime % 30 == 0) {
					charge = (int) (40 * deltatime / 30);
				} else {
					charge = (int) (40 * deltatime / 30) + 1;
				}
			}
			user.setValue(user.getValue() - charge);
			user.setTotalTime(user.getTotalTime()+deltatime);
			user.isused = false;
			station.setCapacity(station.getCapacity()-1);
			station.setAvailable(station.getAvailable()+1);
			//rentalList.add(new Rental(user, station));
			System.out.printf("還成功,扣款金額:%d,悠遊卡餘額:%d", charge, user.getValue());
                        return Constants.RETURN_SUCCESS;
                }
        }



	public static void returnbike(User user, Station station,
			ArrayList<Rental> rentalList) {

		if (station.getCapacity() == 0) {
			System.out.println("沒有空位");
		} else {
			// 扣款
			int charge;
			user.setReturntime(System.currentTimeMillis());
			long deltatime = (System.currentTimeMillis() - user.getRenttime()) / 1000 / 60;
			user.setTotalTime(user.getTotalTime() + deltatime);
			if (deltatime < 30) {// 30分鐘內
				charge = 5;
			} else if (deltatime < 240) {// 4小時內
				if (deltatime % 30 == 0) {
					charge = (int) (10 * deltatime / 30);
				} else {
					charge = (int) (10 * deltatime / 30) + 1;
				}
			} else if (deltatime < 480) {// 8小時內
				if (deltatime % 30 == 0) {
					charge = (int) (20 * deltatime / 30);
				} else {
					charge = (int) (20 * deltatime / 30) + 1;
				}
			} else {// 逾8小時
				if (deltatime % 30 == 0) {
					charge = (int) (40 * deltatime / 30);
				} else {
					charge = (int) (40 * deltatime / 30) + 1;
				}
			}
			user.setValue(user.getValue() - charge);
			user.setTotalTime(user.getTotalTime()+deltatime);
			user.isused = false;
			station.setCapacity(station.getCapacity()-1);
			station.setAvailable(station.getAvailable()+1);
			rentalList.add(new Rental(user, station));
			System.out.printf("還成功,扣款金額:%d,悠遊卡餘額:%d", charge, user.getValue());
		}
	}

	// 輸入使用者座標位置列出最近站點依序排列
	//public static void findNearest(ArrayList<Station> stationList, double x,
	public LinkedList<Station> findNearest(double x, double y) {
                LinkedList<Station> rank = new LinkedList<Station>();

                for (Station s : stationList) {
                        boolean added = false;
                        if (rank.size() == 0) {
                                rank.add(s);
                                added = true;
                        }

                        for (int index = 0; index < rank.size(); ++index) {
                                if (s.getDistance(x, y) < rank.get(index).getDistance(x, y)) {
                                        rank.add(index, s);
                                        added = true;
                                        break;
                                }
                        }
                        if (!added) {
                                rank.add(s);
                        }
                }
                return rank;
	}

	// 列出最多空位數的站點依序排列
	public static LinkedList<Station> orderBySpace() {
                LinkedList<Station> rank = new LinkedList<Station>();

                for (Station s : stationList) {
                        boolean added = false;
                        if (rank.size() == 0) {
                                rank.add(s);
                                added = true;
                        }

                        for (int index = 0; index < rank.size(); ++index) {
                                if (s.getCapacity() < rank.get(index).getCapacity()) {
                                        rank.add(index, s);
                                        added = true;
                                        break;
                                }
                        }
                        if (!added) {
                                rank.add(s);
                        }
                }
                return rank;
        }
	// 列出最多車輛數的站點依序排列
	public static LinkedList<Station> orderByBike() {
                LinkedList<Station> rank = new LinkedList<Station>();

                for (Station s : stationList) {
                        boolean added = false;
                        if (rank.size() == 0) {
                                rank.add(s);
                                added = true;
                        }

                        for (int index = 0; index < rank.size(); ++index) {
                                if (s.getAvailable() > rank.get(index).getAvailable()) {
                                        rank.add(index, s);
                                        added = true;
                                        break;
                                }
                        }
                        if (!added) {
                                rank.add(s);
                        }
                }
                return rank;
        }

	public static void getUsedUser(ArrayList<User> userList) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).isused == true)
				System.out.println(userList.get(i).getUserID());
		}
	}

	public static void getUnusedUser(ArrayList<User> userList) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).isused == false)
				System.out.println(userList.get(i).getUserID());
		}
	}

	public static void getTimeTop10User(ArrayList<User> userList) {
		ArrayList<User> rankingList = new ArrayList<User>(115);
		rankingList.add(userList.get(0));
		for (int i = 1; i < userList.size(); i++) {
			for (int j = 0; j < rankingList.size(); j++) {
				if (userList.get(i).getTotalTime() > rankingList.get(j)
						.getTotalTime()) {
					rankingList.add(j, userList.get(i));
					break;
				}
			}
			rankingList.add(userList.get(i));
		}
		for (int k = 0; k < 10; k++) {
			System.out.println(rankingList.get(k).getUserID() + " "
					+ rankingList.get(k).getTotalTime());
		}
	}

	public static void getTimesTop10User(ArrayList<User> userList) {
		ArrayList<User> rankingList = new ArrayList<User>(115);
		rankingList.add(userList.get(0));
		for (int i = 1; i < userList.size(); i++) {
			for (int j = 0; j < rankingList.size(); j++) {
				if (userList.get(i).getTimes() > rankingList.get(j).getTimes()) {
					rankingList.add(j, userList.get(i));
					break;
				}
			}
			rankingList.add(userList.get(i));
		}
		for (int k = 0; k < 10; k++) {
			System.out.println(rankingList.get(k).getUserID() + " "
					+ rankingList.get(k).getTimes());
		}
	}

}
