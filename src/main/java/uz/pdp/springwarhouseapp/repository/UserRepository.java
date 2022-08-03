package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdNotAndPhoneNumber(Integer id, String phoneNumber);

    @Query(value = "SELECT * FROM users WHERE id=(SELECT MAX(id) FROM users)", nativeQuery = true)
    Optional<User> getMaxId();
}
