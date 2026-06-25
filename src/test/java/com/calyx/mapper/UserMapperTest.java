package com.calyx.mapper;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;
import com.calyx.model.User;
import com.calyx.testutil.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    @Test
    public void toEntity_mapsRequestFields() {
        CreateUserRequest request = new CreateUserRequest(
                "Maria", "maria@calyx.local", "pass", 28, 65.0, 168, "lose_weight"
        );

        User user = UserMapper.toEntity(request, "hashed");

        assertEquals("Maria", user.getName());
        assertEquals("hashed", user.getPassword());
        assertEquals(65.0, user.getWeiqht(), 0.001);
    }

    @Test
    public void toResponse_mapsUser() {
        UserResponse response = UserMapper.toResponse(TestData.sampleUser());

        assertEquals("maria@calyx.local", response.email());
        assertEquals(28, response.age());
    }
}
