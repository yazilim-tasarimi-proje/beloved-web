package beloved.beloved.service;

import beloved.beloved.dto.CartDto;

public interface ICartService {
    public CartDto addToCart(String email, Long productId, int quantity);
    public CartDto getCart(String email);
    public CartDto removeFromCart(String email, Long productId);
}
