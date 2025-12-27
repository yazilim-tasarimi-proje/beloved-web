package beloved.beloved.service.impl;

import beloved.beloved.dto.ParsedGiftResponseDto;
import beloved.beloved.dto.ProductDto;
import beloved.beloved.entity.Product;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.service.IRecommendationService;
import beloved.beloved.service.impl.FastApiClientService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationService implements IRecommendationService {

    private final FastApiClientService aiClient;
    private final ProductRepository productRepository;

    public RecommendationService(
            FastApiClientService aiClient,
            ProductRepository productRepository
    ) {
        this.aiClient = aiClient;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> recommendFromText(String text) {

        ParsedGiftResponseDto ai = aiClient.parseText(text);

        return productRepository.findAll().stream()
                .map(p -> score(p, ai))
                .filter(sp -> sp.score > 0)
                .sorted(Comparator.comparingInt(Scored::score).reversed())
                .limit(5)
                .map(sp -> toDto(sp.product))
                .toList();
    }

    private Scored score(Product p, ParsedGiftResponseDto ai) {
        int score = 0;

        if (ai.getRelationType() != null &&
                p.getSuitableRelationTypes().stream()
                        .anyMatch(r -> r.getName().equals(ai.getRelationType()))) {
            score += 30;
        }

        if (ai.getSpecialDay() != null &&
                p.getSuitableSpecialDays().stream()
                        .anyMatch(s -> s.getName().equals(ai.getSpecialDay()))) {
            score += 40;
        }

        if (ai.getColor() != null &&
                p.getDescription() != null &&
                p.getDescription().toLowerCase().contains(ai.getColor())) {
            score += 20;
        }

        if (ai.getMaxBudget() != null &&
                p.getPrice().intValue() <= ai.getMaxBudget()) {
            score += 10;
        }

        if (Boolean.TRUE.equals(ai.getPersonalized()) &&
                Boolean.TRUE.equals(p.getPersonalized())) {
            score += 10;
        }

        return new Scored(p, score);
    }

    private ProductDto toDto(Product p) {
        return new ProductDto(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getStock(),
                p.getDescription(),
                p.getImageUrl(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getPersonalized(),
                p.getProductType()
        );
    }

    private record Scored(Product product, int score) {}
}
