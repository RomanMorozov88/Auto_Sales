package presentation.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Смотрит, что бы зашедший в систему пользователь не перешёл на страницу входа\регистрации
 */
public class AuthFilterFirst implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthFilterFirst.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        if (
                (request.getRequestURI().contains("/login.html")
                        || request.getRequestURI().contains("/signup.html"))
                        && session.getAttribute("mainUser") != null
        ) {
            ((HttpServletResponse) resp).sendRedirect(String.format("%s/index.html", request.getContextPath()));
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}