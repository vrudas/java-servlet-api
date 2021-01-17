package io.servlet.examples.example_06_session_cookies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static io.servlet.examples.example_06_session_cookies.SessionAttributeConstants.USER_NAME_ATTRIBUTE;

@WebServlet(urlPatterns = "/session-read")
public class ReadSessionServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ReadSessionServlet.class);

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        try {
            HttpSession session = request.getSession(false);

            if (session == null) {
                writeNullSessionMessageIntoResponse(response);
                return;
            }

            String sessionId = session.getId();
            String userName = (String) session.getAttribute(USER_NAME_ATTRIBUTE);

            try (var writer = response.getWriter()) {
                writer.println(
                    "User name is: '" + userName
                        + "' for session with id: " + sessionId
                );
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void writeNullSessionMessageIntoResponse(
        HttpServletResponse response
    ) throws IOException {
        logger.warn("Session is null");

        try (var writer = response.getWriter()) {
            writer.println("Can't read attribute from 'null' session");
        }
    }
}
