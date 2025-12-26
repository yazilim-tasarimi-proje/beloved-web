package beloved.beloved.repository;

import beloved.beloved.entity.RelationType;
import beloved.beloved.entity.SpecialDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialDayRepository extends JpaRepository<SpecialDay, Long> {
    Optional<SpecialDay> findByName(String name);

}
