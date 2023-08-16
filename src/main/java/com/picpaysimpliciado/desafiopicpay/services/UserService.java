package com.picpaysimpliciado.desafiopicpay.services;

import com.picpaysimpliciado.desafiopicpay.domain.user.User;
import com.picpaysimpliciado.desafiopicpay.domain.user.UserType;
import com.picpaysimpliciado.desafiopicpay.dtos.UserDTO;
import com.picpaysimpliciado.desafiopicpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;


    public void validadeTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType()== UserType.MERCHANT){
            throw new Exception("Usuario do tipo Lojista nao esta autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount)< 0){
            throw new Exception("Saldo insuficiente");
        }

    }
    /* Se nao encontrar o usuario*/
    public User findUserById(Long id) throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));

    }

    public  User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public List<User> getAllUsers(){

        return this.repository.findAll();
    }
    public void saveUser(User user){
        this.repository.save(user);
    }
}
