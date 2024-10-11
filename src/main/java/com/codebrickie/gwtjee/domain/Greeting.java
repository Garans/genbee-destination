package com.codebrickie.gwtjee.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public record Greeting(String greetingText) implements Serializable {

    public Greeting {
    }

    public void setGreetingText(String greetingText) {
        throw new UnsupportedOperationException("Cannot modify a record");
    }
}