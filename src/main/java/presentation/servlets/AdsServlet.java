package presentation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import models.Advertisement;
import models.Owner;
import services.CarGetter;
import services.StoreDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Возвращает лист объявлений.
 * Либо всех либо только аутентифицированного пользователя.
 * В зависимости от входящего значения flag.
 */
public class AdsServlet extends HttpServlet {

    private final StoreDB store = StoreDB.getInstance();
    private final CarGetter carGetter = new CarGetter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        boolean flag = Boolean.parseBoolean(req.getParameter("flag"));
        //Получаем информацию для фильтрации.
        String generalName = req.getParameter("general_otions");
        String apartName = req.getParameter("apart_otions");

        List<Advertisement> result = null;

        Owner mainUser = null;
        if (flag) {
            HttpSession session = req.getSession();
            mainUser = (Owner) session.getAttribute("mainUser");
        }
        if (flag && mainUser != null) {
            result = store.sessionFunc(store.getAdvertisementDAO().getParts(generalName, apartName, mainUser));
        } else {
            if (generalName != null && apartName != null) {
                result = store.sessionFunc(store.getAdvertisementDAO().getParts(generalName, apartName, mainUser));
            } else {
                result = store.sessionFunc(store.getAdvertisementDAO().getParts());
            }
        }

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        //Подключается модуль для работы с lazy-loading (что обёрнуты в Hibernate.initialize())
        mapper.registerModule(new Hibernate5Module());

        writer.append(mapper.writeValueAsString(result));
        writer.flush();
    }

    @Override
    public void destroy() {
        store.close();
        super.destroy();
    }
}