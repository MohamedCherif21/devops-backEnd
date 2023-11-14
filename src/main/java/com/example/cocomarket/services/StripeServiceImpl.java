package com.example.cocomarket.services;


import com.example.cocomarket.interfaces.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;
    public StripeServiceImpl() {
        Stripe.apiKey = stripeApiKey;
    }

    @Override
    public void effectuerPaiement(Long montant, String devise, String token) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", montant);
        params.put("currency", devise);
        params.put("source", token);

        Charge.create(params);
    }
}
