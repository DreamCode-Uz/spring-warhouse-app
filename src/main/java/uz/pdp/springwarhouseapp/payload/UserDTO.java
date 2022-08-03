package uz.pdp.springwarhouseapp.payload;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String password;
    private boolean active;
    private Set<Integer> warehouseId;
}
