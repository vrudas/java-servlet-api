package io.servlet.examples.example_05_redirect_vs_forward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/favorite-game")
public class FavoriteGameServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteGameServlet.class);

    @Override
    protected void doGet(
        HttpServletRequest req,
        HttpServletResponse resp
    ) {
        try (var writer = resp.getWriter()) {
            writer.print("Favorite game is: " + req.getParameter("game"));
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
