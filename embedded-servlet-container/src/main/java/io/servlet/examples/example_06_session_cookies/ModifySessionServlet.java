package io.servlet.examples.example_06_session_cookies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static io.servlet.examples.example_06_session_cookies.SessionAttributeConstants.USER_NAME_ATTRIBUTE;

/**
 * GET http://localhost:8080/session-modify?userName=Mario
 * GET http://localhost:8080/session-read
 * DELETE http://localhost:8080/session-modify
 */
@WebServlet(urlPatterns = "/session-modify")
public class ModifySessionServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ModifySessionServlet.class);

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        try {
            String userName = Objects.requireNonNullElse(
                request.getParameter("userName"),
                ""
            );

            if (userName.isBlank()) {
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute(USER_NAME_ATTRIBUTE, userName);

            try (var writer = response.getWriter()) {
                String sessionId = session.getId();

                writer.println(
                    "User name '" + userName
                        + "' was set as attribute into session with id: "
                        + sessionId
                );
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doDelete(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String sessionId = session.getId();
            session.invalidate();

            try (var writer = response.getWriter()) {
                writer.println("Invalidated session with id: " + sessionId);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
