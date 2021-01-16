package de.trekki03.trekkisinventorymanager.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * This class specifies functions, which handle MojangAPI related request
 * @author Trekki03
 * @since 1.0
 */
public class MojangApi {

    /**
     * This gives back a player uuid or an error
     * @param name user name of the player the uuid should come from 
     * @return returns the uuid without hyphens or an error ("error" - if something went completely wrong or "invalid name" if the user does not exist in the mojang api)
     * @since 1.0
     */
    public static String getUUID(String name) 
    {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        String json = getJsonStringFromURL(url);

        //if reading from url went wrong, returning error
        if(json.equals("error") || json.equals("invalid name"))
        {
            return json;
        }

        //extract UUID and add hyphen
        String idWithoutHyphen = getID(json);
        String idWithHyphen = setMinus(idWithoutHyphen, 8);
        idWithHyphen = setMinus(idWithHyphen, 13);
        idWithHyphen = setMinus(idWithHyphen, 18);

        return setMinus(idWithHyphen, 23);

    }

    //reads the content of a webAPI with HTTP - "GET"
    private static String getJsonStringFromURL(String targetUrl) {
        try 
        {
            //connecting to URl
            URL url = new URL(targetUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            //Reading content into one string
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String inputBuffer = "";

            while( (inputLine = inputReader.readLine()) != null)
            {
                inputBuffer += inputLine; 
            }

            inputReader.close();
            
            //validate api input and the return input or error
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

    //extracts the id from a json
    private static String getID(String jsonString)
    {
        int startOfID = jsonString.indexOf("id") + 5;
        int endOfID = jsonString.indexOf("\"", startOfID);
        return jsonString.substring(startOfID,endOfID);
    }

    //add a minus to a specific point in a string
    private static String setMinus(String inputString, int position)
    {
        String buffer = inputString.substring(0, position);
        buffer += "-";
        return buffer + inputString.substring(position, inputString.length());
    }
}
