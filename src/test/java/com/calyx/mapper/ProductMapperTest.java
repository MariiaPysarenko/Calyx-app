package com.calyx.mapper;

import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.ProductResponse;
import com.calyx.model.Product;
import com.calyx.testutil.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductMapperTest {

    @Test
    public void toEntity_defaultsCategoryToOther() {
        Product product = ProductMapper.toEntity(
                new ProductRequest("Milk", null, 50, 3.0, 1.0, 5.0)
        );

        assertEquals("other", product.getCategory());
    }

    @Test
    public void toEntity_normalizesCategory() {
        Product product = ProductMapper.toEntity(
                new ProductRequest("Apple", "Fruits", 52, 0.3, 0.2, 13.8)
        );

        assertEquals("fruits", product.getCategory());
    }

    @Test
    public void toResponse_mapsProduct() {
        ProductResponse response = ProductMapper.toResponse(TestData.sampleProduct());

        assertEquals("Chicken breast", response.name());
        assertEquals("protein", response.category());
        assertEquals(165, response.caloriesPer100g());
    }
}
