package me.bodiw;

import me.bodiw.guardian.database.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Embeds {

    public static MessageEmbed profileEmbed(User user) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Profile");
        builder.addField("Discord user", String.format("<@%s>", user.discordID()), true);
        builder.addField("Minecraft username", Utils.getMinecraftName(user.minecraftUUID()), true);
        builder.addField("Banned", user.banned() ? "Yes" : "No", true);
        builder.setColor(user.banned() ? 0xff0000 : 0x00ff00);
        builder.setThumbnail("https://crafatar.com/avatars/" + user.minecraftUUID() + "?size=128&overlay");
        return builder.build();
    }
}
