package com.picpaysimpliciado.desafiopicpay.services;

import com.picpaysimpliciado.desafiopicpay.repositories.TransactionRepository;
import com.picpaysimpliciado.desafiopicpay.domain.transaction.Transaction;
import com.picpaysimpliciado.desafiopicpay.domain.user.User;
import com.picpaysimpliciado.desafiopicpay.dtos.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    /* RestTemplate oferece para fazer comunicacao http*/
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;


    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validadeTransaction(sender, transaction.value());
        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized){
            throw new Exception("Transação não autorizado");
        }
        LocalDateTime now = LocalDateTime.now();

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));


        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;

    }
    public boolean authorizeTransaction(User sender, BigDecimal value){
       ResponseEntity<Map> authorizationResponse= restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

       if(authorizationResponse.getStatusCode() == HttpStatus.OK ){
           String message = (String) authorizationResponse.getBody().get("message");
           return "Autorizado".equalsIgnoreCase(message);
       }else return false;
    }
}
