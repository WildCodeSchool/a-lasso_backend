package com.back_alasso.Authentication;

import com.back_alasso.Address.Address;
import java.util.Optional;

public record AssociationRegistrationDTO(String siret, String name, String email, String password, Optional<String> mobile_phone, Address address) {}
