package beloved.beloved.service.impl.strategy;

import beloved.beloved.entity.Review;

import java.util.Comparator;
import java.util.List;

// Yorumları tarihe göre sıralar (en yeni → en eski).

public class SortByDateNewest implements ReviewSortingStrategy {

    @Override
    public List<Review> sort(List<Review> reviews) {
        return reviews.stream()
                .sorted(Comparator.comparing(Review::getDate).reversed())
                .toList();
    }
}
