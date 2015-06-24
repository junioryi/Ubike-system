import java.util.Date;
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
    	private double totalTime;	//unit:min
	private int times;
        private int charge;

        private Date rentDate;
        private Date returnDate;


	public User(int index, long userID,String strID)
	{
                this.index  = index;
		this.userID = userID;
		this.strID  = strID;
		this.value  = 50;
                this.renttime   = 0;
                this.returntime = 0;
                this.totalTime  = 0;
                this.charge     = 0;
                this.rentstation = null;
                this.returnStation = null;
	}

        public void setRentDate(Date date) {
                this.rentDate = date;
        }
        public Date getRentDate() {
                return rentDate;
        }
        public void setReturnDate(Date date) {
                this.returnDate = date;
        }
        public Date getReturnDate() {
                return returnDate;
        }
        public void setCharge(int charge) {
                this.charge = charge;
        }
        public int getCharge() {
                return charge;
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

        public void deposit(int money) {
                this.value += money;
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

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public int getTimes(){
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}


	
}
