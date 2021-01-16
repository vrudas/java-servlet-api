package io.servlet.examples.example_00_hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    name = "HelloServlet",
    urlPatterns = {"/hello-servlet"}
)
public class HelloServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            writer.print("Welcome to Java EE World in embedded Tomcat from " + getClass().getSimpleName());
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
