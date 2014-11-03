package info.inpureprojects.core.NEI.gtfoMicroblocks.Commands;

import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureTooltipConfig;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * Created by den on 11/3/2014.
 */
public class CommandReload implements ICommand {

    @Override
    public String getCommandName() {
        return "INpureCore";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return null;
    }

    @Override
    public List getCommandAliases() {
        return Arrays.asList(new String[]{"inpurecore"});
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
        if (Arrays.asList(p_71515_2_).contains("reload")) {
            NEIINpureConfig.startReloadProcess();
        } else if (Arrays.asList(p_71515_2_).contains("tooltips")) {
            if (NEIINpureTooltipConfig.tooltips_enabled) {
                INpureCore.proxy.sendMessageToPlayer("Registry tooltips are now hidden.");
                NEIINpureTooltipConfig.tooltips_enabled = false;
            } else {
                INpureCore.proxy.sendMessageToPlayer("Registry tooltips are now shown.");
                NEIINpureTooltipConfig.tooltips_enabled = true;
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return Arrays.asList(new String[]{"reload", "tooltips"});
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
