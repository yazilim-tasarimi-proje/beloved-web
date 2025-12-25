package beloved.beloved.controller;

import beloved.beloved.dto.ReviewDTO;
import beloved.beloved.service.impl.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.saveReview(reviewDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }


    //YENİ STRATEGY ENDPOINT
    //Kullanıcıdan sıralama türünü (sortBy) alır.
    //sorted?sortBy=date
    //

    // sorted?sortBy=rating
    //
    //Yönlendirmeyi servise bırakır (controller sadece API endpointidir).


    @GetMapping("/product/{productId}/sorted")
    public ResponseEntity<List<ReviewDTO>> getSortedReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return ResponseEntity.ok(reviewService.getReviewsSorted(productId, sortBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewDTO reviewDTO
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
