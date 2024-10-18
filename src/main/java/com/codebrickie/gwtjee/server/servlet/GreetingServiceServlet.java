package com.codebrickie.gwtjee.server.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;

import com.codebrickie.gwtjee.client.GreetingService;
import com.codebrickie.gwtjee.domain.Greeting;
import com.codebrickie.gwtjee.server.service.GreetingBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/GWTJEE/greet" })
public class GreetingServiceServlet extends RemoteServiceServlet implements GreetingService {

    @EJB
    private GreetingBean greetingBean;

    public Greeting greetServer(String input) throws IllegalArgumentException {
        var serverInfo = escapeHtml(getServletContext().getServerInfo());
        var userAgent = escapeHtml(getThreadLocalRequest().getHeader("User-Agent"));

        return greetingBean.greet(input, userAgent, serverInfo);
    }

    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}