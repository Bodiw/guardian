package me.bodiw.guardian.commands;

import me.bodiw.Embeds;
import me.bodiw.Utils;
import me.bodiw.guardian.database.Database;
import me.bodiw.guardian.database.Field;
import me.bodiw.guardian.database.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class UnbanCommand extends Command {

    public UnbanCommand(SlashCommandInteractionEvent event) {
        super(event);
    }

    @Override
    public void execute() {
        String name = event.getOption("Player").getAsString();
        String uuid = Utils.getMinecraftUUID(name, true);
        User target = Database.getUser(Field.MINECRAFT_UUID, uuid).setBanned(false);
        if (!target.equals(User.EMPTY)) {
            Database.addUser(target);
            event.replyEmbeds(Embeds.profileEmbed(target)).setEphemeral(true).queue();
        } else {
            event.reply("Could not find user with playername " + name).setEphemeral(true).queue();
        }
    }

}
