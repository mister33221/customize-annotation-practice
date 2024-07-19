package com.kai.customize_annotation_practice.controller;

import com.kai.customize_annotation_practice.annotation.RequireJwt;
import com.kai.customize_annotation_practice.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "JWT Authentication API")
@AllArgsConstructor
public class ApiController {

    private JwtUtil jwtUtil;

    @Operation(summary = "Generate JWT token", description = "Generates a new JWT token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully generated token",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = {
                                    @ExampleObject(name = "example1", value = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzIxNDAyODkxLCJleHAiOjE3MjE0MDMxOTF9.Pf3yBoOQ2PRq20JcsOGAduMtf_SJJWzoS2Om8-3LsF9iEMqqedXHVPX_wOp1Z7uHesLx5U_mLXyil0u7WxaKig")
                            })),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/generate-token")
    public String generateToken() {
        return jwtUtil.generateToken("user123");
    }

    @Operation(summary = "Access protected resource", description = "Access a resource that requires JWT authentication")
    @SecurityRequirement(name = "bearer-jwt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully accessed the resource",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class),
                            examples = {
                                    @ExampleObject(name = "example1", value = "{\"key\":\"value\", \"key2\":\"value2\"}")
                            })),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{\"error\":\"Unauthorized\", \"message\":\"Invalid or missing token\"}")
                            })),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = "{\"error\":\"Internal Server Error\", \"message\":\"An unexpected error occurred\"}")
                            }))
    })
    @GetMapping("/protected-resource")
    @RequireJwt
    public String protectedResource() {
        return "This is a protected resource";
    }
}
