package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.Output;

import java.util.Optional;

@Repository
public interface OutputRepository extends JpaRepository<Output, Integer> {

    @Query(value = "SELECT * FROM output WHERE id=(SELECT MAX(id) FROM output)", nativeQuery = true)
    Optional<Output> getMaxId();
}
