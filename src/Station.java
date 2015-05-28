public class Station
{
    private int idx;
    private String name;
    private double locationX;
    private double locationY;
    private int capacity;
    private int available;
    private String address;

    public Station(int idx, String name, double x, double y, int capacity, int available, String address)
    {
        this.idx = idx;
        this.name = name;
        this.locationX = x;
        this.locationY = y;
        this.capacity = capacity;
        this.available = available;
        this.address = address;
    }

    public int getIndex()
    {
        return idx;
    }
    public String getName()
    {
        return name;
    }
    public double[] getLocation()
    {
        return new double[] {locationX, locationY};
    }
    public int getCapacity()
    {
        return capacity;
    }
    public int getAvailable()
    {
        return available;
    }
    public String getAddress()
    {
        return address;
    }   
    public String toString()
    {
        String s = new String();
        // TODO: show more detail.
        return name;
    }
    

}









