package com.ecommerce.app.service;


import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.dto.exceptions.AuthentificationFailException;
import com.ecommerce.app.dto.exceptions.CustomException;
import com.ecommerce.app.dto.user.SignInResponseDto;
import com.ecommerce.app.dto.user.SigninDto;
import com.ecommerce.app.dto.user.SignupDto;
import com.ecommerce.app.model.AuthentificationToken;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenficationService authenficationService;

    public ResponseDto signUp(SignupDto signupDto) {
        //check if email already exist
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new ClassCastException("User already exist");
        }

        //hash the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // save user
        User user = new User();
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        // create a token
        final AuthentificationToken authentificationToken = new AuthentificationToken(user);

        authenficationService.saveConfigurationToken(authentificationToken);

        return new ResponseDto("success","user has been register");

    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }


    public String signIn(SigninDto signinDto) {
        //Find user by email
        User user = userRepository.findByEmail(signinDto.getEmail());


        //Verify if user exist
        if (Objects.isNull(user)){
            return "User is not valid";
        }

        //hash the password and compare to BD password
        try {
           if (!user.getPassword().equals(hashPassword(signinDto.getPassword()))){
               return "Wrong password";
           }
        } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
        }

        //if password match
        //Retrive the token
        AuthentificationToken token = authenficationService.getToken(user);

        if(Objects.isNull(token)){
            return "token is not present";
        }

        return token.getToken();
    }
}
