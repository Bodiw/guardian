package me.bodiw.guardian.database;

public enum Field {

    DISCORD_ID("discord_id"),
    MINECRAFT_UUID("minecraft_uuid");

    private final String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
