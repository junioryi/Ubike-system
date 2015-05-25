import java.io.*;

public class ReadID 
{
    public static void main(String[] args)
    {
        try {
            File fin = new File ("RFIDCard.txt");
            readLine(fin);
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

    public static void readLine(File fin) throws IOException 
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

}

