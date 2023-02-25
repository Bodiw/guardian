package me.bodiw.guardian.commands;

import me.bodiw.Embeds;
import me.bodiw.guardian.database.Database;
import me.bodiw.guardian.database.Field;
import me.bodiw.guardian.database.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class UserCommand extends Command {

    public UserCommand(SlashCommandInteractionEvent event) {
        super(event);
    }

    @Override
    public void execute() {
        String discordId = event.getOption("User").getAsUser().getId();
        User target = Database.getUser(Field.DISCORD_ID, discordId);
        if (!target.equals(User.EMPTY)) {
            event.replyEmbeds(Embeds.profileEmbed(target)).setEphemeral(true).queue();
        } else {
            event.reply("Could not find user with Discord ID " + discordId)
                    .setEphemeral(true).queue();
        }
    }
}
