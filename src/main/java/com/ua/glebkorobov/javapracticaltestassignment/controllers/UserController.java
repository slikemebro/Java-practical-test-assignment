package com.ua.glebkorobov.javapracticaltestassignment.controllers;

import com.ua.glebkorobov.javapracticaltestassignment.dto.UserDto;
import com.ua.glebkorobov.javapracticaltestassignment.entities.UserEntity;
import com.ua.glebkorobov.javapracticaltestassignment.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

import static com.ua.glebkorobov.javapracticaltestassignment.utils.ResponseUtils.deleteResponse;
import static com.ua.glebkorobov.javapracticaltestassignment.utils.ResponseUtils.getResponse;
import static com.ua.glebkorobov.javapracticaltestassignment.utils.ResponseUtils.postResponse;
import static com.ua.glebkorobov.javapracticaltestassignment.utils.ResponseUtils.updateResponse;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDto userDto) {
        return postResponse(userService.save(userDto));
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserEntity> update(
            @PathVariable("id") long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phoneNumber,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)LocalDate birthday
            ){
        return updateResponse(userService.update(id, email, firstName, lastName, address, phoneNumber, birthday));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserEntity> updateAllFields(@PathVariable("id") long id, @RequestBody UserDto user){
        return updateResponse(userService.update(id, user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("id") long id){
        return deleteResponse(userService.delete(id));
    }

    @GetMapping
    public ResponseEntity<Collection<UserEntity>> searchUsersFromToDate(@RequestParam LocalDate fromDate,
                                                                        @RequestParam LocalDate toDate){
        return getResponse(userService.getFromToDate(fromDate, toDate));
    }
}
