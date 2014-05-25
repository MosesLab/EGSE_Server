/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mosesserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Matthew Handley
 */
public class DataLogger {
    
    Calendar cal;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
    SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
    SimpleDateFormat millisecondFormat = new SimpleDateFormat("SSS");
    String path;
    
    public DataLogger(String in_path)
    {
        path = in_path;   
        
        if (!path.endsWith("\\"))
        {
            path += "\\";
        }
    }
    
    public void Write(String data, String extension)
    {
        try
        {
            Calendar cal = Calendar.getInstance();
            
            String tempPath = updatePath(extension);
            File filePath = new File(tempPath.substring(0, tempPath.lastIndexOf("\\" ) +1));
            File file = new File(tempPath);
            if(!filePath.isDirectory())
            {
                filePath.mkdirs();
            }
            
            if(!file.exists())
            {
                file.createNewFile();
            }
            
            String maskedData = "";
            for(int i = 0; i < data.length(); i++)
            {
                maskedData += (char)(data.charAt(i) & 0x7F);
            }

            FileWriter writer = new FileWriter(file, true);
            
            writer.write(timeFormat.format(cal.getTime()) + " ");
            writer.write(data + "    " + maskedData + "\n");
            
            writer.close();
            
        }
        catch(Exception ex)
        {
            System.out.println("Exception in DataLogger:\n" + ex.toString());
        }
        
    }
    
    private String updatePath(String extension)
    {
        Calendar cal = Calendar.getInstance();
        
        String tempPath = path;
        
        tempPath += yearFormat.format(cal.getTime());
        tempPath += "_";
        tempPath += monthFormat.format(cal.getTime());
        tempPath += "_";
        tempPath += dayFormat.format(cal.getTime());
        tempPath += "_";
        tempPath += extension;
        
        return tempPath;
    }
    
}