package com.adidas.common.publicservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwaggerConfigTest {

    @Test
    public void testCustomOpenAPI() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openApi = config.customOpenAPI();

        assertNotNull(openApi);
        assertNotNull(openApi.getInfo());
        assertEquals("Public Service API", openApi.getInfo().getTitle());
        assertEquals("Public Service description", openApi.getInfo().getDescription());
        assertNotNull(openApi.getInfo().getContact());
        assertEquals("not_real@test.com", openApi.getInfo().getContact().getEmail());
        assertEquals("Public Service", openApi.getInfo().getContact().getName());

        assertNotNull(openApi.getComponents());
        assertNotNull(openApi.getComponents().getSecuritySchemes());
        assertTrue(openApi.getComponents().getSecuritySchemes().containsKey("BearerScheme"));
        assertNotNull(openApi.getComponents().getSecuritySchemes().get("BearerScheme"));
        assertEquals("BearerScheme", openApi.getComponents().getSecuritySchemes().get("BearerScheme").getName());
        assertEquals(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP,
                openApi.getComponents().getSecuritySchemes().get("BearerScheme").getType());
        assertEquals("Bearer", openApi.getComponents().getSecuritySchemes().get("BearerScheme").getScheme());

        assertNotNull(openApi.getSecurity());
        assertEquals(1, openApi.getSecurity().size());
        assertNotNull(openApi.getSecurity().get(0).get("BearerScheme"));
    }
}