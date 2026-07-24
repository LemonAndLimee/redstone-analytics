package name.redstone.analytics;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;

import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

public class RedstoneAnalytics implements ModInitializer {
    public static final String MOD_ID = "redstone-analytics";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");

        CommandRegistrationCallback.EVENT.register(
            (
                (dispatcher, registryAccess, environment) ->
                {
                    helloWorldCommand(dispatcher);
                }
            )
        );
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private static void helloWorldCommand(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(
            Commands.literal("hello_world")
                .requires(source -> source.hasPermission(2))
                .executes(RedstoneAnalytics::executeHelloWorld)
        );
    }

    private static int executeHelloWorld(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException
    {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayerOrException();

        source.sendSuccess(
            () -> Component.literal("Hello world!").withStyle(ChatFormatting.GREEN),
            false
        );

        return 1;
    }
}
