package io.servlet.examples.example_03_error_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/error-handler")
public class ErrorHandlerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerServlet.class);

    @Override
    protected void doGet(
        HttpServletRequest httpServletRequest,
        HttpServletResponse response
    ) {
        response.setContentType("text/html; charset=utf-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.write("<html><head><title>Error description</title></head><body>");
            writer.write("<h2>Error description</h2>");
            writer.write("<ul>");

            List.of(
                RequestDispatcher.ERROR_STATUS_CODE,
                RequestDispatcher.ERROR_EXCEPTION_TYPE,
                RequestDispatcher.ERROR_MESSAGE
            ).forEach(attributeName ->
                writer.write(
                    "<li>"
                        + attributeName + ": " + httpServletRequest.getAttribute(attributeName)
                        + " </li>"
                )
            );

            writer.write("</ul>");
            writer.write("</body></html>");

            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
