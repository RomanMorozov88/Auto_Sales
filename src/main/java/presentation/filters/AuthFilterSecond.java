package presentation.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Проверяет- не попытался ли гость зайти куда-нибудь кроме
 * разрешённых ему страниц.
 */
public class AuthFilterSecond implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        if (
                (
                        !request.getRequestURI().contains("/index.html")
                                || !request.getRequestURI().contains("/login.html")
                                || !request.getRequestURI().contains("/signup.html")
                )
                        && session.getAttribute("mainUser") == null
        ) {
            ((HttpServletResponse) resp).sendRedirect(String.format("%s/index.html", request.getContextPath()));
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}