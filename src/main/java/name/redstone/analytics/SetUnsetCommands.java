package name.redstone.analytics;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public class SetUnsetCommands
{
    public static int executeSet(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException
    {
        CommandSourceStack source = ctx.getSource();
        String name = StringArgumentType.getString(ctx, "name");

        String logMsg = "Called /ra set " + name;
        RedstoneAnalytics.LOGGER.info(logMsg);

        ServerPlayer player = source.getPlayerOrException();

        BlockPos targetedBlockPos = PlayerRaycastHelper.getTargetedBlock(player);
        if (targetedBlockPos == null)
        {
            RedstoneAnalytics.LOGGER.warn("Player not looking at block.");
            source.sendSuccess(() -> Component.literal("Must be looking at a block.").withStyle(ChatFormatting.RED), false);
            return 0;
        }

        BlockState targetedBlockState = player.level().getBlockState(targetedBlockPos);
        String blockName = targetedBlockState.getBlock().getName().getString();
        String posString = targetedBlockPos.toShortString();

        logMsg = "Adding entry \"" + name + "\": [" + blockName + "] at (" + posString + ").";
        RedstoneAnalytics.LOGGER.info(logMsg);
        source.sendSuccess(() -> Component.literal("Watching \"" + name + "\" at (" + posString + ").")
            .withStyle(ChatFormatting.WHITE), false
        );

        BlockRegister.addEntry(name, targetedBlockPos, targetedBlockState);

        return 1;
    }

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
