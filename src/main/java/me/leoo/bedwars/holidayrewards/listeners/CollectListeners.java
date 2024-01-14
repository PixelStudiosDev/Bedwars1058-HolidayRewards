package me.leoo.bedwars.holidayrewards.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerGeneratorCollectEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import me.leoo.bedwars.holidayrewards.HolidayRewards;
import me.leoo.bedwars.holidayrewards.events.RewardReceiveEvent;
import me.leoo.bedwars.holidayrewards.utils.Reward;
import me.leoo.bedwars.holidayrewards.utils.RewardGenerator;
import me.leoo.bedwars.holidayrewards.utils.RewardsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectListeners implements Listener {

    @EventHandler
    public void onArenaStart(GameStateChangeEvent event) {
        if (event.getNewState().equals(GameState.playing)) {
            IArena arena = event.getArena();
            Location location;

            for (String type : Arrays.asList("Diamond", "Emerald")) {
                if (arena.getConfig().getYml().get("generator." + type) != null) {
                    for (String s : arena.getConfig().getYml().getStringList("generator." + type)) {
                        location = arena.getConfig().convertStringToArenaLocation(s);
                        if (location == null) {
                            HolidayRewards.get().getLogger().severe("Invalid location for " + type + " generator: " + s);
                            continue;
                        }
                        arena.getOreGenerators().add(new RewardGenerator(location, arena, null, GeneratorType.valueOf(type.toUpperCase())));
                    }
                }
            }

            for (ITeam team : arena.getTeams()) {
                List<Location> locs = new ArrayList<>();

                Object o = arena.getConfig().getYml().get("Team." + team.getName() + ".Gold");
                if (o instanceof String) {
                    locs.add(arena.getConfig().getArenaLoc("Team." + team.getName() + ".Gold"));
                } else {
                    locs = arena.getConfig().getArenaLocations("Team." + team.getName() + ".Gold");
                }

                locs.forEach(location1 -> team.getGenerators().add(new RewardGenerator(location1, arena, team, GeneratorType.CUSTOM)));

            }
        }
    }

    @EventHandler
    public void onHeadReceive(RewardReceiveEvent event) {
        if (event.getPlayer() == null) return;

        event.getPlayer().getInventory().addItem(event.getItem());

        for (String s : event.getMessage()) {
            s = s.replace("{amount}", String.valueOf(event.getAmount()));
            event.getPlayer().sendMessage(s);
        }
    }

    @EventHandler
    public void onGeneratorCollect(PlayerGeneratorCollectEvent event) {
        if (event.getPlayer() == null) return;
        String n1 = HolidayRewards.get().getBedWars().getVersionSupport().getTag(event.getItemStack(), "nbt1");
        if (n1 == null) return;
        if (n1.equals("hr_reward")) {
            for (Reward re : RewardsUtil.getRewardList()) {
                event.setCancelled(true);
                event.getItem().remove();
                int chance = RewardsUtil.randomChance();
                if (chance <= re.getGenerationChance()) {
                    Bukkit.getServer().getPluginManager().callEvent(new RewardReceiveEvent(event.getPlayer(), event.getItemStack().getAmount(), "GENERATOR_COLLECT", re));
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerKillEvent event) {
        if (event.getKiller() == null) return;
        for (Reward reward : RewardsUtil.getRewardList()) {
            for (String sre : reward.getGenerationType()) {
                if (event.getCause().isFinalKill()) {
                    if (sre.contains("FINAL_KILL")) {
                        int chance = RewardsUtil.randomChance();
                        if (chance <= reward.getGenerationChance()) {
                            Bukkit.getServer().getPluginManager().callEvent(new RewardReceiveEvent(event.getKiller(), 1, "FINAL_KILL", reward));
                        }
                    }
                } else {
                    if (sre.contains("KILL")) {
                        int chance = RewardsUtil.randomChance();
                        if (chance <= reward.getGenerationChance()) {
                            Bukkit.getServer().getPluginManager().callEvent(new RewardReceiveEvent(event.getKiller(), 1, "KILL", reward));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBedDestoy(PlayerBedBreakEvent e) {
        if (e.getPlayer() == null) return;
        for (Reward re : RewardsUtil.getRewardList()) {
            for (String sre : re.getGenerationType()) {
                if (sre.contains("BED_DESTROY")) {
                    int chance = RewardsUtil.randomChance();
                    if (chance <= re.getGenerationChance()) {
                        Bukkit.getServer().getPluginManager().callEvent(new RewardReceiveEvent(e.getPlayer(), 1, "BED_DESTROY", re));
                    }
                }
            }
        }
    }

}
