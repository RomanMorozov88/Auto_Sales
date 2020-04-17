package presentation.servlets;

import models.Advertisement;
import models.Car;
import models.Owner;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;
import models.cars_parts.Transmission;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CarGetter;
import services.Config;
import services.StoreDB;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Получает данные из соответствующей формы и записывает в БД новое объявление.
 * И, при необходимости, новый связанные с ним Car.
 */
public class CreateAdServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CreateAdServlet.class.getName());

    private final StoreDB store = StoreDB.getInstance();
    private final CarGetter carGetter = new CarGetter();
    private static final Config CONFIG = Config.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(String.format("%s/index.html", req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File(CONFIG.get("folderimg"));
            if (!folder.exists()) {
                folder.mkdir();
            }

            String shortDesc = null;
            String fullDesc = null;
            String engineName = null;
            String carbodyName = null;
            String transmissionName = null;
            String imgName = null;
            String bufferField = null;

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    imgName = item.getName();
                    if (!imgName.equals("")) {
                        File file = new File(folder + File.separator + imgName);
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                        }
                    }
                } else {
                    bufferField = item.getFieldName();
                    if (bufferField.equals("shortdesc")) {
                        shortDesc = item.getString();
                    } else if (bufferField.equals("fulldesc")) {
                        fullDesc = item.getString();
                    } else if (bufferField.equals("eng")) {
                        engineName = item.getString();
                    } else if (bufferField.equals("body")) {
                        carbodyName = item.getString();
                    } else if (bufferField.equals("trns")) {
                        transmissionName = item.getString();
                    }
                }
            }

            Car car = carGetter.fillTheCar(engineName, carbodyName, transmissionName);

            Advertisement advertisement = new Advertisement();
            advertisement.setAdCar(car);
            Owner mainUser = null;
            HttpSession session = req.getSession();
            mainUser = (Owner) session.getAttribute("mainUser");
            advertisement.setAdCreator(mainUser);
            advertisement.setAdShortName(shortDesc);
            advertisement.setAdDescription(fullDesc);
            advertisement.setAdPhoto(imgName);
            advertisement.setAdTime(LocalDateTime.now());
            advertisement.setAdStatus(true);

            store.sessionFunc(store.getAdvertisementDAO().add(advertisement));

            doGet(req, resp);
        } catch (FileUploadException e) {
            doGet(req, resp);
        }
    }
}