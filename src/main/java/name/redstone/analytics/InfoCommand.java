package name.redstone.analytics;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class InfoCommand
{
    public static int executeInfo(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        String name = StringArgumentType.getString(ctx, "name");

        String logMsg = "Called /ra info " + name + ".";
        RedstoneAnalytics.LOGGER.info(logMsg);

        if (!BlockRegister.hasEntry(name))
        {
            logMsg = "No entry for name \"" + name + "\".";
            RedstoneAnalytics.LOGGER.error(logMsg);

            source.sendSuccess(() -> Component.literal("No entry for name \"" + name + "\".")
                .withStyle(ChatFormatting.RED), false
            );
            return 0;
        }

        BlockInfo entry = BlockRegister.getEntry(name);
        BlockPos position = entry.position;
        String posString = position.toShortString();

        logMsg = "Queried block \"" + name + "\" at (" + posString + ").";
        RedstoneAnalytics.LOGGER.info(logMsg);
        source.sendSuccess(() -> Component.literal("\"" + name + "\" at (" + posString + ").")
            .withStyle(ChatFormatting.WHITE), false
        );

        return 1;
    }
}
