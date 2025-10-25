package com.coolcoder.filter;

import com.coolcoder.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            // 1️⃣ Check if Authorization header exists and starts with "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7); // Remove "Bearer "

            // 2️⃣ Validate the token using JwtUtil
            if (!JwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 3️⃣ Extract username from token
            String username = JwtUtil.getUsername(token);

            // 4️⃣ Optionally, forward username to downstream services
            exchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-Username", username)
                            .build())
                    .build();

            return chain.filter(exchange);

        };
    }

    public static class Config {
        // Add configuration properties if needed in future
    }
}
