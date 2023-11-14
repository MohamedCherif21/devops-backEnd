package com.example.cocomarket.interfaces;

import com.stripe.exception.StripeException;

public interface StripeService {

     void effectuerPaiement(Long montant, String devise, String token) throws StripeException;
}
