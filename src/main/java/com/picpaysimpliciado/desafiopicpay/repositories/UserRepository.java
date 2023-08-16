package com.picpaysimpliciado.desafiopicpay.repositories;

import com.picpaysimpliciado.desafiopicpay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /* Cria um metodo que busca usuario pelo documento */
    Optional<User>findUserByDocument(String document);
    Optional<User>findUserById(Long id);
}
