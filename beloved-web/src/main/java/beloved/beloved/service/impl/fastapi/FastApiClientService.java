package beloved.beloved.service.impl.fastapi;

import beloved.beloved.dto.ParsedGiftResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FastApiClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ParsedGiftResponseDto parseGiftText(String text) {

        String url = "http://localhost:8000/parse-gift-text";

        Map<String, String> requestBody = Map.of(
                "text", text
        );

        return restTemplate.postForObject(
                url,
                requestBody,
                ParsedGiftResponseDto.class
        );
    }
}
