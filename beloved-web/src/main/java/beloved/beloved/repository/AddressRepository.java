package beloved.beloved.repository;

import beloved.beloved.entity.Address;
import beloved.beloved.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAllByUser(User user);

    //Aynı şehirdeki adresleri getir
    List<Address> findAllByUserAndCity(User user,String city);

    //Kullanıcının belli bir posta koduna sahip adresi var mı kontrol et(opsiyonel)
     boolean existsByPostalCodeAndUser(String postalCode, User user);

}
