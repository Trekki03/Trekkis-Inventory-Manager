### The Plugin:

The plugin contains inventory management for server administrators.
The plugin is designed and testes in version 1.12 to 1.16.4

### Commands:

#### /saveinv [Online Player]
- Permission: tim.save - default: op
- Saves the content of the players inventory in a .yml file
#### /loadinv [saved player] [replace inventory (true/false)]
- Permission: tim.load - default: op
- Loads the inventory data of a saved file. You can choose if your inventory gets replaced or the loaded data is shown in a chest like inventory. With no arguments you invenory didn't get replaced.
- Attention, this command uses the MojangAPI. If you use it more than 600 times in 10 minutes, your ip can get banned from the MojangAPI. This has an impact on all plugins using the Mojang API. (No safty feature implemented yet)
#### /seeinv <Online Player> [interact true/false]
- Permission: tim.see - default: op
- Let you see and interact with the targets inventory. You can choose if items get removed of the targets inventory. With no Argument you only see the inventory.
#### /seeend <Online Player> [interact true/false]
- Permission: tim.end - default: op
- Let you see and interact with the targets enderchest. You can choose if items get removed of the targets enderchest. With no Argument you only see the enderchest.

### Permissions:

All permissions have the prefix tim (**T**rekkis **I**nventory **M**anager).
The All-In permission is tim.*
You find all the permissions and there default configuration above.

### Language:

The plugin supports english and german.
The chat messages are automatically in the language of the commmand senders minecraft client.
If your language isn't supported it gets automatically changed to english.
Console messages are automatically in english.

### Stats:

The plugin collects anonymous data with bstats.
(https://bstats.org/getting-started)
It is possible to turn it off in the bstats config. But I ask you to leave it on, because the data (most notably the Java and Minecraft Version) helps me a lot and is totaly anonymous.
And if you want, you can also see this data.
https://bstats.org/plugin/bukkit/TrekkisInventoryManager/6317
bStats is used by the most plugins (e.g. World Edit, World Guard...).
