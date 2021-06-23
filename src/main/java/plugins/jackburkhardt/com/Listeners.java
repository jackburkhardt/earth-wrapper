package plugins.jackburkhardt.com;

import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Listeners implements Listener {

    @EventHandler
    public static void onExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper) {
            e.blockList().clear();
        }
    }

}
