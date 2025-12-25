package beloved.beloved.dto;

import java.util.List;

public class AddToCartRequest {
    private String email;
    private List<CartItemDto> items;  // Çoklu ürün listesi

    public AddToCartRequest() {}

    public AddToCartRequest(String email, List<CartItemDto> items) {
        this.email = email;
        this.items = items;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
