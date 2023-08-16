package com.picpaysimpliciado.desafiopicpay.dtos;

import com.picpaysimpliciado.desafiopicpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document,
                      BigDecimal balance, String email, String password, UserType userType) {
}
