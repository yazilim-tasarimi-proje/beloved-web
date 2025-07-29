package beloved.beloved.repository;

import beloved.beloved.entity.Address;
import beloved.beloved.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAllByUser(User user);
}
