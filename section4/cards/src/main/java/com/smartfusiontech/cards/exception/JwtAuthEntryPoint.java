package com.smartfusiontech.cards.exception;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    String jsonResponse = String.format(
        "{\"status\": %d, \"error\": \"Unauthorized\", \"message\": \"Token is missing or invalid.\"}",
        HttpServletResponse.SC_UNAUTHORIZED
    );
    response.getWriter().write(jsonResponse);
  }
}
