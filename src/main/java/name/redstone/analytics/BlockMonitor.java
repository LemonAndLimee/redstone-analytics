package name.redstone.analytics;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BlockMonitor
{
    private static boolean isRecording = false;
    private static int currentTick = -1;

    public static void startRecording() {isRecording = true; }
    public static void stopRecording()
    {
        isRecording = false;
        currentTick = -1;
    }

    public static void checkBlocks(Level level)
    {
        if (isRecording && level != null)
        {
            MinecraftServer server = level.getServer();

            for (String name : BlockRegister.getKeys())
            {
                BlockInfo entry = BlockRegister.getEntry(name);
                BlockPos position = entry.position;
                BlockState lastState = entry.lastState;

                BlockState currentState = level.getBlockState(position);

                if (currentState != lastState)
                {
                    if (currentTick < 0) { currentTick = 0; }

                    if (currentState.hasProperty(BlockStateProperties.POWER))
                    {
                        int lastPower = lastState.getValue(BlockStateProperties.POWER);
                        int currentPower = currentState.getValue(BlockStateProperties.POWER);

                        if (currentPower != lastPower)
                        {
                            String logLine = currentTick + ", " + name + ", " + currentPower;
                            FileIO.writeLine(logLine);
                            server.getPlayerList().broadcastSystemMessage(Component.literal(logLine), false);

                            RedstoneAnalytics.LOGGER.info("Block \"" + name + "\" at (" + position.toShortString() + ") changed power from " + lastPower + " to " + currentPower);
                        }
                    }
                    else if (currentState.hasProperty(BlockStateProperties.POWERED))
                    {
                        boolean wasPowered = lastState.getValue(BlockStateProperties.POWERED);
                        boolean isPowered = currentState.getValue(BlockStateProperties.POWERED);

                        if (isPowered != wasPowered)
                        {
                            String logLine = currentTick + ", " + name + ", " + isPowered;
                            FileIO.writeLine(logLine);
                            server.getPlayerList().broadcastSystemMessage(Component.literal(logLine), false);

                            RedstoneAnalytics.LOGGER.info("Block \"" + name + "\"at (" + position.toShortString() + ") changed power from " + wasPowered + " to " + isPowered);
                        }
                    }
                    else if (currentState.hasProperty(BlockStateProperties.LIT))
                    {
                        boolean wasLit = lastState.getValue(BlockStateProperties.LIT);
                        boolean isLit = currentState.getValue(BlockStateProperties.LIT);

                        if (isLit != wasLit)
                        {
                            String logLine = currentTick + ", " + name + ", " + isLit;
                            FileIO.writeLine(logLine);
                            server.getPlayerList().broadcastSystemMessage(Component.literal(logLine), false);

                            RedstoneAnalytics.LOGGER.info("Block \"" + name + "\"at (" + position.toShortString() + ") changed power from " + wasLit + " to " + isLit);
                        }
                    }
                    else
                    {
                        String logLine = "Unknown change in block \"" + name + "\" at (" + position.toShortString() + "). RA only records redstone activity.";
                        FileIO.writeLine(logLine);
                        server.getPlayerList().broadcastSystemMessage(Component.literal(logLine).withStyle(ChatFormatting.RED), false);

                        // Unexpected other change in block - this mod is only for measuring redstone signals.
                        RedstoneAnalytics.LOGGER.warn(logLine);
                    }

                    // Update entry
                    BlockRegister.updateEntry(name, currentState);
                }
            }
        }

        if (currentTick >= 0) { currentTick += 1; }
    }
}
