package me.bodiw.guardian;

import java.util.Collections;

import me.bodiw.guardian.commands.BanCommand;
import me.bodiw.guardian.commands.PlayerCommand;
import me.bodiw.guardian.commands.ProfileCommand;
import me.bodiw.guardian.commands.UnbanCommand;
import me.bodiw.guardian.commands.UserCommand;
import me.bodiw.guardian.commands.WhitelistCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Guardian extends ListenerAdapter {

        static final String COMMAND_PLAYER_DESCRIPTION = "The player's Minecraft username or UUID";

        public static void main(String[] args) {
                String token = System.getenv("GUARDIAN_TOKEN");

                JDA jda = JDABuilder
                                .createLight(token, Collections.emptyList())
                                .addEventListeners(new Guardian())
                                .setActivity(Activity.playing("Type \"/whitelist name\" to whitelist yourself!"))
                                .build();

                jda.updateCommands().addCommands(
                                Commands.slash("profile", "Check your current whitelist status"),
                                Commands.slash("whitelist", "Add yourself to the whitelist")
                                                .addOption(OptionType.STRING, "Minecraft username",
                                                                "Your Minecraft username"),
                                Commands.slash("user", "Check a user's whitelist status")
                                                .addOption(OptionType.USER, "User", "The user to check")
                                                .setDefaultPermissions(DefaultMemberPermissions
                                                                .enabledFor(Permission.KICK_MEMBERS)),
                                Commands.slash("player", "a player's whitelist information")
                                                .addOption(OptionType.STRING, "Player", COMMAND_PLAYER_DESCRIPTION)
                                                .setDefaultPermissions(DefaultMemberPermissions
                                                                .enabledFor(Permission.KICK_MEMBERS)),
                                Commands.slash("ban", "ban a player from the whitelist")
                                                .addOption(OptionType.STRING, "Player", COMMAND_PLAYER_DESCRIPTION)
                                                .setDefaultPermissions(DefaultMemberPermissions
                                                                .enabledFor(Permission.KICK_MEMBERS)),
                                Commands.slash("unban", "unban a player from the whitelist")
                                                .addOption(OptionType.STRING, "Player", COMMAND_PLAYER_DESCRIPTION)
                                                .setDefaultPermissions(DefaultMemberPermissions
                                                                .enabledFor(Permission.KICK_MEMBERS)))
                                .queue();

        }

        public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
                switch (event.getName()) {
                        case "profile" -> new ProfileCommand(event).execute();
                        case "whitelist" -> new WhitelistCommand(event).execute();
                        case "user" -> new UserCommand(event).execute();
                        case "player" -> new PlayerCommand(event).execute();
                        case "ban" -> new BanCommand(event).execute();
                        case "unban" -> new UnbanCommand(event).execute();
                }
        }
}
