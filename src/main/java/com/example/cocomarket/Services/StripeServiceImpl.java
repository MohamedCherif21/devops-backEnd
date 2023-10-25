package com.example.cocomarket.Services;


import com.example.cocomarket.Interfaces.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeServiceImpl implements StripeService {

    public StripeServiceImpl() {
        Stripe.apiKey="sk_test_51MkxwWBumFf5y8RzJF6k3wqx7NLaNNxKvhnxQnQzfizfHI2MNwCOnZCsiullqQD3Cnubyetks2WJABqERL07nWJD007Y5crYwq" ;
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
