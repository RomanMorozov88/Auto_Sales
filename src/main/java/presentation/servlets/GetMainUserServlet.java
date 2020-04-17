package presentation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import models.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Возвращает из сессии основные данные об авторизованном пользователе.
 * Так же, как и в AdsServlet к ObjectMapper`у
 * подключается модуль для работы с lazy-loading (что обёрнуты в Hibernate.initialize())
 */
public class GetMainUserServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(GetMainUserServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Owner result = null;
        HttpSession session = req.getSession();
        result = (Owner) session.getAttribute("mainUser");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        writer.append(mapper.writeValueAsString(result));
        writer.flush();
    }
}