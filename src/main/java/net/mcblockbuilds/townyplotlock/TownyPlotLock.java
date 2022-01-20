package net.mcblockbuilds.townyplotlock;

import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class TownyPlotLock extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register events and commands
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.PLOT, "lock", new lockCmd());
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.PLOT, "unlock", new unlockCmd());
        this.getServer().getPluginManager().registerEvents(new listener(this), this);

    }
}
