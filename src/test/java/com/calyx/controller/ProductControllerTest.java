package com.calyx.controller;

import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.ProductResponse;
import com.calyx.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @Before
    public void setUp() {
        productController = new ProductController(productService);
    }

    @Test
    public void create_delegatesToService() {
        ProductRequest request = new ProductRequest("Apple", "fruits", 52, 0.3, 0.2, 13.8);
        ProductResponse expected = new ProductResponse(1L, "Apple", "fruits", 52, 0.3, 0.2, 13.8);
        when(productService.addProduct(request)).thenReturn(expected);

        ProductResponse response = productController.create(request);

        assertEquals("Apple", response.name());
        verify(productService).addProduct(request);
    }

    @Test
    public void getAll_delegatesToService() {
        when(productService.getAllProducts()).thenReturn(List.of());

        assertEquals(0, productController.getAll().size());
    }
}
