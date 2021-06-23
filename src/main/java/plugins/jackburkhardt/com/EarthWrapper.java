package plugins.jackburkhardt.com;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public final class EarthWrapper extends JavaPlugin {

    public static int BORDER_BUFFER, XMAX, ZMAX, XMIN, ZMIN;
    private static EarthWrapper instance;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    instance = this;
    saveDefaultConfig();

    BORDER_BUFFER = getConfig().getInt("buffer");
    XMAX = getConfig().getInt("xmax");
    XMIN = getConfig().getInt("xmin");
    ZMAX = getConfig().getInt("zmax");
    ZMIN = getConfig().getInt("zmin");

    new BukkitRunnable() {

        public void run() {
            if (Bukkit.getOnlinePlayers().size() == 0) { return; }

            for (Player p : Bukkit.getOnlinePlayers()) {
                double pX = p.getLocation().getX();
                double pZ = p.getLocation().getZ();

                if (pX >= XMAX || pX <= XMIN || pZ >= ZMAX || pZ <= ZMIN) {
                    wrapPlayer(p, pX, pZ);
                }
            }

        }
    }.runTaskTimer(this, 100L, 100L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public EarthWrapper getInstance(){
        return instance;
    }

    private static void wrapPlayer(Player p, double pX, double pZ) {
        double pY = p.getLocation().getY();
        double newX = pX;
        double newZ = pZ;

        if (pX >= XMAX) {
            newX = XMIN + BORDER_BUFFER;
        }
        if (pX <= XMIN) {
            newX = XMAX - BORDER_BUFFER;
        }
        if (pZ >= ZMAX) {
            newZ = ZMIN + BORDER_BUFFER;
        }
        if (pZ <= ZMIN) {
            newZ = ZMAX - BORDER_BUFFER;
        }

        p.teleport(new Location(p.getWorld(), newX, pY + 90, newZ, p.getLocation().getYaw(), p.getLocation().getPitch()));
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 3, true, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 120, true, false));
        p.sendMessage(ChatColor.GREEN + "You've been sent to the other side of the world (because the earth is round, after all).");

    }
}
