package me.leoo.bedwars.holidayrewards.utils;

import lombok.Getter;
import me.leoo.bedwars.holidayrewards.HolidayRewards;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RewardsUtil {

    @Getter
    private static final List<Reward> rewardList = new ArrayList<>();

    private static final ConfigManager config = HolidayRewards.get().getMainConfig();

    public static void initializeRewards() {
        for (String reward : config.getSection("holiday_rewards.reward")) {
            String s1 = config.getString("holiday_rewards.reward." + reward + ".period_start");
            String s2 = config.getString("holiday_rewards.reward." + reward + ".period_end");

            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            if (month >= Integer.parseInt(s1.split("-")[1]) && day >= Integer.parseInt(s1.split("-")[0]) && month <= Integer.parseInt(s2.split("-")[1]) && day <= Integer.parseInt(s2.split("-")[0])) {
                rewardList.add(new Reward(reward));

                System.out.println("[Bedwars1058-HolidayRewards] Loaded " + reward);
            }
        }
    }

    public static int randomChance() {
        return new Random().nextInt(101);
    }

    public static void playSound(String name, Player player) {
        player.playSound(player.getLocation(), Sound.valueOf(name), 1, 1);
    }

}
