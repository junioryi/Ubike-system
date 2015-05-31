import java.util.*;

public class Rental{
	private long userID;
	private int stationID;
	private Mode mode;
	private Date rentTime;
	private Date returnTime;
	
	public Rental(User user, Station, station){
		userID = user.getUserID();
		stationID = stationID.getIndex();
		if (user.isused == True){
			mode = Mode.RENT;
			rentTime = new Date(user.getRenttime());
			returnTime = null;
		}
		// SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		else{
			mode = Mode.RETURN;
			returnTime = new Date(user.getReturntime());
			rentTime = null;
		}
	}
	
	public String toString(){
		if (mode == Mode.RENT)
			return userID + " " + "RENT   @ " + stationID + " @ " + rentTime;
		else
			return userID + " " + "RETURN @ " + stationID + " @ " + returnTime;
	}
	
	public long getUserID{
		return userID;
	}
	public int getStationID{
		return stationID;
	}
	public Mode getMode{
		return mode;
	}
	public Date getRentTime{
		return rentTime;
	}
	private Date getReturnTime{
		return returnTime;
	}

	// public static void showAllHistory(ArrayList<Rental> rentalList){
	// 	for(int i = rentalList.size()-1 ; i >= 0 ; i--){
	// 		System.out.println(rentalList.get(i));
	// 	}
	// }
	// public static void showUserHistory(String userID, ArrayList<Rental> rentalList){
	// 	for(int i = rentalList.size()-1 ; i >= 0 ; i--){
	// 		if ( userID == rentalList.get(i).userID )
	// 			System.out.println(rentalList.get(i));
	// 	}
	// }
}
