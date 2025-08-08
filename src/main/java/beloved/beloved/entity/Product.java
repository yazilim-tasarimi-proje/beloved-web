package beloved.beloved.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.ManyToOne;


@Entity
public class Product {

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

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private Set<OrderItem> orderItemSet=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<Recommendation> recommendations=new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> imageList= new ArrayList<Image>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Favorite> favoriteList=new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviewList=new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItemSet =new HashSet<>();

    public Product(Long id, String name, BigDecimal price, int stock, String description, String imageUrl, Boolean isPersonalized, String productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isPersonalized = isPersonalized;
        this.productType = productType;
    }
    public Product() {}

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

    public Set<OrderItem> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(Set<OrderItem> orderItemSet) {
        this.orderItemSet = orderItemSet;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Boolean isPersonalized() {
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
}
