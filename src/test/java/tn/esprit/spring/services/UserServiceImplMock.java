package tn.esprit.spring.services;



import com.example.cocomarket.Entity.Categorie;

import com.example.cocomarket.Repository.Categorie_Repository;


import com.example.cocomarket.Services.Categorie_Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplMock {
    @Mock
    private Categorie_Repository categorieRepository;

    @InjectMocks
    private Categorie_Service categorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddnewCategorie() {

        Categorie categorie = new Categorie();
        categorie.setId(1);
        categorie.setType("TestType");


        when(categorieRepository.save(any())).thenReturn(categorie);


        Categorie result = categorieService.AddnewCategorie(categorie);

        // Vérifier le résultat
        assertEquals(1, result.getId());
        assertEquals("TestType", result.getType());


        verify(categorieRepository, times(1)).save(eq(categorie));
    }


}












