package me.bodiw.guardian.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class Command {

    SlashCommandInteractionEvent event;

    public Command(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public abstract void execute();
}
