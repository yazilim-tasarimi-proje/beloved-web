package beloved.beloved.service.impl.strategy;

import beloved.beloved.entity.Review;

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
