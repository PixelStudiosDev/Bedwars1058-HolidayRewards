package me.leoo.bedwars.holidayrewards.events;

import lombok.Data;
import me.leoo.bedwars.holidayrewards.utils.Reward;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class RewardReceiveEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final Player player;
	private final Reward reward;
	private final int amount;
	private final String type;
	private final ItemStack item;
	private List<String> message;

	public RewardReceiveEvent(Player player, int amount, String type, Reward reward){
		this.reward = reward;
		this.player = player;
		this.amount = amount;
		this.type = type;

		reward.getItem().setAmount(amount);

		this.item = reward.getItem();
		switch (type){
			case "GENERATOR_COLLECT":
				this.message = reward.getMessageGen();
				break;
			case "KILL":
				this.message = reward.getMessageKill();
				break;
			case "FINAL_KILL":
				this.message = reward.getMessageFinalkill();
				break;
			case "BED_DESTROY":
				this.message = reward.getMessageBeddestroy();
				break;
		}
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
