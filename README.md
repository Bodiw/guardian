# Guardian

## Description

- Guardian is a discord bot designed for integration with WhitelistSync2, providing a self-whitelist via Discord's slash commands.

## Compilation and running

- Guardian uses Java 17, so you will need to have said Java version to run the .jar executable.
- Guardian uses 2 environment variables, `GUARDIAN_TOKEN` and `MINECRAFT_SQLITE_DB`. The former is the bot's token, and the latter is the path to WhitelistSync2's sqlite server.

## Commands

**Note:** `Admin commands` are commands that require the `KICK` permission.

- `/whitelist <Player>` : Self-use command for players to whitelist themselves, can also be used to update a current whitelist entry.
- `/profile` : Self-use command for players to view their current whitelist entry.
- `user <Discord User>` : Admin-only command to view a player's whitelist entry.
- `player <Player>` : Admin-only command to view a player's whitelist entry.
- `ban <Player>` : Admin-only command to ban a player from the whitelist system.
- `unban <Player>` : Admin-only command to unban a player from the whitelist system.
