package beloved.beloved.strategy.impl;

import beloved.beloved.entity.Review;
import beloved.beloved.strategy.ReviewSortingStrategy;

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
