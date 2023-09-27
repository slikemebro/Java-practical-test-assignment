package com.ua.glebkorobov.javapracticaltestassignment.services;

import com.ua.glebkorobov.javapracticaltestassignment.dto.UserDto;
import com.ua.glebkorobov.javapracticaltestassignment.entities.UserEntity;
import com.ua.glebkorobov.javapracticaltestassignment.exceptions.NotFoundUser;
import com.ua.glebkorobov.javapracticaltestassignment.exceptions.WrongDates;
import com.ua.glebkorobov.javapracticaltestassignment.exceptions.WrongValidation;
import com.ua.glebkorobov.javapracticaltestassignment.repositories.UserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private final UserRepository userRepository;

    public Optional<UserEntity> save(UserDto userDto) {
        if (!validator.validate(userDto).isEmpty()){
            throw new WrongValidation(validator.validate(userDto).toString());
        }
        Optional<UserEntity> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()){
            return Optional.empty();
        }
        return Optional.of(userRepository.save(UserEntity.builder()
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .birthday(userDto.getBirthday())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phoneNumber(userDto.getPhoneNumber())
                .build()));
    }

    public Optional<UserEntity> update(long id, String email, String firstName, String lastName, String address,
                                       String phoneNumber, LocalDate birthday) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new NotFoundUser("User not found");
        }

        UserEntity user = userOptional.get();

        if (email != null){
            user.setEmail(email);
        }
        if (firstName != null){
            user.setFirstName(firstName);
        }
        if (lastName != null){
            user.setLastName(lastName);
        }
        if (address != null){
            user.setAddress(address);
        }
        if (phoneNumber != null){
            user.setPhoneNumber(phoneNumber);
        }
        if (birthday != null){
            user.setBirthday(birthday);
        }

        if (!validator.validate(user).isEmpty()){
            throw new WrongValidation(validator.validate(user).toString());
        }

        return Optional.of(userRepository.save(user));
    }

    public Optional<UserEntity> update(long id, UserDto userDto) {
        if (!validator.validate(userDto).isEmpty()){
            throw new WrongValidation(validator.validate(userDto).toString());
        }
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new NotFoundUser("User not found");
        }
        UserEntity user = userOptional.get();

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());



        if (!validator.validate(user).isEmpty()){
            throw new WrongValidation(validator.validate(user).toString());
        }

        return Optional.of(userRepository.save(user));
    }

    public Optional<UserEntity> delete(long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new NotFoundUser("User not found");
        }
        userRepository.deleteById(id);
        return userOptional;
    }

    public Collection<UserEntity> getFromToDate(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)){
            throw new WrongDates("From date is before than to date");
        }
        return userRepository.findByBirthdayBetween(fromDate, toDate);
    }
}
