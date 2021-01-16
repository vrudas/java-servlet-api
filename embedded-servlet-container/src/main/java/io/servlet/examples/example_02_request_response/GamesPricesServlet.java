package io.servlet.examples.example_02_request_response;

import io.servlet.examples.HttpErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebServlet(
    name = "GamesPricesServlet",
    urlPatterns = {"/games"}
)
public class GamesPricesServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GamesPricesServlet.class);

    private static final String GAME_NAME_PARAMETER = "game";
    private static final String PRICE_PARAMETER = "price";

    private static final String REQUIRED_GAME_PARAM_MESSAGE =
        "Required parameter '" + GAME_NAME_PARAMETER + "' is blank or missing";

    private GamesPricesStorage gamesPricesStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gamesPricesStorage = new GamesPricesStorage();
    }

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        String gameName = extractGameNameParameter(request);
        Integer gamePrice = gamesPricesStorage.read(gameName);

        if (gamePrice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (var writer = response.getWriter()) {
            writer.print(gamePrice);
            writer.flush();
        }

        response.setContentType("text/html");
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String gameName = extractGameNameParameter(request);
        int gamePrice = extractPriceParameter(request);

        gamesPricesStorage.create(gameName, gamePrice);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String gameName = extractGameNameParameter(request);
        int gamePrice = extractPriceParameter(request);

        gamesPricesStorage.update(gameName, gamePrice);

        response.setHeader("Game-Price-Status", "updated");
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String gameName = extractGameNameParameter(request);

        gamesPricesStorage.delete(gameName);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void service(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        try {
            super.service(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage(), e);
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                HttpErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE
            );
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                HttpErrorMessages.BAD_REQUEST_MESSAGE
            );
        }
    }

    private static String extractGameNameParameter(HttpServletRequest request) {
        String gameName = Objects.requireNonNullElse(
            request.getParameter(GAME_NAME_PARAMETER),
            ""
        );

        if (gameName.isBlank()) {
            throw new IllegalArgumentException(REQUIRED_GAME_PARAM_MESSAGE);
        }

        return gameName;
    }

    private static int extractPriceParameter(
        HttpServletRequest request
    ) {
        String parameterValue = request.getParameter(PRICE_PARAMETER);
        return Integer.parseInt(parameterValue);
    }
}
