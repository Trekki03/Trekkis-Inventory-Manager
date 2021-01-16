package de.trekki03.trekkisinventorymanager.utility;

import de.trekki03.trekkisinventorymanager.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;

/**
 * This class handles all language related things.
 * <p>
 * It specifies functions for handling the lang.yml
 * @since A0.4
 * @author Trekki03
 */
public class Language
{
    YamlConfiguration langConfig;

    /**
     * loads/creates the lang.yml 
     */
    public Language()
    {
        File langFile = new File(Main.plugin.getDataFolder(), "lang.yml");
        langConfig = YamlConfiguration.loadConfiguration(langFile);

        //If lang-file does not exist, create a new one
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
        langConfig.addDefault("en_en.consoleMessage.bStatsLoaded", "bStats successfully started");
        langConfig.addDefault("en_en.consoleMessage.bStatsNotLoaded", "bStats could not be started");

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
        langConfig.addDefault("de_de.consoleMessage.bStatsLoaded", "bStats wurde erfolgreich geladen");
        langConfig.addDefault("de_de.consoleMessage.bStatsNotLoaded", "bStats konnte nicht geladen werden");

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

    /**
     * This function return a string from the lang.yml
     * @param path name of the string in the lang.yml
     * @return returns the string specified by the path or an error message
     * @since A0.4
     */
    public String getText(String path)
    {
        String language = "en_en";

        //return string from lang.yml in en_en
        String output = langConfig.getString( language + "." + path);
        if(output == null)
        {
            return "An error occurred while reading the lang.yml. If this is the first server start with the plugin, please restart. Elsewise please report this in the comments on the Spigot or Github page of this plugin.";
        }
        
        return output;
    }

    /**
     * This function return a string from the lang.yml in language of the sender
     * @param path name of the string in the lang.yml
     * @param sender sender of the commend the string replies to. (For the language selection)
     * @return returns the string specified by the path or an error message
     * @since A0.4
     */
    public String getText(String path, Player sender)
    {
        //get language of player (en_en or de_de elsewise en_en)
        String language = sender.getLocale();

        if(!language.equals("en_en") && !language.equals("de_de"))
        {
            language = "en_en";
        }

        //return string from lang.yml in players language
        String output = langConfig.getString( language + "." + path);
        if(output == null)
        {
            return "An error occurred while reading the lang.yml. If this is the first server start with the plugin, please restart. Elsewise please report this in the comments on the Spigot or Github page of this plugin.";
        }

        return output;
    }

    /**
     * returns the console prefix. 
     * @return console prefix
     * @since 1.0
     */
    public String getConsolePrefix()
    {
        return ChatColor.GRAY + "[TrekkisInventoryManager] ";
    }
}
