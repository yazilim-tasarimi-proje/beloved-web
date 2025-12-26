package beloved.beloved.service.impl;

import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.RecommendationRequestDto;
import beloved.beloved.entity.Product;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.service.IRecommendationService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService implements IRecommendationService {

    private final ProductRepository productRepository;

    public RecommendationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> recommend(RecommendationRequestDto request) {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> {
                    int score = 0;

                    // 🔹 SpecialDay uyumu (en güçlü sinyal)
                    if (request.getSpecialDayId() != null
                            && product.getSuitableSpecialDays() != null
                            && product.getSuitableSpecialDays()
                            .stream()
                            .anyMatch(sd -> sd.getId().equals(request.getSpecialDayId()))) {
                        score += 40;
                    }

                    // 🔹 RelationType uyumu
                    if (request.getRelationTypeId() != null
                            && product.getSuitableRelationTypes() != null
                            && product.getSuitableRelationTypes()
                            .stream()
                            .anyMatch(rt -> rt.getId().equals(request.getRelationTypeId()))) {
                        score += 30;
                    }

                    // 🔹 Kategori uyumu
                    if (request.getCategoryIds() != null
                            && product.getCategory() != null
                            && request.getCategoryIds().contains(product.getCategory().getId())) {
                        score += 30;
                    }

                    // 🔹 Bütçe uyumu
                    if (request.getMaxBudget() != null
                            && product.getPrice() != null
                            && product.getPrice().compareTo(request.getMaxBudget()) <= 0) {
                        score += 20;
                    }

                    ProductDto dto = new ProductDto(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getStock(),
                            product.getDescription(),
                            product.getImageUrl(),
                            product.getCategory() != null ? product.getCategory().getId() : null,
                            product.isPersonalized(),
                            product.getProductType()
                    );

                    return new ScoredProduct(dto, score);
                })
                .sorted(Comparator.comparingInt(ScoredProduct::score).reversed())
                .map(ScoredProduct::dto)
                .limit(5)
                .collect(Collectors.toList());
    }

    // helper record (class scope’ta)
    private record ScoredProduct(ProductDto dto, int score) {}
}
