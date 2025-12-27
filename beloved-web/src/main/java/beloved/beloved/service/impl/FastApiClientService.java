package beloved.beloved.service.impl;

import beloved.beloved.dto.ParsedGiftResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FastApiClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ParsedGiftResponseDto parseText(String text) {
        return restTemplate.postForObject(
                "http://localhost:8000/ai-recommend",
                Map.of("text", text),
                ParsedGiftResponseDto.class
        );
    }
}
