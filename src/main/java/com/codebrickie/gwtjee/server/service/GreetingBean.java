package com.codebrickie.gwtjee.server.service;

import javax.ejb.Stateless;
import jakarta.validation.constraints.NotNull;

import com.codebrickie.gwtjee.domain.Greeting;
import com.codebrickie.gwtjee.shared.FieldVerifier;

@Stateless
public class GreetingBean {

    public Greeting greet(@NotNull String input, @NotNull String userAgent, @NotNull String serverInfo) {
        if (!FieldVerifier.isValidName(input)) {
            throw new IllegalArgumentException("Name must be at least 4 characters long");
        }

        return new Greeting("""
                Hello, %s!<br><br>
                I am running %s.<br><br>
                It looks like you are using:<br>%s""".formatted(input, serverInfo, userAgent));
    }
}