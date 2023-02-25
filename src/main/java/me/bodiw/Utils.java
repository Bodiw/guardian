package me.bodiw;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

public class Utils {

    public static String getMinecraftName(String UUID) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + UUID))
                .GET()
                .build();

        try {
            String name = (new JSONObject((HttpClient.newHttpClient().send(request, BodyHandlers.ofString()).body())))
                    .getString("name");
            return name;
        } catch (Exception e) {
            System.out.println("Error receiving the username http response from the mojang api");
        }

        return "";
    }

    public static String getMinecraftUUID(String name, boolean dashes) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create("https://api.mojang.com/users/profiles/minecraft/" + name))
                .GET()
                .build();

        try {
            String uuid = (new JSONObject((HttpClient.newHttpClient().send(request, BodyHandlers.ofString()).body())))
                    .getString("id");
            if (dashes) {
                return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-"
                        + uuid.substring(16, 20) + "-" + uuid.substring(20);
            } else {
                return uuid;
            }
        } catch (Exception e) {
            System.out.println("Error receiving the UUID response for player " + name);
        }

        return "";
    }
}
