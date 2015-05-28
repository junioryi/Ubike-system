import java.io.*;
import java.util.ArrayList;

public class UbikeSystem {

	public static void main(String[] args){
		System.out.println("\nInput users... ");
		ArrayList<User> userList = new ArrayList<User>();
		try {
			File finID = new File ("RFIDCard.txt");
			userList = inputUser(finID);
			System.out.println("Users input completed.\n");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ID file not found.");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("IOException");
			e.printStackTrace();
		}
		
		//System.out.println(userList.get(0));

		System.out.println("Input stations...");
		ArrayList<Station> stationList = new ArrayList<>();
		try {
			File fin = new File ("ubike.csv");
			stationList = inputStation(fin);
			System.out.println("Stations input completed.\n");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ID file not found.");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("IOException");
			e.printStackTrace();
		}

		//System.out.println(stationList.get(0));

		System.out.println("\nChoose an action: ");
		System.out.println("1. Rent bike");
		System.out.println("2. Return bike");
		System.out.println("3. User add money");
		System.out.println("4. Find nearest station");
		System.out.println("5. List station by bikes numbers\n");
		// TODO: Parse the input and execute user's choice.

	}

	public static ArrayList<User> inputUser(File userFin) throws IOException
	{
		BufferedReader br = new BufferedReader (new FileReader(userFin));
		ArrayList userList = new ArrayList<User>();
		String line = null;
		while ((line = br.readLine()) != null)
		{
			String[] splitedLine = line.split("-");
			long id = Long.parseLong(splitedLine[0], 16);
			User user = new User(id);
			userList.add(user);
		}
		br.close();
		return userList;
	}
	public static ArrayList<Station> inputStation(File stationFin) throws IOException
	{
		BufferedReader br = new BufferedReader (new FileReader(stationFin));
		ArrayList stationList = new ArrayList<Station>();
		String line = null;
		line = br.readLine(); // Skip first line.
		while ((line = br.readLine()) != null)
		{
			String[] splited = line.split(",");
			// index, name, x, y, capacity, available, address
			int index = Integer.parseInt(splited[0].substring(3));
			String name = splited[1].substring(1);
			double x = Double.parseDouble(splited[2].substring(1));
			double y = Double.parseDouble(splited[3].substring(1));
			int capacity  = Integer.parseInt(splited[4].substring(1));
			int available = Integer.parseInt(splited[5].substring(1));
			String address = splited[6].substring(1);
			
			Station station = new Station(index, name, x, y, capacity, available, address);
			stationList.add(station);
		}
		br.close();
		return stationList;
	}
		
        public static void getStationInfo(Station station,int n){
                System.out.println("站點名稱");
                System.out.println("可借車輛數");
                System.out.println("空位數");
                System.out.println("站點地址");
                System.out.printf("與該站點相近的%d個站點名稱 距離 可借車輛 空位數",n);
        }
        
        
        public static void getUserInfo(User user){
                System.out.printf("餘額:%d\n",user.getValue());
                if(user.isused){
                        System.out.println("借車中");
                }else{
                        System.out.println("非借車中");
                }
        }
        
        public static void rentbike(User user,Station station){
                if (false){     //UserId沒註冊
                        System.out.println("沒註冊");
                }else if(user.getValue()<5){
                        System.out.println("餘額不足");
                }else if(user.isused==true){
                        System.out.println("正在借車中");
                }else if(false){//沒車
                        System.out.println("沒車");
                }else{

                        user.setRenttime(System.currentTimeMillis());
                        user.isused=true;
                        user.setRentstation(station);
                        System.out.println("借成功,站點ID,剩餘車輛,剩餘空位");
                }
                
        }
        
        public static void returnbike(User user,Station station){
                if(false){//沒有空位
                        System.out.println("沒有空位");
                }else{
                        //扣款
                        user.setReturntime(System.currentTimeMillis());
                        long deltatime=(System.currentTimeMillis()-user.getRenttime())/1000/60;
                        if (deltatime<30){//30分鐘內
                                user.setValue(user.getValue()-5);
                        }else if (deltatime<240){//4小時內
                                if (deltatime%30==0){
                                        user.setValue((int)(user.getValue()-10*deltatime/30));
                                }else{
                                        user.setValue((int)(user.getValue()-10*(deltatime/30+1)));
                                }
                        }else if (deltatime<480){//8小時內
                                if (deltatime%30==0){
                                        user.setValue((int)(user.getValue()-20*deltatime/30));
                                }else{
                                        user.setValue((int)(user.getValue()-20*(deltatime/30+1)));
                                }                       
                        }else{//逾8小時
                                if (deltatime%30==0){
                                        user.setValue((int)(user.getValue()-40*deltatime/30));
                                }else{
                                        user.setValue((int)(user.getValue()-40*(deltatime/30+1)));                              
                                }
                        }
                        user.isused=false;
                        System.out.println("還成功,扣款金額,悠遊卡餘額");                       
                }
        
        }       
        
        //算出最近的站點
        public static Station findNearest(Station station){
                
                return null;
        }

        //輸入使用者座標位置列出最近站點依序排列
        public static Station findNearest(double x,double y){
                
                return null;
        }       
        
        //列出最多空位數的站點依序排列
        public static void orderBySpace(){
                
        }
        
        //列出最多車輛數的站點依序排列
        public static void orderByBike(){
                
        }
                
        public static void getUsedUser(){
                
        }
        
        public static void getUnusedUser(){
                
        }
        
        public static void getTimeTop10User(){
                
        }
        
        public static void getTimesTop10User(){
                
        }
        


}
