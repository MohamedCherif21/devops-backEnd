package com.example.testunitaire;

import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.repository.ProduitRepository;
import com.example.cocomarket.services.ProductService;
import com.stripe.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductService productService;
    @Mock
    ProduitRepository productRepository;


    Produit product = new Produit(1, "Product1", 150L, 15.0f, 2f);
    List<Produit> productList = new ArrayList<Produit>() {
        {
            add(new Produit(1, "Product1", 150L, 150.0f, 2000f));
            add(new Produit(2, "Product2", 1500L, 1500.0f, 200f));
            add(new Produit(3, "Product3", 15000L, 15000.0f, 20f));
        }
    };

    @Test
    void addProduct() {
        Mockito.when(productRepository.save(Mockito.any(Produit.class))).thenReturn(product);
        Produit productTest = productService.createProduit(product);
        Assertions.assertNotNull(productTest);
    }

    @Test
    void getAllProductsTest() {
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        List<Produit> retrievedProducts = productService.getAllProduits();

        Assertions.assertEquals(3, retrievedProducts.size());
    }

    @Test
    void deleteProductByIdTest() {
        int productIdToDelete = 1;
        productService.deleteProduitById(productIdToDelete);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productIdToDelete);
    }

    @Test
    void getProductByIdTest() {
        int productId = 1;
        Produit product = new Produit(productId, "Product1", 150L, 15.0f, 2f);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Produit> retrievedProduct = Optional.ofNullable(productService.retrieveProduct(productId));

        Assertions.assertTrue(retrievedProduct.isPresent());
        Assertions.assertEquals(product.getId(), retrievedProduct.get().getId());
    }


}