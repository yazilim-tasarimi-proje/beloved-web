package beloved.beloved.service.impl;

import beloved.beloved.dto.CartDto;
import beloved.beloved.dto.CartItemDto;
import beloved.beloved.entity.Cart;
import beloved.beloved.entity.CartItem;
import beloved.beloved.entity.Product;
import beloved.beloved.entity.User;
import beloved.beloved.repository.CartRepository;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.service.ICartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Çoklu ürün sepete ekleme
    @Override
    public CartDto addToCart(String email, List<CartItemDto> items) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setCartItems(new HashSet<>());
                    return cartRepository.save(newCart);
                });

        for (CartItemDto itemDto : items) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItem existingItem = cart.getCartItems().stream()
                    .filter(i -> i.getProduct().equals(product))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + itemDto.getQuantity());
            } else {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setQuantity(itemDto.getQuantity());
                newItem.setCart(cart);
                cart.getCartItems().add(newItem);
            }
        }

        cartRepository.save(cart);

        return entityToDto(cart);
    }

    // Sepeti listele
    @Override
    public CartDto getCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setCartItems(new HashSet<>());
                    return cartRepository.save(newCart);
                });

        return entityToDto(cart);
    }

    // Sepetten ürün kaldır
    @Override
    public CartDto removeFromCart(String email, Long productId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));

        cartRepository.save(cart);
        return entityToDto(cart);
    }

    // Entity -> DTO dönüşümü
    private CartDto entityToDto(Cart cart) {
        Set<CartItemDto> itemDtos = cart.getCartItems().stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());

        BigDecimal totalPrice = itemDtos.stream()
                .map(i -> i.getProductPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartDto dto = new CartDto();
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setCartItems(itemDtos);
        dto.setTotalPrice(totalPrice);
        dto.setDiscount(BigDecimal.ZERO);
        dto.setDiscountPrice(totalPrice);

        return dto;
    }

    private CartItemDto entityToDto(CartItem item) {
        Product product = item.getProduct();
        return new CartItemDto(
                item.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                item.getQuantity()
        );
    }
}
