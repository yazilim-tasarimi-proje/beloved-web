package beloved.beloved.service.impl;

import beloved.beloved.dto.FavoriteDto;
import beloved.beloved.entity.Favorite;
import beloved.beloved.entity.Product;
import beloved.beloved.entity.User;
import beloved.beloved.repository.FavoriteRepository;
import beloved.beloved.repository.ProductRepository;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.service.IFavoriteService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService implements IFavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public FavoriteDto addFavorite(String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (favoriteRepository.existsByUserAndProduct(user, product)) {
            throw new RuntimeException("This product is already in favorites!");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        Favorite saved = favoriteRepository.save(favorite);
        return new FavoriteDto(saved.getId(), saved.getUser().getEmail(), saved.getProduct().getId());
    }

    @Override
    public void removeFavorite(String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        favoriteRepository.deleteByUserAndProduct(user, product);
    }

    @Override
    public List<FavoriteDto> getUserFavorites(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return favoriteRepository.findAllByUser(user).stream()
                .map(fav -> new FavoriteDto(fav.getId(),
                        fav.getUser().getEmail(),
                        fav.getProduct().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isProductFavorited(String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return favoriteRepository.existsByUserAndProduct(user, product);
    }
}
