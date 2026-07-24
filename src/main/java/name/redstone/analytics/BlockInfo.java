package name.redstone.analytics;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

// Wrapper class around an entry in the block register. Contains block position and its last recorded state.
public class BlockInfo
{
    public BlockPos position;
    public BlockState lastState;
}
