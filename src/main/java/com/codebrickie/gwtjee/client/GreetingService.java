package com.codebrickie.gwtjee.client;

import com.codebrickie.gwtjee.domain.Greeting;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    Greeting greetServer(String name) throws IllegalArgumentException;
}