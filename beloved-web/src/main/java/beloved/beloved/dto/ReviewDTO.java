package beloved.beloved.dto;

import java.time.LocalDateTime;

public class ReviewDTO {

    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime date;
    private Long productId;

    public ReviewDTO() {}

    public ReviewDTO(Long id, int rating, String comment,
                     LocalDateTime date, Long productId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
