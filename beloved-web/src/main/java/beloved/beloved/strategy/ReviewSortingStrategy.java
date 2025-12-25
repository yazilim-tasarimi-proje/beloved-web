package beloved.beloved.strategy;

import beloved.beloved.entity.Review;

import java.util.List;

//Her sıralama algoritması için ortak bir davranış tanımlar.
//“Bir listeyi sırala” yönteminin imzası burada olur.

public interface ReviewSortingStrategy {
    List<Review> sort(List<Review> reviews);
}
