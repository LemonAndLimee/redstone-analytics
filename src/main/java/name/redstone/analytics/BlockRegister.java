package name.redstone.analytics;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlockRegister
{
    // Mapping of name -> block info wrapper
    private static final Map<String, BlockInfo> REGISTER = new HashMap<>();

    public static void addEntry(String name, BlockPos pos, BlockState state)
    {
        BlockInfo entry = new BlockInfo();
        entry.position = pos;
        entry.lastState = state;
        REGISTER.put(name, entry);
    }

    public static BlockInfo getEntry(String name)
    {
        return REGISTER.get(name);
    }
    public static boolean hasEntry(String name)
    {
        return REGISTER.containsKey(name);
    }
    public static void removeEntry(String name)
    {
        REGISTER.remove(name);
    }
    public static Set<String> getKeys()
    {
        return REGISTER.keySet();
    }

    public static void clearRegister()
    {
        REGISTER.clear();
    }
}
