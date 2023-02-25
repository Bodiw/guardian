package me.bodiw.guardian.commands;

import me.bodiw.Embeds;
import me.bodiw.Utils;
import me.bodiw.guardian.database.Database;
import me.bodiw.guardian.database.Field;
import me.bodiw.guardian.database.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WhitelistCommand extends Command {

    public WhitelistCommand(SlashCommandInteractionEvent event) {
        super(event);
    }

    @Override
    public void execute() {
        /**
         * Cases :
         * 1. User is not in the database
         * 2. User is in the database
         * 2.1. User is banned and must remain so
         * 2.2. User is not banned and is updating his uuid
         */

        String name = event.getOption("Minecraft username").getAsString();
        String uuid = Utils.getMinecraftUUID(name, true);

        Member sender = event.getMember();

        User player = Database.getUser(Field.MINECRAFT_UUID, uuid);
        User oldUser = Database.getUser(Field.DISCORD_ID, sender.getId());
        User newUser = new User(sender.getId(), uuid, false);

        if (player.banned()) {
            event.reply(name + " is banned from the whitelist.").setEphemeral(true).queue();
            return;
        }
        if (oldUser.banned()) {
            event.reply("You are banned from the whitelist.").setEphemeral(true).queue();
            return;
        }
        if (!newUser.discordID().equals(oldUser.discordID())) {
            event.reply(name + " doesnt belong to you").setEphemeral(true).queue();
            return;
        }
        if (oldUser.minecraftUUID().equals(uuid)) {
            event.reply("You are already whitelisted.").setEphemeral(true).queue();
            return;
        }
        if (!uuid.equals(oldUser.minecraftUUID())) { // Case where a user is updating his uuid
            Database.setWhitelisted(oldUser, false);
            Database.addUser(newUser);
            event.replyEmbeds(Embeds.profileEmbed(newUser)).setEphemeral(true).queue();
        }
        if (oldUser.equals(User.EMPTY)) {
            Database.addUser(newUser);
            event.replyEmbeds(Embeds.profileEmbed(newUser)).setEphemeral(true).queue();
            return;
        }

    }
}
