package beloved.beloved.service;


import beloved.beloved.dto.ProductDto;
import beloved.beloved.dto.RecommendationRequestDto;

import java.util.List;

public interface IRecommendationService {
    List<ProductDto> recommend(RecommendationRequestDto request);
}
