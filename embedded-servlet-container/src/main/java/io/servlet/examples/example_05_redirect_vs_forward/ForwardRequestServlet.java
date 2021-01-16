package io.servlet.examples.example_05_redirect_vs_forward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/forward")
public class ForwardRequestServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ForwardRequestServlet.class);

    /**
     * Execute example 'GET http://localhost:8080/forward?game=name'
     */
    @Override
    protected void doGet(
        HttpServletRequest req,
        HttpServletResponse resp
    ) {
        try {
            RequestDispatcher requestDispatcher = getServletContext()
                .getRequestDispatcher("/favorite-game");

            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
