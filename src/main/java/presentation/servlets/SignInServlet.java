package presentation.servlets;

import models.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.StoreDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Вход пользователя.
 */
public class SignInServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SignInServlet.class.getName());

    private final StoreDB store = StoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(String.format("%s/login.html", req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        int password = Integer.parseInt(req.getParameter("password"));
        Owner check = this.store.sessionFunc(store.getOwnerDAO().getPart(login));
        if (check != null && check.getPassword() == password) {
            HttpSession session = req.getSession();
            session.setAttribute("mainUser", check);
            resp.sendRedirect(String.format("%s/index.html", req.getContextPath()));
        } else {
            doGet(req, resp);
        }
    }
}