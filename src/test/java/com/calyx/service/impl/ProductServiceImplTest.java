package com.calyx.service.impl;

import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.ProductResponse;
import com.calyx.model.Product;
import com.calyx.repository.ProductRepository;
import com.calyx.testutil.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    @Before
    public void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void addProduct_savesAndReturnsResponse() {
        ProductRequest request = new ProductRequest("Apple", "fruits", 52, 0.3, 0.2, 13.8);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(10L);
            return product;
        });

        ProductResponse response = productService.addProduct(request);

        assertEquals(Long.valueOf(10L), response.id());
        assertEquals("Apple", response.name());
        assertEquals("fruits", response.category());
    }

    @Test
    public void getProductById_returnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleProduct()));

        ProductResponse response = productService.getProductById(1L);

        assertEquals("Chicken breast", response.name());
    }

    @Test
    public void getProductById_throwsWhenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(99L));
    }

    @Test
    public void deleteProduct_removesExistingProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleProduct()));

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    public void getAllProducts_returnsMappedList() {
        when(productRepository.findAll()).thenReturn(List.of(TestData.sampleProduct()));

        List<ProductResponse> products = productService.getAllProducts();

        assertEquals(1, products.size());
    }

    @Test
    public void updateProduct_updatesCategoryWhenProvided() {
        Product existing = TestData.sampleProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.update(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.updateProduct(
                1L,
                new ProductRequest("Chicken breast", "protein", 170, 32.0, 4.0, 0.0)
        );

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).update(captor.capture());
        assertEquals(170, captor.getValue().getCaloriesPer100g());
        assertEquals(170, response.caloriesPer100g());
    }
}
