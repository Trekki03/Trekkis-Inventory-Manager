package de.trekki03.inventorymanager.commands;


import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class MojangApi
{
    public String getUUID(String name)
    {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try
        {
            String UUIDJson = IOUtils.toString(new URL(url), "UTF-8");

            if (UUIDJson.isEmpty()) return "invalid name";

            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            String uuidWithoutDashes = UUIDObject.get("id").toString();

            StringBuffer sb = new StringBuffer(uuidWithoutDashes);
            sb.insert(8, "-");
            sb = new StringBuffer(sb.toString());
            sb.insert(13, "-");
            sb = new StringBuffer(sb.toString());
            sb.insert(18, "-");
            sb = new StringBuffer(sb.toString());
            sb.insert(23, "-");

            return sb.toString();
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }

        return "error";
    }
}
