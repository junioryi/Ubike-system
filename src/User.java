
public class User {
        private int index;
	private long userID;
	private String strID;
	private int value = 50;
	private long renttime;
	private Station rentstation;
        private Station returnStation;
	private long returntime;
	public boolean isused=false;
    	private long totalTime;	//unit:min
	private int times;


	public User(int index, long userID,String strID)
	{
                this.index  = index;
		this.userID = userID;
		this.strID  = strID;
		this.value  = 50;
                this.renttime   = 0;
                this.returntime = 0;
                this.totalTime  = 0;
                this.rentstation = null;
                this.returnStation = null;
	}

        public int getIndex() {
                return index;
        }

	public long getUserID() {
		return userID;
	}
	
	public String getStrID() {
		return strID;
	}
	
        public boolean getStatus() {
                return isused;
        }

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getRenttime() {
		return renttime;
	}

	public void setRenttime(long renttime) {
		this.renttime = renttime;
	}

	public long getReturntime() {
		return returntime;
	}

	public void setReturntime(long returntime) {
		this.returntime = returntime;
	}

	public Station getRentStation() {
		return rentstation;
	}

	public void setRentstation(Station rentstation) {
		this.rentstation = rentstation;
	}

        public Station getReturnStation() {
                return returnStation;
        }

        public void setReturnStation(Station station) {
                this.returnStation = station;
        }

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public int getTimes(){
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}


	
}
