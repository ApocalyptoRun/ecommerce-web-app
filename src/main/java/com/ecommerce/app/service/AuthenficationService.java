package com.ecommerce.app.service;

import com.ecommerce.app.dto.exceptions.AuthentificationFailException;
import com.ecommerce.app.model.AuthentificationToken;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenficationService {
    @Autowired
    TokenRepository tokenRepository;
    public void saveConfigurationToken(AuthentificationToken authentificationToken) {
        tokenRepository.save(authentificationToken);
    }

    public AuthentificationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
        AuthentificationToken authentificationToken = tokenRepository.findByToken(token);
        if (Objects.isNull(authentificationToken)){
            return null;
        }

        // aauthentificationToken is not null
        return authentificationToken.getUser();
    }

    public void authenticate(String token){
        // null check
        if (Objects.isNull(token)){
            // throw an exception
            throw new AuthentificationFailException("token not present");
        }

        if (Objects.isNull(getUser(token))){
            throw new AuthentificationFailException("token not valid");
        }
    }
}
