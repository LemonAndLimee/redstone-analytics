package name.redstone.analytics;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(
            Commands.literal("ra")
                .then(
                    Commands.literal("set")
                    .then(Commands.argument("name", StringArgumentType.word()).executes(
                        SetUnsetCommands::executeSet
                    ))
                )
                .then(
                    Commands.literal("info")
                    .then(Commands.argument("name", StringArgumentType.word()).executes(
                        InfoCommand::executeInfo
                    ))
                )
                .then(
                    Commands.literal("list").executes(ListCommand::executeList)
                )
                .then(
                    Commands.literal("unset")
                    .then(Commands.argument("name", StringArgumentType.word()).executes(
                        SetUnsetCommands::executeUnset
                    ))
                )
                .then(
                    Commands.literal("clear").executes(ClearCommand::executeClear)
                )
                .then(
                    Commands.literal("start").executes(StartStopCommands::executeStart)
                )
                .then(
                    Commands.literal("stop").executes(StartStopCommands::executeStop)
                )
        );
    }
}
