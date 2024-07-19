package com.kai.customize_annotation_practice.controller;

import com.kai.customize_annotation_practice.util.JwtUtil;
import com.kai.customize_annotation_practice.annotation.RequireJwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "JWT Authentication API")
public class ApiController {

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Generate JWT token", description = "Generates a new JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/generate-token")
    public String generateToken() {
        return jwtUtil.generateToken("user123");
    }

    @Operation(summary = "Access protected resource", description = "Access a resource that requires JWT authentication")
    @SecurityRequirement(name = "bearer-jwt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully accessed the resource"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/protected-resource")
    @RequireJwt
    public String protectedResource() {
        return "This is a protected resource";
    }
}
