import javax.sound.sampled.Line;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * a class for create map
 * this class create map from  map file
 * <p>
 * pattern used: singleton
 *
 * @author Mohammad mighani
 */
public class MapMaker implements Serializable{
    private ArrayList<String> strings;
    private static String filePath = "files\\mapText11.txt";
    private int fieldOfView = 7;
    private static MapMaker mapMaker = null;
    private int locxShow ;
    private int locyShow ;
    private int locxShowF ;
    private int locyShowF;
    private int lastPositionX;
    private int lastPositionY;
    private int remaindX;
    private int remaindY;
    private int lastPositionXF;
    private int lastPositionYF;
    private int remaindXF;
    private int remaindYF;
    private int sizeY;
    private int sizeX;
    private int changex ;
    private int changey ;

    /**
     * private cunstructor (singleton)
     */
    private MapMaker()
    {
        strings = getMapFromFile();
        sizeY = strings.size() * 100;
        sizeX = strings.get(0).length() * 100;
        lastPositionX = 640;
        lastPositionY = 360;
        locxShow = 0;
        locyShow = 0;
    }


    /**
     * return single object of class
     *
     * @return
     */
    public static MapMaker getMapMaker()
    {

        if (mapMaker == null)
            mapMaker = new MapMaker();
        return mapMaker;
    }
    public void resetMap()
    {
        mapMaker=new MapMaker();
    }

    /**
     * a method for extract map from file
     *
     * @return
     */
    private ArrayList<String> getMapFromFile()
    {
        if(StartFrames.isMap()){
            filePath = "files/mapText.txt";
        }else {
            String path = filePath;
            path = path.substring(0, path.indexOf(".") - 2);
            filePath = path + Informations.getInfo().level + Informations.getInfo().difficultyLevel + ".txt";
        }
        File file = new File(filePath);
        if(!file.exists()) {
            JOptionPane.showMessageDialog(null, "There is not another file for map of game!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(filePath));

            while (reader.ready())
            {
                String line = reader.readLine();
                strings.add(line);
            }
        } catch (IOException ex)
        {
            System.err.println(ex);
        } finally
        {
            try
            {
                if (reader != null)
                    reader.close();
            } catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
        return strings;
    }

    /**
     * a method for return a special part of map
//     *
     * @param locX
     * @param locY
     * @return
     */
    public Vector<String> getSpecialPartMap(int locX, int locY)
    {

        if (locX > 640 && locX < sizeX - 640)
            changex = locX - lastPositionX;
        else
            changex = 0;
        if (locY > 360 && locY < sizeY - 360)
            changey = locY - lastPositionY;
        else
            changey = 0;

        locyShow += changey;
        locxShow += changex;

        locxShow = Math.max(locxShow, 0);
        locxShow = Math.min(locxShow, sizeX -    1350 );
        locyShow = Math.max(locyShow, 0);
        locyShow = Math.min(locyShow, sizeY - 720);


        remaindX = locxShow % 100;
        remaindY = locyShow % 100;

        Vector<String> special = new Vector<>(0);

        for (int i = 0; i < fieldOfView + 1; i++)
        {
            special.add(strings.get(locyShow / 100 + i).substring(locxShow / 100, locxShow / 100 + fieldOfView*2));
        }

        lastPositionX = locX;
        lastPositionY = locY;


        return special;
    }
    public int getRemaindX()
    {
        return remaindX;
    }

    public int getRemaindY()
    {
        return remaindY;
    }


    public  int getLocxShow()
    {
        return locxShow;
    }

    public  int getLocyShow()
    {
        return locyShow;
    }

    /**
     * a metjod for remove a part of string
     * @param x
     * @param y
     * @param replacement
     */
    public void changeChar(int x, int y, char replacement)
    {
        char[] newString = strings.get(y / 100).toCharArray();
        if (replacement == 'D')
        {
            char[] replaceString = new char[newString.length - 1];
            for (int i = 0; i < x / 100; i++)
            {
                replaceString[i] = newString[i];
            }
            for (int i = x / 100; i < newString.length - 1; i++)
            {
                replaceString[i] = newString[i + 1];
            }
            strings.set(y / 100, String.valueOf(replaceString));
        } else
        {
            newString[x / 100] = replacement;
            strings.set(y / 100, String.valueOf(newString));
        }
    }

    public static void setMapMaker(MapMaker mapMaker1) {
        mapMaker = mapMaker1;
    }

    public ArrayList<String> getStrings()
    {
        return strings;
    }

    public int getSizeY()
    {
        return sizeY;
    }


    public int getSizeX()
    {
        return sizeX;
    }

    public void setFilePath(String filePath) {
        MapMaker.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}

