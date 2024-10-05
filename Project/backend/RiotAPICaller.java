import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

public class RiotAPICaller {
    public static void main(String[] args) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/cosmiclatte/2220?api_key=RGAPI-9142670d-9703-4347-8851-7804b15ee9a0"))
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
            .header("Accept-Language", "en-US,en;q=0.9")
            .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
            .header("Origin", "https://developer.riotgames.com")
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