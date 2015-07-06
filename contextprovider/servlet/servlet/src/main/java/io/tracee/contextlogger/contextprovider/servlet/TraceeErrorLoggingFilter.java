package io.tracee.contextlogger.contextprovider.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.tracee.contextlogger.MessagePrefixProvider;
import io.tracee.contextlogger.TraceeContextLogger;
import io.tracee.contextlogger.contextprovider.api.ImplicitContext;
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
        catch (Throwable e) {

            if (servletRequest instanceof HttpServletRequest) {
                handleHttpServletRequest((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse, e);
            }

            // now rethrow error
            if (e instanceof IOException) {
                throw (IOException)e;
            }
            if (e instanceof ServletException) {
                throw (ServletException)e;
            }

            // wrap all other kinds of exceptions ...
            throw new ServletException(e);
        }
    }

    private void handleHttpServletRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Throwable e) {

        TraceeContextLogger
                .create()
                .enforceOrder()
                .apply()
                .logWithPrefixedMessage(MessagePrefixProvider.provideLogMessagePrefix(MessageLogLevel.ERROR, TraceeErrorLoggingFilter.class),
                        ImplicitContext.COMMON, ImplicitContext.TRACEE, servletRequest, servletResponse, servletRequest.getSession(false), e);

    }

    @Override
    public final void destroy() {

    }
}
