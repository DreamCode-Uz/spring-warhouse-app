package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.InputProduct;

@Repository
public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {
}
