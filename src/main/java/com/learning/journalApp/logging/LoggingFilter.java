package com.learning.journalApp.logging;

import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoggingFilter
//        extends OncePerRequestFilter
{
//
//    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
//
//    @Override
//    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
//                                    jakarta.servlet.FilterChain filterChain)
//            throws IOException, ServletException {
//
//        logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
//
//        // Proceed with the filter chain
//        filterChain.doFilter(request, response);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            logger.info("Authenticated User: {}", authentication.getName());
//        } else {
//            logger.warn("Unauthenticated access to: {}", request.getRequestURI());
//        }
//    }
}
