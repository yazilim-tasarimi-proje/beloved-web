package beloved.beloved.controller;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.RecommendationRequestDto;
import beloved.beloved.service.impl.RecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public List<ProductDto> recommendProducts(
            @RequestBody RecommendationRequestDto request
    ) {
        return recommendationService.recommend(request);
    }
}

