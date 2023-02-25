package me.bodiw.guardian.commands;

import me.bodiw.Embeds;
import me.bodiw.Utils;
import me.bodiw.guardian.database.Database;
import me.bodiw.guardian.database.Field;
import me.bodiw.guardian.database.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PlayerCommand extends Command {

    public PlayerCommand(SlashCommandInteractionEvent event) {
        super(event);
    }

    @Override
    public void execute() {
        String uuid = Utils.getMinecraftUUID(event.getOption("Player").getAsString(), true);
        User target = Database.getUser(Field.MINECRAFT_UUID, uuid);
        if (!target.equals(User.EMPTY)) {
            event.replyEmbeds(Embeds.profileEmbed(target)).setEphemeral(true).queue();
        } else {
            event.reply("Could not find user with Minecraft username " + event.getOption("Player").getAsString())
                    .setEphemeral(true).queue();
        }
    }

}
