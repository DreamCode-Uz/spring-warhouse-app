package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.Input;

import java.util.Optional;

@Repository
public interface InputRepository extends JpaRepository<Input, Integer> {

    @Query(value = "SELECT * FROM input WHERE id=(SELECT MAX(id) FROM input)", nativeQuery = true)
    Optional<Input> getMaxId();
}
