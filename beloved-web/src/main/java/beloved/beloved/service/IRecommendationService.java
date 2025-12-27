package beloved.beloved.service;

import beloved.beloved.dto.ProductDto;
import java.util.List;

public interface IRecommendationService {
    List<ProductDto> recommendFromText(String text);
}
