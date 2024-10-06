import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

public class GetByRiotID {
    public static void main(String[] args) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{RIOT_ID}?api_key={API_KEY}"))
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
    }
}
