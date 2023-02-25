package me.bodiw.guardian.database;

public record User(String discordID, String minecraftUUID, boolean banned) {

    public static final User EMPTY = new User("", "", false);

    public User setMinecraftUUID(String minecraftUUID) {
        return new User(discordID, minecraftUUID, banned);
    }

    public User setBanned(boolean banned) {
        return new User(discordID, minecraftUUID, banned);
    }

    public boolean equals(User other) {
        return discordID.equals(other.discordID) && minecraftUUID.equals(other.minecraftUUID);
    }

    public String toString() {
        return "User{" +
                "discordID='" + discordID + '\'' +
                ", minecraftUUID='" + minecraftUUID + '\'' +
                ", banned=" + banned +
                '}';
    }
}
