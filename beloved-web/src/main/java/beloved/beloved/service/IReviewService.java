package beloved.beloved.service;

import beloved.beloved.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
    ReviewDTO saveReview(ReviewDTO reviewDTO);

    ReviewDTO getReviewById(Long id);
    List<ReviewDTO> getReviewsByProductId(Long productId);
    List<ReviewDTO> getAllReviews();
    ReviewDTO updateReview(Long id,ReviewDTO reviewDTO);
    void deleteReview(Long id);
}
