package name.redstone.analytics;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class ListCommand
{
    public static int executeList(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        RedstoneAnalytics.LOGGER.info("Called /ra list.");

        source.sendSuccess(() -> Component.literal("---\nCurrently monitoring the following blocks:")
            .withStyle(ChatFormatting.WHITE), false
        );

        for (String name : BlockRegister.getKeys())
        {
            BlockInfo entry = BlockRegister.getEntry(name);

            BlockPos position = entry.position;
            String posString = position.toShortString();

            BlockState state = entry.lastState;
            String blockName = state.getBlock().getName().getString();

            source.sendSuccess(
                () -> Component.literal("\"" + name + "\" at (" + posString + "): " + blockName + ".")
                .withStyle(ChatFormatting.WHITE), false
            );
        }

        source.sendSuccess(() -> Component.literal("End of list.\n---")
            .withStyle(ChatFormatting.WHITE), false
        );

        return 1;
    }
}
