package me.leoo.bedwars.holidayrewards.listeners;

import com.andrei1058.bedwars.api.language.Language;
import me.leoo.bedwars.holidayrewards.events.RewardBroughtEvent;
import me.leoo.bedwars.holidayrewards.events.RewardReceiveEvent;
import me.leoo.bedwars.holidayrewards.utils.Reward;
import me.leoo.bedwars.holidayrewards.utils.RewardsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ShopListeners implements Listener {

	public static HashMap<String, Integer> q = new HashMap<>();

	@EventHandler
	public void onShopOpen(InventoryOpenEvent e){
		Player p = (Player) e.getPlayer();
		if(e.getInventory().getName().equals(Language.getMsg(p, "shop-items-messages.inventory-name"))){
			for(Reward re : RewardsUtil.rewardList){
				if(q.get(re.getItemName()) == null){
					q.put(re.getItemName(), 0);
				}
				for(ItemStack i : p.getInventory().getContents()){
					if(i != null){
						if(i.getType().equals(re.getItem().getType()) && i.getItemMeta().getDisplayName().equals(re.getItemName())){
							q.put(re.getItemName(), q.get(re.getItemName()) + i.getAmount());
							p.getInventory().removeItem(i);
							Bukkit.getServer().getPluginManager().callEvent(new RewardBroughtEvent(p, q.get(re.getItemName()), re));
							q.clear();
						}
					}
				}

			}
		}
	}

	@EventHandler
	public void onHeadBrought(RewardBroughtEvent e){
		for(String brm : e.getReward().getMessageBrought()){
			e.getPlayer().sendMessage(brm.replace("{amount}", String.valueOf(e.getAmount())));
			if(!e.getReward().getCommandOnBrought().equals("")){
				String c = e.getReward().getCommandOnBrought();
				if(c.contains("{player}")) c = c.replace("{player}", e.getPlayer().getName());
				if(c.contains("{rewardsAmount}")) c = c.replace("{rewardsAmount}", String.valueOf(e.getAmount()));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
			}
		}
	}
}
