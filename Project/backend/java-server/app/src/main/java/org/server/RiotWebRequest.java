package org.server;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RiotWebRequest {
//    private final String RIOT_API_KEY;
    public static String getPUUID(String uuid) {
        String[] namePlusID = uuid.split("#");
        String gameName = namePlusID[0];
        String ID = namePlusID[1];
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + ID + "?api_key={RIOT_API_KEY}"))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());
        return response.body();
    }
    public static String getData(String puuid) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://br1.api.riotgames.com/lol/challenges/v1/player-data/" + puuid + "?api_key={RIOT_API_KEY}"))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());
        return response.body();
    }
    public static void main(String[] args) {
        getPUUID("cosmiclatte#2220");//temp values
        getData("d_SZpQJYLou1d-B5bmeLKY3XE0hiW-qIrDgF4zwThsVA_T7CEti-rP2MckEiGP_8ZasdfTJqLhRbrA");
    }

}
