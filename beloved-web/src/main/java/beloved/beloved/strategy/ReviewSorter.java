package beloved.beloved.strategy;

import beloved.beloved.entity.Review;

import java.util.List;

//Hangi stratejinin kullanılacağına dışarıdan karar verilir.
//Seçilen strateji ile review listesini sıralar.
//Sıralama davranışı runtime’da (çalışırken) değiştirilebilir.

public class ReviewSorter {
    private ReviewSortingStrategy strategy;

    public void setStrategy(ReviewSortingStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Review> sort(List<Review> reviews) {
        return strategy.sort(reviews);
    }
}
