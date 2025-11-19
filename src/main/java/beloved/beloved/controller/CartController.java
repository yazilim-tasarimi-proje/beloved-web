package beloved.beloved.controller;

import beloved.beloved.dto.AddToCartRequest;
import beloved.beloved.dto.CartDto;
import beloved.beloved.service.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    // 1. Sepete Birden Fazla Ürün Ekle
    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestBody AddToCartRequest request) {
        // Çoklu ürün ekleme için items listesini parametre olarak veriyoruz
        CartDto updatedCart = cartService.addToCart(
                request.getEmail(),
                request.getItems()
        );
        return ResponseEntity.ok(updatedCart);
    }

    // 2. Sepeti Listele
    @GetMapping
    public ResponseEntity<CartDto> getCart(@RequestParam String email) {
        CartDto cartDto = cartService.getCart(email);
        return ResponseEntity.ok(cartDto);
    }

    // 3. Sepetten Ürün Sil
    @DeleteMapping("/remove")
    public ResponseEntity<CartDto> removeFromCart(
            @RequestParam String email,
            @RequestParam Long productId
    ) {
        CartDto updatedCart = cartService.removeFromCart(email, productId);
        return ResponseEntity.ok(updatedCart);
    }
}
