package com.example.phonebook.configurations.websecurity;

import com.example.phonebook.logging.LogController;
import com.example.phonebook.responses.Validations;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, LogController {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Validations validations = new Validations();
        validations.addErrorDetails(new Validations.Exceptions(null, "Failed to authenticate", AuthenticationException.class.getSimpleName(), HttpStatus.UNAUTHORIZED));
        getLogger().warn("Tried to authenticate, failed");
        response.getOutputStream().println(new Gson().toJson(validations));
    }

    @Override
    public String getLoggerClassName() {
        return RestAuthenticationEntryPoint.class.getSimpleName();
    }


}