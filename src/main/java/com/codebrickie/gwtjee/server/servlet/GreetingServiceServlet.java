package com.codebrickie.gwtjee.server.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;

import com.codebrickie.gwtjee.client.GreetingService;
import com.codebrickie.gwtjee.domain.Greeting;
import com.codebrickie.gwtjee.server.service.GreetingBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@WebServlet(urlPatterns = { "/GWTJEE/greet" })
public class GreetingServiceServlet extends RemoteServiceServlet implements GreetingService {

    @EJB
    private GreetingBean greetingBean;

    public Greeting greetServer(String input) throws IllegalArgumentException {
        String serverInfo = escapeHtml(getServletContext().getServerInfo());
        String userAgent = escapeHtml(getThreadLocalRequest().getHeader("User-Agent"));

        return greetingBean.greet(input, userAgent, serverInfo);
    }

    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}