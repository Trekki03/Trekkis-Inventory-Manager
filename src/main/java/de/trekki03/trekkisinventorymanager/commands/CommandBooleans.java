package de.trekki03.trekkisinventorymanager.commands;

import de.trekki03.trekkisinventorymanager.utility.Language;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class contains functions, which handle boolean quests for commands (e.g. if the sender is a player)
 * @author Trekki03
 * @since A-0.3
 */
public class CommandBooleans
{

    /**
     * Checks if a commend sender is console and sends an error message to the sender if wanted.
     * @param sender sender of the commend
     * @param hasToBePlayer specifies if the player has to be a player (true) or a console (false) - if true, sends an error message to the sender if is console 
     * @param lang language of the command sender
     * @return returns if player is console
     */
    public static boolean isConsole(CommandSender sender, boolean hasToBePlayer, Language lang)
    {

        if (sender instanceof Player)
        {
            return false;
        }

        if (hasToBePlayer)
        {
            sender.sendMessage(lang.getText("consoleMessage.onlyPlayer"));
        }

        return true;
    }
}
