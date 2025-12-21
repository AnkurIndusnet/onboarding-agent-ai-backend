package com.example.onboardingAgent.onboardingAgent.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        // ✅ SKIP AUTH ENDPOINTS
        if (request.getRequestURI().startsWith("/auth")) {
            chain.doFilter(req, res);
            return;
        }

        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            ((HttpServletResponse) res)
                    .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT");
            return;
        }

        try {
            Claims claims = jwtUtil.validate(auth.substring(7));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            chain.doFilter(req, res);

        } catch (Exception e) {
            ((HttpServletResponse) res)
                    .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
        }
    }
}
