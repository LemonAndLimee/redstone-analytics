package name.redstone.analytics;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class ClearCommand
{
    public static int executeClear(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        RedstoneAnalytics.LOGGER.info("Called /ra clear.");

        BlockRegister.clearRegister();

        RedstoneAnalytics.LOGGER.info("Cleared block register.");
        source.sendSuccess(() -> Component.literal("Cleared block register.")
            .withStyle(ChatFormatting.WHITE), false
        );

        return 1;
    }
}
