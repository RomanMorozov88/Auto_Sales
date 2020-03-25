package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import services.daos.AdvertisementDAO;
import services.daos.OwnerDAO;
import services.daos.RoleDAO;
import services.daos.car_parts_dao.CarBodyDAO;
import services.daos.car_parts_dao.EngineDAO;
import services.daos.car_parts_dao.TransmissionDAO;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Работа с БД. Содержит в себе необходимые ДАО, из которых можно получить нужные функции.
 * Получается большая цепочка вызовов
 * (например для получения списка объявлений нужно:
 * *object.sessionFunc(store.getAdvertisementDAO().getParts())* )
 */
public class StoreDB {

    private final static StoreDB INSTANCE = new StoreDB();
    private final static SessionFactory FACTORY = new Configuration()
            .configure()
            .buildSessionFactory();

    private final RoleDAO roleDAO = new RoleDAO();
    private final OwnerDAO ownerDAO = new OwnerDAO();
    private final AdvertisementDAO advertisementDAO = new AdvertisementDAO();
    private final EngineDAO engineDAO = new EngineDAO();
    private final CarBodyDAO carBodyDAO = new CarBodyDAO();
    private final TransmissionDAO transmissionDAO = new TransmissionDAO();

    private StoreDB() {
    }

    public static StoreDB getInstance() {
        return INSTANCE;
    }

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }

    public OwnerDAO getOwnerDAO() {
        return ownerDAO;
    }

    public AdvertisementDAO getAdvertisementDAO() {
        return advertisementDAO;
    }

    public EngineDAO getEngineDAO() {
        return engineDAO;
    }

    public CarBodyDAO getCarBodyDAO() {
        return carBodyDAO;
    }

    public TransmissionDAO getTransmissionDAO() {
        return transmissionDAO;
    }

    /**
     * Общие для всех БД-методов блоки.
     *
     * @param command
     * @param <T>
     * @return
     */
    public <T> T sessionFunc(final Function<Session, T> command) {
        final Session session = FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void sessionFunc(final Consumer<Session> command) {
        final Session session = FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            command.accept(session);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void close() {
        FACTORY.close();
    }

}
