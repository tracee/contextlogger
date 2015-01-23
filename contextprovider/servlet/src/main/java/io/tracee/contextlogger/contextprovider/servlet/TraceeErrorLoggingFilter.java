package io.tracee.contextlogger.contextprovider.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.api.ImplicitContext;
import io.tracee.contextlogger.api.internal.MessageLogLevel;

/**
 * Servlet filter to detect uncaught exceptions and to provide some contextual error logs.
 * Created by Tobias Gindler, holisticon AG on 11.12.13.
 */
public class TraceeErrorLoggingFilter implements Filter {

    @Override
    public final void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e) {
            if (servletRequest instanceof HttpServletRequest) {
                handleHttpServletRequest((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse, e);
            }

            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            else if (e instanceof ServletException) {
                throw (ServletException)e;
            }
            else if (e instanceof IOException) {
                throw (IOException)e;
            }
        }
    }

    private void handleHttpServletRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Exception e) {

        TraceeContextLogger.createDefault().logJsonWithPrefixedMessage(
                MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, TraceeErrorLoggingFilter.class), ImplicitContext.COMMON,
                ImplicitContext.TRACEE, servletRequest, servletResponse, servletRequest.getSession(false), e);

    }

    @Override
    public final void destroy() {

    }
}
