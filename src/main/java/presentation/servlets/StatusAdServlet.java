package presentation.servlets;

import models.Advertisement;
import services.StoreDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Меняет статус объявления.
 */
public class StatusAdServlet extends HttpServlet {

    private final StoreDB store = StoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(String.format("%s/index.html", req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean newStatus = !Boolean.parseBoolean(req.getParameter("status"));
        int advertisementId = Integer.parseInt(req.getParameter("adId"));

        Advertisement target = store.sessionFunc(store.getAdvertisementDAO().getPart(advertisementId));
        target.setAdStatus(newStatus);
        store.sessionFunc(store.getAdvertisementDAO().add(target));
        doGet(req, resp);
    }
}