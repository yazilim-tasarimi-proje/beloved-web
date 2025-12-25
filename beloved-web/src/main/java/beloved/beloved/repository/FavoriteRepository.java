package beloved.beloved.repository;

import beloved.beloved.entity.Favorite;
import beloved.beloved.entity.Product;
import beloved.beloved.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    //Belirli bir kullanıcının tüm favoriler:
    List<Favorite> findAllByUser(User user);

    //kullanıcının belli bir ürünü favorilere ekleyip eklemediği:
    Optional<Favorite> findByUserAndProduct(User user, Product product);

    //belli kullanıcı ve ürüne göre kaydı sil:
    void deleteByUserAndProduct(User user, Product product);

    //bellir ürün favori mi kontrolü:
    boolean existsByUserAndProduct(User user,Product product);

}
