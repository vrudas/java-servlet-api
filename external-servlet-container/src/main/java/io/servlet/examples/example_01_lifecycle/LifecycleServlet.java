package io.servlet.examples.example_01_lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        logger.info("[START] - service method");
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
