package beloved.beloved.service.impl;

import beloved.beloved.dto.ReviewDTO;
import beloved.beloved.entity.Product;
import beloved.beloved.entity.Review;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.repository.ReviewRepository;
import beloved.beloved.service.IReviewService;
import beloved.beloved.service.impl.strategyReview.ReviewSorter;
import beloved.beloved.service.impl.strategyReview.SortByDateNewest;
import beloved.beloved.service.impl.strategyReview.SortByRatingDesc;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setDate(reviewDTO.getDate());
        review.setProduct(product);

        Review saved = reviewRepository.save(review);
        return convertToDTO(saved);
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return convertToDTO(review);
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {

        return reviewRepository.findByProductId(productId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ⭐ STRATEGY KULLANAN METOT
    public List<ReviewDTO> getReviewsSorted(Long productId, String sortBy) {

        List<Review> reviews = reviewRepository.findByProductId(productId);

        ReviewSorter sorter = new ReviewSorter();

        switch (sortBy.toLowerCase()) {
            case "rating":
                sorter.setStrategy(new SortByRatingDesc());
                break;
            case "date":
            default:
                sorter.setStrategy(new SortByDateNewest());
                break;
        }

        List<Review> sorted = sorter.sort(reviews);

        return sorted.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setDate(reviewDTO.getDate());

        if (reviewDTO.getProductId() != null) {
            Product product = productRepository.findById(reviewDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            review.setProduct(product);
        }

        Review updated = reviewRepository.save(review);
        return convertToDTO(updated);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getDate(),
                review.getProduct().getId()
        );
    }
}
