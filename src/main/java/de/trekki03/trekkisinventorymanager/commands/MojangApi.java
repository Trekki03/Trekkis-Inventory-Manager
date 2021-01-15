package de.trekki03.trekkisinventorymanager.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import de.trekki03.trekkisinventorymanager.Main;
import org.bukkit.ChatColor;

public class MojangApi {

    public static String getUUID(String name) 
    {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        String json = getJsonStringFromURL(url);

        Main.plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JSON]: " + json);

        if(json.equals("error") || json.equals("invalid name"))
        {
            return json;
        }

        String idWithoutHyphen = getID(json);
        String idWithHyphen = setMinus(idWithoutHyphen, 8);
        idWithHyphen = setMinus(idWithHyphen, 13);
        idWithHyphen = setMinus(idWithHyphen, 18);
        return setMinus(idWithHyphen, 23);

    }

    private static String getJsonStringFromURL(String targetUrl) {
        try 
        {
            URL url = new URL(targetUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String inputBuffer = "";

            while( (inputLine = inputReader.readLine()) != null)
            {
                inputBuffer += inputLine; 
            }

            inputReader.close();

            if(inputBuffer.contains("error") || inputBuffer == null || inputBuffer.equals(""))
            {
                return "invalid name";
            }
            else
            {
                return inputBuffer;
            }
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
            return "error";
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return "error";
        }
        
    }

    private static String getID(String jsonString)
    {
        int startOfID = jsonString.indexOf("id") + 5;
        int endOfID = jsonString.indexOf("\"", startOfID);
        System.out.println("Beginn: " + startOfID + " End: " + endOfID);
        return jsonString.substring(startOfID,endOfID);
    }

    private static String setMinus(String inputString, int position)
    {
        String buffer = inputString.substring(0, position);
        buffer += "-";
        return buffer + inputString.substring(position, inputString.length());
    }
}
