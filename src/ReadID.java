import java.io.*;

public class ReadID 
{
    public static void main(String[] args)
    {
        Console console = System.console();
        // Ask user to input the ID they want to check.
        String input = console.readLine("Enter input: ");
        boolean hasID;
        try {
            File finID = new File ("RFIDCard.txt");
            File finCSV = new File ("ubike.csv");

            // Check if finID has input.
            hasID = findID(finID, input);
            System.out.println("" + hasID);

            //print the first element of each line of csv file.
            //readCSVFirst(finCSV);
            
            readID(finID);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /** 
     * Print out first hex number in File fin in decimal format.
     *
     * @param fin input file.
     */
    public static void readID(File fin) throws IOException 
    {
        BufferedReader br = new BufferedReader (new FileReader(fin));

        String line = null;
        while ((line = br.readLine()) != null) {
            String[] eachLine = line.split("-");            
            System.out.println(line);
            System.out.println(Long.parseLong(eachLine[0], 16));
        }
        br.close();
    }
    /** 
     * Print out first element in a csv File.
     *
     * @param fin input file.
     */
    public static void readCSVFirst(File fin) throws IOException 
    {
        BufferedReader br = new BufferedReader (new FileReader(fin));

        String line = null;
        while ((line = br.readLine()) != null) {
            String[] eachLine = line.split(",");            
            System.out.println(eachLine[0]);
        }
        br.close();
    }

    /**
     * Check if input string is in the File fin.
     *
     * @param fin input file.
     * @param input input string.
     * @return boolean, whether the input is in the file.
     */
    public static boolean findID(File fin, String input) throws IOException
    {
        BufferedReader br = new BufferedReader (new FileReader(fin));
        String line = null;
        boolean hasID = false;
        while ((line = br.readLine()) != null)
        {
            if (line.equals(input))
            {
                hasID = true;
            }
        }

        br.close();
        return hasID;
    }
}

