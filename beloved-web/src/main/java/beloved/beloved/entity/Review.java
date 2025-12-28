package beloved.beloved.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String comment;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    protected Review() {
        // JPA için
    }

    private Review(int rating, String comment, Product product) {
        this.rating = rating;
        this.comment = comment;
        this.product = product;
    }



    public static Review create(int rating, String comment, Product product) {
        return new Review(rating, comment, product);
    }

    @PrePersist
    public void onCreate() {
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
