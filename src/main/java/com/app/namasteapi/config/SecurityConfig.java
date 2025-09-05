package com.app.namasteapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityConfig extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, IOException, ServletException {
        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.equals("Bearer demo-token")) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        chain.doFilter(req, res);
    }
}
