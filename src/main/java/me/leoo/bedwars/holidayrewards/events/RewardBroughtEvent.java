package me.leoo.bedwars.holidayrewards.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import me.leoo.bedwars.holidayrewards.utils.Reward;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class RewardBroughtEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final Player player;
	private int amount;
	private Reward reward;

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
