package me.leoo.bedwars.holidayrewards.utils;

import lombok.Getter;
import me.leoo.bedwars.holidayrewards.HolidayRewards;
import me.leoo.utils.bukkit.config.ConfigManager;
import me.leoo.utils.bukkit.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Reward {

    private final String name;
    private final String displayName;
    private final String periodStart;
    private final String periodEnd;
    private final List<String> generationType;
    private final int generationChance;
    private final int generationAmount;
    private final List<String> messageBrought;
    private final String commandOnBrought;
    private final List<String> messageGen;
    private final List<String> messageKill;
    private final List<String> messageFinalkill;
    private final List<String> messageBedDestroy;
    private final ItemStack item;

    private static final ConfigManager config = HolidayRewards.get().getMainConfig();

    public Reward(String reward) {
        String path = "holiday_rewards.reward." + reward + ".";

        this.name = reward;
        this.displayName = config.getString(path + "display_name");
        this.periodStart = config.getString(path + "period_start");
        this.periodEnd = config.getString(path + "period_end");
        this.generationType = config.getList(path + "generation_type");
        this.generationChance = config.getInt(path + "generation_chance");
        this.generationAmount = config.getInt(path + "generation_amount");
        this.messageBrought = config.getList(path + "messageBrought");
        this.commandOnBrought = config.getString(path + "commandOnBrought");
        this.messageGen = config.getList(path + "messageGen");
        this.messageKill = config.getList(path + "messageKill");
        this.messageFinalkill = config.getList(path + "messageFinalKill");
        this.messageBedDestroy = config.getList(path + "messageBedDestroy");
        this.item = ItemBuilder.parseFromConfig(path + "item", config).get();  }
}
