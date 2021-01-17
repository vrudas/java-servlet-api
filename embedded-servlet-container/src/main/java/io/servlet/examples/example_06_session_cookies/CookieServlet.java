package io.servlet.examples.example_06_session_cookies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Request examples:
 * GET http://localhost:8080/cookies
 * POST http://localhost:8080/cookies
 * GET http://localhost:8080/hello-servlet // inspect availability ofo custom cookie
 * DELETE http://localhost:8080/cookies
 *
 * More info at <a href='https://www.baeldung.com/java-servlet-cookies-session'>Handling Cookies and a Session in a Java Servlet</a>
 */
@WebServlet(urlPatterns = "/cookies")
public class CookieServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CookieServlet.class);

    private static final String ALL_COOKIES_KEY = "ALL_COOKIES_KEY";

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String cookieKey = Objects.requireNonNullElse(
            request.getParameter("key"),
            ALL_COOKIES_KEY
        );

        try (var writer = response.getWriter()) {
            Stream<Cookie> cookiesStream = Arrays.stream(request.getCookies());

            if (ALL_COOKIES_KEY.equalsIgnoreCase(cookieKey)) {
                cookiesStream
                    .forEach(cookie -> writer.println(asKeyValueString(cookie)));
            } else {
                cookiesStream
                    .filter(cookie -> cookieKey.equalsIgnoreCase(cookie.getName()))
                    .forEach(cookie -> writer.println(asKeyValueString(cookie)));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String asKeyValueString(Cookie cookie) {
        return "key: '" + cookie.getName() + "', value: '" + cookie.getValue() + "'";
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Cookie uiThemeCookie = new Cookie("theme", "dark");

        int maxAgeInSeconds = 60 * 60;
        uiThemeCookie.setMaxAge(maxAgeInSeconds);

//        uiThemeCookie.setDomain("example.com"); // - as example it is important from Advertisement point

        response.addCookie(uiThemeCookie);
    }

    @Override
    protected void doDelete(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Cookie userNameCookieRemove = new Cookie("theme", "");
        userNameCookieRemove.setMaxAge(0);

        response.addCookie(userNameCookieRemove);
    }
}
