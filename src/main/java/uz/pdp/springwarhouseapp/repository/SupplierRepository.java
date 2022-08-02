package uz.pdp.springwarhouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springwarhouseapp.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdNotAndPhoneNumber(Integer id, String phoneNumber);
}
