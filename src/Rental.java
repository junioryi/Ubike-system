import java.util.*;
import java.text.*;


public class Rental{
	private String userID;
	private String stationID;
	private long charge;
	private Mode mode;
	private Date rentalTime;
	private Date returningTime;
	
	public Rental(String userID, String stationID, Mode mode, String time, ArrayList<Rental> history){
		this.userID = userID;
		this.stationID = stationID;
		this.mode = mode;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (mode == Mode.RENT){
			try{
				rentalTime = ft.parse(time);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			returningTime = null;
			charge = 0;
		}
		else{
			try{
				returningTime = ft.parse(time);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			rentalTime = null;
			for(int i = history.size()-1 ; i >= 0 ; i--){
				if (userID == history.get(i).userID){
					charge = countCharge(history.get(i).rentalTime);
					break;
				}
			}
		}
	}
	
	public long countCharge(Date lastRentalTime){
		long minute = ( returningTime.getTime() - lastRentalTime.getTime() ) / (1000*60);
		if (minute<30)
			return 5;
		else if (minute<240)
			return 5 + (minute-30)/30*10;
		else if (minute<480)
			return 75 + (minute-240)/30*20;
		else 
			return 235 + (minute-480)/30*40;
	}
	public String toString(){
		return userID + " " + (mode==Mode.RENT?"RENT  ":"RETURN") + " @ "+ stationID +
				" @ "+ (rentalTime==null?"":rentalTime) + (returningTime==null?"":returningTime) +
				" -$" + charge;
	}
	public static void showAllHistory(ArrayList<Rental> history){
		for(int i = history.size()-1 ; i >= 0 ; i--){
			System.out.println( history.get(i) );
		}
	}
	public static void showUserHistory(String userID, ArrayList<Rental> history){
		for(int i = history.size()-1 ; i >= 0 ; i--){
			if ( userID == history.get(i).userID )
				System.out.println( history.get(i) );
		}
	}
}
