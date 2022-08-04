package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.OutputProduct;

@Repository
public interface OutputProductRepository extends JpaRepository<OutputProduct, Integer> {
}
