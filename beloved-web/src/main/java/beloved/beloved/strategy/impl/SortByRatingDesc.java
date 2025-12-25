package beloved.beloved.strategy.impl;

import beloved.beloved.entity.Review;
import beloved.beloved.strategy.ReviewSortingStrategy;

import java.util.Comparator;
import java.util.List;

//Yorumları puana göre sıralar (yüksek → düşük).
public class SortByRatingDesc implements ReviewSortingStrategy {

    @Override
    public List<Review> sort(List<Review> reviews){
        return reviews.stream()
                .sorted(Comparator.comparing(Review::getRating).reversed())
                .toList();
    }
}
