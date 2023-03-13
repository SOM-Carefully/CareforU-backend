package com.example.carefully.global.error.filter;

import com.example.carefully.global.security.jwt.exception.TokenBlacklistException;
import com.example.carefully.global.security.jwt.exception.TokenEmptyException;
import com.example.carefully.global.security.jwt.exception.TokenExpiredException;
import com.example.carefully.global.security.jwt.exception.TokenValidFailException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class BaseExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            filterChain.doFilter(request, response);
        } catch (TokenEmptyException | TokenBlacklistException | TokenValidFailException | TokenExpiredException e) {
            log.error(e.getMessage(), e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(convertObjectToJson(e.getErrorCode()));
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}
