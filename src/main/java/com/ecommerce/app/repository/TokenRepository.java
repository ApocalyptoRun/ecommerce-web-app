package com.ecommerce.app.repository;

import com.ecommerce.app.model.AuthentificationToken;
import com.ecommerce.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthentificationToken, Long> {
    AuthentificationToken findByUser(User user);
    AuthentificationToken findByToken(String token);


}
