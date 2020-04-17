package presentation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Owner;
import models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.StoreDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Регистрация нового пользователя.
 */
public class SignUpServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SignUpServlet.class.getName());

    private final StoreDB store = StoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Role> result = store.sessionFunc(store.getRoleDAO().getParts());
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        writer.append(mapper.writeValueAsString(result));
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        int password = Integer.parseInt(req.getParameter("password"));
        String roleName = req.getParameter("role");
        Role role = this.store.sessionFunc(store.getRoleDAO().getPart(roleName));

        Owner newUser = new Owner();
        newUser.setOwnerName(login);
        newUser.setPassword(password);
        newUser.setRole(role);
        this.store.sessionFunc(store.getOwnerDAO().add(newUser));

        HttpSession session = req.getSession();
        session.setAttribute("mainUser", newUser);
        resp.sendRedirect(String.format("%s/index.html", req.getContextPath()));
    }

}