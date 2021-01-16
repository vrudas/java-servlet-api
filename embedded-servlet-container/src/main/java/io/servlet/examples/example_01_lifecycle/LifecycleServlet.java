package io.servlet.examples.example_01_lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Use init methods only for initialization of final/stateless fields
 * as Servlets are used in multi-thread environment and ignoring of this rule
 * may produce unexpected behaviour/errors.
 * For more info visit
 * <a href='https://www.baeldung.com/java-servlets-containers-intro#multithreading'>
 *     Introduction to Servlets and Servlet Containers</a>
 */
@WebServlet(
    name = "LifecycleServlet",
    urlPatterns = {"/lifecycle-servlet"},
    loadOnStartup = 1
)
public class LifecycleServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LifecycleServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("[START] - Init with ServletConfig");
    }

    @Override
    public void init() {
        logger.info("[START] - Init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("[START] - service method on path {}", req.getRequestURI());
        super.service(req, resp);
        logger.info("[END] - service method");
    }

    @Override
    public void destroy() {
        logger.info("[START] - destroy method");
        super.destroy();
        logger.info("[END] - destroy method");
    }
}
