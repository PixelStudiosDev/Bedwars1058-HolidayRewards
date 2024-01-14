package me.leoo.bedwars.holidayrewards.listeners;

import com.andrei1058.bedwars.BedWars;
import me.leoo.bedwars.holidayrewards.HolidayRewards;
import me.leoo.bedwars.holidayrewards.utils.Reward;
import me.leoo.bedwars.holidayrewards.utils.RewardsUtil;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemListeners implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		for(Reward re : RewardsUtil.rewardList){
			String n1 = BedWars.nms.getTag(e.getItemInHand(), "nbt1");
			if(n1 == null) return;
			if(n1.equals("hr_reward")){
				e.setCancelled(false);
				if(e.isCancelled()) return;
				RewardsUtil.playSound(Sound.EXPLODE.name(), e.getPlayer());
				PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, true, (float) e.getBlockPlaced().getLocation().getBlockX(), (float) e.getBlockPlaced().getLocation().getBlockY(), (float) e.getBlockPlaced().getLocation().getBlockZ(), (float) 0, (float) 0, (float) 0, (float) 0.1, 50);
				((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(particles);
				new BukkitRunnable(){
					@Override
					public void run() {
						e.getBlockPlaced().getLocation().getBlock().setType(Material.AIR);
					}
				}.runTaskLater(HolidayRewards.instance, 1);
			}
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		String n1 = BedWars.nms.getTag(e.getItemDrop().getItemStack(), "nbt1");
		if(n1 == null) return;
		if(n1.equals("hr_reward")){
			e.setCancelled(true);
		}
	}
}
