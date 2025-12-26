package beloved.beloved.controller;

import beloved.beloved.dto.AiRecommendationRequestDto;
import beloved.beloved.dto.ParsedGiftResponseDto;
import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.RecommendationRequestDto;
import beloved.beloved.service.impl.fastapi.FastApiClientService;
import beloved.beloved.service.impl.fastapi.GiftParseMappingService;
import beloved.beloved.service.IRecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai-recommend")
public class AiRecommendationController {

    private final FastApiClientService fastApiClientService;
    private final GiftParseMappingService mappingService;
    private final IRecommendationService recommendationService;

    public AiRecommendationController(
            FastApiClientService fastApiClientService,
            GiftParseMappingService mappingService,
            IRecommendationService recommendationService
    ) {
        this.fastApiClientService = fastApiClientService;
        this.mappingService = mappingService;
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public List<ProductDto> recommendFromText(@RequestBody String text) {

        // 1️⃣ FastAPI çağrısı
        ParsedGiftResponseDto parsed =
                fastApiClientService.parseGiftText(text);

        // 2️⃣ String → ID
        AiRecommendationRequestDto aiRequest =
                mappingService.toRecommendationRequest(parsed);

        // 3️⃣ Recommendation DTO oluştur
        RecommendationRequestDto request = new RecommendationRequestDto();
        request.setRelationTypeId(aiRequest.getRelationTypeId());
        request.setSpecialDayId(aiRequest.getSpecialDayId());

        // (isteğe bağlı – şimdilik boş)
        request.setCategoryIds(null);
        request.setMaxBudget(null);

        // 4️⃣ GERÇEK öneri
        return recommendationService.recommend(request);
    }
}
