package name.redstone.analytics;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class UnsetCommand
{
    public static int executeUnset(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        String name = StringArgumentType.getString(ctx, "name");

        String logMsg = "Called /ra unset " + name + ".";
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

        BlockRegister.removeEntry(name);

        logMsg = "Removed block \"" + name + "\" from register.";
        RedstoneAnalytics.LOGGER.info(logMsg);
        source.sendSuccess(() -> Component.literal("Removed block \"" + name + "\" from register.")
            .withStyle(ChatFormatting.WHITE), false
        );

        return 1;
    }
}
