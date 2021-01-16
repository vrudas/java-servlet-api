package io.servlet.examples.example_04_filter;

import io.servlet.examples.example_02_request_response.GamesPricesServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/games")
public class GamesPricesFilter extends GenericFilter {

    private static final Logger logger = LoggerFactory.getLogger(GamesPricesFilter.class);

    private static final String DELETE_METHOD = "DELETE";

    @Override
    public void doFilter(
        ServletRequest req,
        ServletResponse res,
        FilterChain chain
    ) throws IOException, ServletException {
        var request = (HttpServletRequest) req;
        var response = (HttpServletResponse) res;

        if (DELETE_METHOD.equals(request.getMethod())) {
            String authorization = Objects.requireNonNullElse(
                request.getHeader("Authorization"),
                ""
            );

            if (authorization.isBlank()) {
                response.reset();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Request not authorized");
                return;
            }

            if (!authorization.equalsIgnoreCase("admin")) {
                response.reset();
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Request authorized with not supported credentials"
                );
                return;
            }

            logger.info("Request for {} can be executed", DELETE_METHOD);
        } else {
            logger.info(
                "Request to {} is performed for not secure method",
                GamesPricesServlet.class.getSimpleName()
            );
        }

        chain.doFilter(req, res);
    }
}
