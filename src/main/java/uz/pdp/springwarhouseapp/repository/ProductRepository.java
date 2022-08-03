package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByNameAndCategoryId(String name, Integer category_id);

    @Query(value = "SELECT * FROM product where id=(SELECT max(id) FROM product)", nativeQuery = true)
    Optional<Product> getMaxId();
}
