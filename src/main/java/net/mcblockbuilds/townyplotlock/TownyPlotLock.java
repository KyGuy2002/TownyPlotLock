package net.mcblockbuilds.townyplotlock;

import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class TownyPlotLock extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.PLOT, "lock", new lockCmd());
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.PLOT, "unlock", new unlockCmd());
        this.getServer().getPluginManager().registerEvents(new listener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
