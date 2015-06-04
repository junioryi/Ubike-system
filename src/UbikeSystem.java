import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class UbikeSystem {

	private static Scanner scanner;

	public static void main(String[] args) {
		ArrayList<Rental> rentalList = new ArrayList<Rental>();

		System.out.println("\nInput users... ");
		ArrayList<User> userList = new ArrayList<User>();
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
		ArrayList<Station> stationList = new ArrayList<>();
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
				System.out.println("List station by bikes numbers:");
				
				break;
		}

	}

	public static ArrayList<User> inputUser(File userFin) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(userFin));
		ArrayList<User> userList = new ArrayList<User>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] splitedLine = line.split("-");
			long id = Long.parseLong(splitedLine[0], 16);
			User user = new User(id, line);
			userList.add(user);
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
			rentalList.add(new Rental(user, station));
			System.out.printf("借成功,站點名稱:%s,剩餘車輛:%d,剩餘空位:%d", station.getName(),
					station.getAvailable(), station.getCapacity());
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
			user.isused = false;
			rentalList.add(new Rental(user, station));
			System.out.printf("還成功,扣款金額:%d,悠遊卡餘額:%d", charge, user.getValue());
		}
	}

	// 輸入使用者座標位置列出最近站點依序排列
	public static void findNearest(ArrayList<Station> stationList, double x,
			double y) {
		ArrayList<Station> rankingList = new ArrayList<Station>(310);
		rankingList.add(stationList.get(0));
		for (int i = 1; i < stationList.size(); i++) {
			for (int j = 0; j < rankingList.size(); j++) {
				if (stationList.get(i).getDistance(x, y) < rankingList.get(j)
						.getDistance(x, y)) {
					rankingList.add(j, stationList.get(i));
					break;
				}
			}
			rankingList.add(stationList.get(i));
		}
		for (int k = 0; k < rankingList.size(); k++) {
			System.out.println(rankingList.get(k).getDistance(x, y) + " "
					+ rankingList.get(k).getName());
		}
	}

	// 列出最多空位數的站點依序排列
	public static void orderBySpace(ArrayList<Station> stationList) {
		ArrayList<Station> rankingList = new ArrayList<Station>(310);
		rankingList.add(stationList.get(0));
		for (int i = 1; i < stationList.size(); i++) {
			for (int j = 0; j < rankingList.size(); j++) {
				if (stationList.get(i).getCapacity() > rankingList.get(j)
						.getCapacity()) {
					rankingList.add(j, stationList.get(i));
					break;
				}
			}
			rankingList.add(stationList.get(i));
		}
		for (int k = 0; k < rankingList.size(); k++) {
			System.out.println(rankingList.get(k).getCapacity() + " "
					+ rankingList.get(k).getName());
		}
	}

	// 列出最多車輛數的站點依序排列
	public static void orderByBike(ArrayList<Station> stationList) {
		ArrayList<Station> rankingList = new ArrayList<Station>(310);
		rankingList.add(stationList.get(0));
		for (int i = 1; i < stationList.size(); i++) {
			for (int j = 0; j < rankingList.size(); j++) {
				if (stationList.get(i).getAvailable() > rankingList.get(j)
						.getAvailable()) {
					rankingList.add(j, stationList.get(i));
					break;
				}
			}
			rankingList.add(stationList.get(i));
		}
		for (int k = 0; k < rankingList.size(); k++) {
			System.out.println(rankingList.get(k).getAvailable() + " "
					+ rankingList.get(k).getName());
		}

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
