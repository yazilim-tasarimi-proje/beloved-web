package beloved.beloved.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Product {

    /* ===================== */
    /* CORE FIELDS */
    /* ===================== */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private int stock;
    private String description;
    private String imageUrl;
    private Boolean isPersonalized;
    private String productType;

    /* ===================== */
    /* RELATIONS */
    /* ===================== */

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItemSet = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Recommendation> recommendations = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Favorite> favoriteList = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviewList = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItemSet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_special_day",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "special_day_id")
    )
    private Set<SpecialDay> suitableSpecialDays = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_relation_type",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "relation_type_id")
    )
    private Set<RelationType> suitableRelationTypes = new HashSet<>();

    /* ===================== */
    /* CONSTRUCTORS */
    /* ===================== */

    protected Product() {
        // JPA için zorunlu
    }

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.stock = builder.stock;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.isPersonalized = builder.isPersonalized;
        this.productType = builder.productType;
        this.category = builder.category;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal price;
        private int stock;
        private String description;
        private String imageUrl;
        private Boolean isPersonalized = false;
        private String productType;
        private Category category;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder personalized(Boolean personalized) {
            this.isPersonalized = personalized;
            return this;
        }

        public Builder productType(String productType) {
            this.productType = productType;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getPersonalized() {
        return isPersonalized;
    }

    public void setPersonalized(Boolean personalized) {
        isPersonalized = personalized;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<OrderItem> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(Set<OrderItem> orderItemSet) {
        this.orderItemSet = orderItemSet;
    }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Set<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(Set<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public Set<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(Set<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Set<CartItem> getCartItemSet() {
        return cartItemSet;
    }

    public void setCartItemSet(Set<CartItem> cartItemSet) {
        this.cartItemSet = cartItemSet;
    }

    public Set<SpecialDay> getSuitableSpecialDays() {
        return suitableSpecialDays;
    }

    public void setSuitableSpecialDays(Set<SpecialDay> suitableSpecialDays) {
        this.suitableSpecialDays = suitableSpecialDays;
    }

    public Set<RelationType> getSuitableRelationTypes() {
        return suitableRelationTypes;
    }

    public void setSuitableRelationTypes(Set<RelationType> suitableRelationTypes) {
        this.suitableRelationTypes = suitableRelationTypes;
    }
}
