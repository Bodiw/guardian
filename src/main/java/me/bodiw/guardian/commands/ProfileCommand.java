package me.bodiw.guardian.commands;

import me.bodiw.Embeds;
import me.bodiw.guardian.database.Database;
import me.bodiw.guardian.database.Field;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ProfileCommand {

    SlashCommandInteractionEvent event;

    public ProfileCommand(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public void execute() {
        event.replyEmbeds(Embeds.profileEmbed(Database.getUser(Field.DISCORD_ID, event.getMember().getId())))
                .setEphemeral(true).queue();
    }
}
