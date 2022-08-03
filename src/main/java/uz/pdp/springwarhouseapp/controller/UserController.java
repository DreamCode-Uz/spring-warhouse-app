package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.payload.UserDTO;
import uz.pdp.springwarhouseapp.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return service.getAllUsers(page);
    }

    @GetMapping("/id={userId}")
    private ResponseEntity<?> getOne(@PathVariable("userId") Integer userId) {
        return service.getOneUser(userId);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody UserDTO userDTO) {
        return service.addUser(userDTO);
    }

    @PutMapping("/id={userId}")
    private ResponseEntity<?> update(@PathVariable("userId") Integer userId, @RequestBody UserDTO userDTO) {
        return service.editUser(userId, userDTO);
    }

    @DeleteMapping("/id={userId}")
    private ResponseEntity<?> delete(@PathVariable("userId") Integer userId) {
        return service.deleteUser(userId);
    }
}
