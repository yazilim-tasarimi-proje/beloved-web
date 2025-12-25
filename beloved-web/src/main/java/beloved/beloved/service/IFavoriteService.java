package beloved.beloved.service;

import beloved.beloved.dto.FavoriteDto;

import java.util.List;

public interface IFavoriteService {

    FavoriteDto addFavorite(String userEmail, Long productId);

    void removeFavorite(String userEmail, Long productId);

    List<FavoriteDto> getUserFavorites(String userEmail);

    boolean isProductFavorited(String userEmail,Long productId);

}
