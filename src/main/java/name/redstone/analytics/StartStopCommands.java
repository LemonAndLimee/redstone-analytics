package name.redstone.analytics;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class StartStopCommands
{
    public static int executeStart(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        FileIO.createFile();
        BlockMonitor.startRecording();
        source.sendSuccess(() -> Component.literal("---\nStarted recording...\n---").withStyle(ChatFormatting.WHITE), false);
        return 1;
    }
    public static int executeStop(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        BlockMonitor.stopRecording();
        source.sendSuccess(() -> Component.literal("---\nStopped recording.\n---").withStyle(ChatFormatting.WHITE), false);
        return 1;
    }
}
