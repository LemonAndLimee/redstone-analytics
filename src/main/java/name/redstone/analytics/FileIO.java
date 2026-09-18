package name.redstone.analytics;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileIO
{
    public static void writeLine(String line)
    {
        Path minecraftDir = FabricLoader.getInstance().getGameDir();
        Path logPath = minecraftDir.resolve("RedstoneAnalytics").resolve("latest.txt");

        try
        {
            Files.createDirectories(logPath.getParent());
            Files.writeString(
                logPath,
                line + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void createFile()
    {
        Path minecraftDir = FabricLoader.getInstance().getGameDir();
        Path logPath = minecraftDir.resolve("RedstoneAnalytics").resolve("latest.txt");

        String headerLine = "---\nRedstone tick, name, status:\n---";

        try
        {
            Files.createDirectories(logPath.getParent());
            Files.writeString(
                logPath,
                headerLine + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
