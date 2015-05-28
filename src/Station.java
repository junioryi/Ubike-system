public class Station
{
    private int idx;
    private String name;
    private float locationX;
    private float locationY;
    private int availableNum;
    private int emptyNum;
    private String address;

    public Station(int idx, String name, float x, float y, int available, int empty, String address)
    {
        this.idx = idx;
        this.name = name;
        this.locationX = x;
        this.locationY = y;
        this.availableNum = available;
        this.emptyNum = empty;
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
    public float[] getLocation()
    {
        return new float[] {locationX, locationY};
    }
    public int getAvailable()
    {
        return availableNum;
    }
    public int getEmpty()
    {
        return emptyNum;
    }
    public String getAddress()
    {
        return address;
    }   

}









