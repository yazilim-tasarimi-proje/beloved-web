package beloved.beloved.service;
import beloved.beloved.dto.CartDto;
import beloved.beloved.dto.CartItemDto;
import java.util.List;


public interface ICartService {
    CartDto addToCart(String email, List<CartItemDto> items);
    CartDto getCart(String email);
    CartDto removeFromCart(String email, Long productId);
}
