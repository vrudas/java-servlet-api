package io.servlet.examples.example_05_redirect_vs_forward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/redirect")
public class RedirectRequestServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RedirectRequestServlet.class);

    /**
     * Execute example 'GET http://localhost:8080/redirect?game=name'
     */
    @Override
    protected void doGet(
        HttpServletRequest req,
        HttpServletResponse resp
    ) {
        try {
            resp.sendRedirect(req.getContextPath() + "/favorite-game");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
