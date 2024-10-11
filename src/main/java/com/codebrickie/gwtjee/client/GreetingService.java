package com.codebrickie.gwtjee.client;

import com.codebrickie.gwtjee.domain.Greeting;
import jakarta.validation.constraints.NotNull;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    Greeting greetServer(@NotNull String name) throws IllegalArgumentException;
}