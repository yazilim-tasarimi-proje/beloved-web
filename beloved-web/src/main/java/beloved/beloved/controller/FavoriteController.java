package beloved.beloved.controller;

import beloved.beloved.dto.FavoriteDto;
import beloved.beloved.service.IFavoriteService;
import beloved.beloved.service.impl.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final IFavoriteService favoriteService;
    private final JWTUtil jwtUtil;

    public FavoriteController(IFavoriteService favoriteService, JWTUtil jwtUtil) {
        this.favoriteService = favoriteService;
        this.jwtUtil = jwtUtil;
    }

    private String getEmailFromRequest(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        return jwtUtil.extractUsername(token);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<FavoriteDto> addFavorite(@PathVariable Long productId, HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(favoriteService.addFavorite(email, productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long productId, HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        favoriteService.removeFavorite(email, productId);
        return ResponseEntity.ok("Favorite removed successfully");
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getFavorites(HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(favoriteService.getUserFavorites(email));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> checkFavorite(@PathVariable Long productId, HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        return ResponseEntity.ok(favoriteService.isProductFavorited(email, productId));
    }
}