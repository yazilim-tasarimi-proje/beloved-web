package beloved.beloved.repository;

import beloved.beloved.entity.Order;
import beloved.beloved.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    //optional sadece 1 tane sipariş döndürür,list ise birden fazla sipariş döndürür.
}
