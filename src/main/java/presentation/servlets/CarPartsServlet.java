package presentation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;
import models.cars_parts.GeneralPart;
import models.cars_parts.Transmission;
import services.StoreDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CarPartsServlet extends HttpServlet {

    private final StoreDB store = StoreDB.getInstance();

    /**
     * Возвращает существующие в БД детали
     * для создания нового объявления.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        List<Engine> engines = store.sessionFunc(store.getEngineDAO().getParts());
        List<CarBody> carBodies = store.sessionFunc(store.getCarBodyDAO().getParts());
        List<Transmission> transmissions = store.sessionFunc(store.getTransmissionDAO().getParts());

        Map<String, List<GeneralPart>> result = new HashMap<>();
        result.put("engines", this.convertList(engines));
        result.put("carBodies", this.convertList(carBodies));
        result.put("transmissions", this.convertList(transmissions));

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        writer.append(mapper.writeValueAsString(result));
        writer.flush();
    }

    /**
     * Избавляется от лишнего кода.
     * Перегоняет лист с конкретным типов в лист с общим типом-родителем
     * для помещения в результирующий Map<String, List<GeneralPart>> result.
     *
     * @param list
     * @param <T>
     * @return
     */
    private <T extends GeneralPart> List<GeneralPart> convertList(List<T> list) {
        List<GeneralPart> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }
}