package de.trekki03.trekkisinventorymanager.utility;

import de.trekki03.trekkisinventorymanager.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Language
{
    YamlConfiguration langConfig;
    public Language()
    {
        File langFile = new File(Main.plugin.getDataFolder(), "lang.yml");
        langConfig = YamlConfiguration.loadConfiguration(langFile);

        if(!langFile.exists())
        {
            try
            {
                langFile.getParentFile().mkdirs();
                langFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        //ENGLISH:
        //Console Messages
        langConfig.addDefault("en_en.consoleMessage.load", "--Trekkis Inventory Manager-- was loaded");
        langConfig.addDefault("en_en.consoleMessage.unload", "Trekkis Inventory Manager-- was unloaded");
        langConfig.addDefault("en_en.consoleMessage.onlyPlayer", "This command can't be executed by a console");

        //Chat Messages
        langConfig.addDefault("en_en.chatMessage.error", "An Error occurred");

        //Specific Chat Messages
        //COMMAND_LoadInventory
        langConfig.addDefault("en_en.chatMessage.loadInventory.noPlayer", "The player doesn't exists");
        langConfig.addDefault("en_en.chatMessage.loadInventory.noPlayerData", "No saved data for this player exists");
        langConfig.addDefault("en_en.chatMessage.loadInventory.inventoryReplaced", "Your inventory was replaced");

        //COMMAND_SaveInventory
        langConfig.addDefault("en_en.chatMessage.saveInventory.noPlayer", "No online player with this name");
        langConfig.addDefault("en_en.chatMessage.saveInventory.inventorySaved", "Inventory data saved");

        //COMMAND:SeeInventory
        langConfig.addDefault("en_en.chatMessage.seeInventory.noPlayer", "The targeted player isn't online");

        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //GERMAN
        //Console Messages
        langConfig.addDefault("de_de.consoleMessage.load", "--Trekkis Inventory Manager-- wurde geladen");
        langConfig.addDefault("de_de.consoleMessage.unload", "Trekkis Inventory Manager-- wurde entladen");
        langConfig.addDefault("de_de.consoleMessage.onlyPlayer", "Dieser Befehl kann nicht von einer Konsole ausgführt werden");

        //Chat Messages
        langConfig.addDefault("de_de.chatMessage.error", "Es ist ein Fehler aufgetreten");

        //Specific Chat Messages
        //COMMAND_LoadInventory
        langConfig.addDefault("de_de.chatMessage.loadInventory.noPlayer", "Dieser Spieler existiert nicht");
        langConfig.addDefault("de_de.chatMessage.loadInventory.noPlayerData", "Es existieren keine gespeicherten Daten für diesen Spieler");
        langConfig.addDefault("de_de.chatMessage.loadInventory.inventoryReplaced", "Dein Inventar wurde ersetzt");

        //COMMAND_SaveInventory
        langConfig.addDefault("de_de.chatMessage.saveInventory.noPlayer", "Es ist kein Spieler mit diesem Namen online");
        langConfig.addDefault("de_de.chatMessage.saveInventory.inventorySaved", "Inventar Daten gespeichert");

        //COMMAND:SeeInventory
        langConfig.addDefault("de_de.chatMessage.seeInventory.noPlayer", "Der Spieler ist nicht online");

        langConfig.options().copyDefaults(true);
        try
        {
            langConfig.save(langFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getText(String path)
    {
        String language = "en_en";

        String output = langConfig.getString( language + "." + path);
        if(output == null)
            return "An error occurred while reading the lang.yml. Please report this in the comments on the Spigot page of this plugin.";
        return output;
    }

    public String getText(String path, Player sender)
    {
        String language = sender.getLocale();
        if(!language.equals("en_en") && !language.equals("de_de"))
        {
            language = "en_en";
        }
        String output = langConfig.getString( language + "." + path);
        if(output == null)
            return "An error occurred while reading the lang.yml. Please report this in the comments on the Spigot page of this plugin.";
        return output;
    }

}
