package me.leoo.bedwars.holidayrewards.configuration;

import me.leoo.bedwars.holidayrewards.HolidayRewards;
import me.leoo.utils.bukkit.config.ConfigManager;
import me.leoo.utils.bukkit.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Config extends ConfigManager {

    public Config() {
        super("config", "plugins/BedWars1058/Addons/HolidayRewards");

        YamlConfiguration yml = getYml();

        yml.options().header(HolidayRewards.get().getDescription().getName() + " v" + HolidayRewards.get().getDescription().getVersion() + " made by " + HolidayRewards.get().getDescription().getAuthors() + ".\n" +
                "Dependencies: " + HolidayRewards.get().getDescription().getDepend() + ".\n" +
                "SoftDependencies: " + HolidayRewards.get().getDescription().getSoftDepend() + ".\n" +
                "Read the wiki for more info: https://wiki.pixelstudios.dev/guilds\n" +
                "Join my discord for support: https://pixelstudios.dev/discord\n"
        );

        //rewards
        addRewardItem("halloween", "Pumpkinator's Pumpkin", "01-10", "31-10",
                Arrays.asList("GENERATOR_COLLECT:DIAMOND-EMERALD", "KILL", "FINAL_KILL", "BED_DESTROY"), 50, 1,
                Collections.singletonList("&aYou brought &6{amount} &apumpkins back to the shopkeeper!"),
                Collections.singletonList("&6+{amount} pumpkins from generator"), Collections.singletonList("&6+{amount} pumpkins from kill"),
                Collections.singletonList("&6+{amount} pumpkins from final kill"), Collections.singletonList("&6+{amount} pumpkins from bed destoy"),
                new ItemBuilder(Material.SKULL_ITEM).setData(3)
                        .setToSaveString("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNhZDc5MjA3YzNmOGJiZjRiMWQ1MzJjZmU4NmRjY2I1N2QyYzk3YWVlYjUxZWYwMGE2NjBhZmRjZWFiNWZhOSJ9fX0=")
                        .setName("&6Pumpkinator's Pumpkin")
                        .setLore("&7Trade it at any shopkeeper to", "&7claim for the Pumpkinator Quest!"));

        addRewardItem("christmas", "Christmas Gift", "01-12", "31-12",
                Arrays.asList("GENERATOR_COLLECT:DIAMOND-EMERALD", "KILL", "FINAL_KILL", "BED_DESTROY"), 50, 1,
                Collections.singletonList("&aYou brought &c{amount} &agifts back to the shopkeeper!"), Collections.singletonList("&c+{amount} gifts from generator"),
                Collections.singletonList("&c+{amount} gifts from kill"), Collections.singletonList("&c+{amount} gifts from final kill"),
                Collections.singletonList("&c+{amount} gifts from bed destoy"),
                new ItemBuilder(Material.SKULL_ITEM)
                        .setData(3)
                        .setToSaveString("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBhY2IyMmRmNTBlN2Y5MDdjNTI0NWU1YTcyMTBmYzk4MDIwMjQxNjAxMmY2Y2I3OWExOTZlYjAzMWVlZGE3NSJ9fX0=")
                        .setName("&cChristmas's Gift")
                        .setLore("&7Trade it at any shopkeeper to", "&7claim for the Christmas Quest!"));

        yml.options().copyDefaults(true);
        save();
    }

    private void addRewardItem(String sectionName, String displayName, String periodStart, String periodEnd, List<String> gen_type, int chance, int amount, List<String> messageBrought, List<String> messageGen, List<String> messageKill, List<String> messageFinalKill, List<String> messageBeddestroy, ItemBuilder item) {
        YamlConfiguration yml = getYml();
        String path = "holiday_rewards.reward." + sectionName;

        yml.addDefault(path + ".display_name", displayName);
        yml.addDefault(path + ".period_start", periodStart);
        yml.addDefault(path + ".period_end", periodEnd);
        yml.addDefault(path + ".generation_type", gen_type);
        yml.addDefault(path + ".generation_chance", chance);
        yml.addDefault(path + ".generation_amount", amount);
        yml.addDefault(path + ".messageBrought", messageBrought);
        yml.addDefault(path + ".commandOnBrought", "give {player} diamond {rewardsAmount}");
        yml.addDefault(path + ".messageGen", messageGen);
        yml.addDefault(path + ".messageKill", messageKill);
        yml.addDefault(path + ".messageFinalKill", messageFinalKill);
        yml.addDefault(path + ".messageBedDestroy", messageBeddestroy);

        item.saveIntoConfig(path + ".item", this);
    }

}
