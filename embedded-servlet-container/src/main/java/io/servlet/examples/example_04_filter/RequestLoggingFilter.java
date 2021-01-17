package io.servlet.examples.example_04_filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebFilter(urlPatterns = "/*")
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Init {}", getClass().getSimpleName());
    }

    @Override
    public void doFilter(
        ServletRequest req,
        ServletResponse res,
        FilterChain chain
    ) throws IOException, ServletException {
        var request = (HttpServletRequest) req;
        var response = (HttpServletResponse) res;

        logger.info("User IP address: {}", request.getRemoteAddr());
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request Params: {}", Collections.list(request.getParameterNames()));
        logger.info("");

        //Example with missing chain.doFilter(...) call
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Destroy {}", getClass().getSimpleName());
    }
}
