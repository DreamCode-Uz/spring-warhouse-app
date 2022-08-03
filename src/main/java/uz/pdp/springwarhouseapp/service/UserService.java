package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.User;
import uz.pdp.springwarhouseapp.entity.Warehouse;
import uz.pdp.springwarhouseapp.payload.UserDTO;
import uz.pdp.springwarhouseapp.repository.UserRepository;
import uz.pdp.springwarhouseapp.repository.WarehouseRepository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Service
public class UserService {
    private final UserRepository repository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public UserService(UserRepository repository, WarehouseRepository warehouseRepository) {
        this.repository = repository;
        this.warehouseRepository = warehouseRepository;
    }

    public ResponseEntity<Page<User>> getAllUsers(Integer page) {
        return new ResponseEntity<>(repository.findAll(PageRequest.of(page > 0 ? page - 1 : 0, 10)), OK);
    }

    public ResponseEntity<?> getOneUser(Integer userId) {
        Optional<User> optionalUser = repository.findById(userId);
        if (!optionalUser.isPresent()) return new ResponseEntity<>("User not found.", NOT_FOUND);
        return new ResponseEntity<>(optionalUser.get(), OK);
    }

    public ResponseEntity<?> addUser(UserDTO dto) {
        if (repository.existsByPhoneNumber(dto.getPhoneNumber()))
            return new ResponseEntity<>("This phone is already registered.", ALREADY_REPORTED);
        if (dto.getLastname() == null || dto.getFirstname() == null || dto.getPhoneNumber() == null || dto.getWarehouseId().size() == 0) {
            return new ResponseEntity<>("The value is not fully entered.", PRECONDITION_FAILED);
        }
        Set<Warehouse> warehouses = warehouseCheck(dto.getWarehouseId());
        if (warehouses == null)
            return new ResponseEntity<>("A warehouse id that does not exist was entered.", NOT_FOUND);
        if (warehouses.size() > 0) return new ResponseEntity<>("Warehouse not included.", PRECONDITION_FAILED);
        Optional<User> optional = repository.getMaxId();
        String code = optional.map(value -> String.valueOf(value.getId() + 1)).orElse("0");
        User user = new User(dto.getFirstname(), dto.getLastname(), dto.getPassword(), dto.getPhoneNumber(), code, warehouses);

        return new ResponseEntity<>(repository.save(user), OK);
    }

    public ResponseEntity<?> editUser(Integer id, UserDTO dto) {
        Optional<User> optionalUser = repository.findById(id);
        if (!optionalUser.isPresent()) return new ResponseEntity<>("User not found.", NOT_FOUND);
        User user = optionalUser.get();
        user.setActive(dto.isActive());
        if (!repository.existsByIdNotAndPhoneNumber(id, dto.getPhoneNumber()) && dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getFirstname() != null) user.setFirstname(dto.getFirstname());
        if (dto.getLastname() != null) user.setLastname(dto.getLastname());
        if (dto.getPassword() != null) user.setPassword(dto.getPassword());
        Set<Warehouse> warehouses = warehouseCheck(dto.getWarehouseId());
        if (warehouses != null && warehouses.size() > 0) user.setWarehouses(warehouses);
        return new ResponseEntity<>(repository.save(user), OK);
    }

    public ResponseEntity<?> deleteUser(Integer userId) {
        Optional<User> optionalUser = repository.findById(userId);
        if (!optionalUser.isPresent()) return new ResponseEntity<>("User not found.", NOT_FOUND);
        try {
            repository.delete(optionalUser.get());
        } catch (Exception e) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>("User successfully deleted.", OK);
    }

    //    ACTIONS
    public Set<Warehouse> warehouseCheck(Set<Integer> warehousesId) {
        Iterator<Integer> iterator = warehousesId.iterator();
        Set<Warehouse> warehouses = new HashSet<>();
        while (iterator.hasNext()) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(iterator.next());
            if (!optionalWarehouse.isPresent()) return null;
            warehouses.add(optionalWarehouse.get());
        }
        return warehouses;
    }
}
