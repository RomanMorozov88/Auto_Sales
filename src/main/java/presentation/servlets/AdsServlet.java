package presentation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import models.Advertisement;
import models.Car;
import models.Owner;
import services.CarGetter;
import services.StoreDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        //Получаем Car для фильтрации.
        String engineName = req.getParameter("eng");
        String carbodyName = req.getParameter("body");
        String transmissionName = req.getParameter("trns");
        Integer period = Integer.parseInt(req.getParameter("period"));

        Car filterCar = carGetter.fillTheCar(engineName, carbodyName, transmissionName);

        List<Advertisement> result = null;
        List<Advertisement> buffer = null;

        Owner mainUser;
        HttpSession session = req.getSession();
        synchronized (session) {
            mainUser = (Owner) session.getAttribute("mainUser");
        }

        if (flag && mainUser != null) {
            buffer = new ArrayList<>(mainUser.getAdvertisements());
        } else {
            buffer = store.sessionFunc(store.getAdvertisementDAO().getParts());
        }

        if (filterCar != null) {
            result = new ArrayList<>();
            for (Advertisement ad : buffer) {
                if (filterCar.carsAccord(ad.getAdCar())) {
                    result.add(ad);
                }
            }
        } else {
            result = buffer;
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