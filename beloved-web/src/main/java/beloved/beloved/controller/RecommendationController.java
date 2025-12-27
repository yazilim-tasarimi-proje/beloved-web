package beloved.beloved.controller;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.service.IRecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommend")
public class RecommendationController {

    private final IRecommendationService recommendationService;

    public RecommendationController(IRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public List<ProductDto> recommend(@RequestBody Map<String, String> body) {
        return recommendationService.recommendFromText(body.get("text"));
    }
}
