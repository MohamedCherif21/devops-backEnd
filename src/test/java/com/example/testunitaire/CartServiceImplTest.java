package com.example.testunitaire;

import com.example.cocomarket.services.CartService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class CartServiceImplTest {

    @InjectMocks
    CartService cartService;


}
