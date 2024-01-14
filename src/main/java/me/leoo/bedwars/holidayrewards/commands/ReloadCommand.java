package me.leoo.bedwars.holidayrewards.commands;

import me.leoo.bedwars.holidayrewards.HolidayRewards;
import me.leoo.bedwars.holidayrewards.configuration.Config;
import me.leoo.bedwars.holidayrewards.utils.RewardsUtil;
import me.leoo.utils.bukkit.commands.Command;
import me.leoo.utils.bukkit.commands.CommandBuilder;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("holidayrewards");

        setMainCommand(new CommandBuilder("holidayrewards")
                .setAliases("hr")
                .setExecutor(((sender, strings) -> {
                    HolidayRewards.get().getMainConfig().reload();

                    RewardsUtil.rewardList.clear();
                    RewardsUtil.initializeRewards();
                })));
    }

    @Override
    public String getNoPermissionMessage() {
        return null;
    }
}
