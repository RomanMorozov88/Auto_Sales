package services.daos.car_parts_dao;

import models.cars_parts.Transmission;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransmissionDAO implements PartDAO<Transmission, String> {

    @Override
    public Consumer<Session> add(Transmission value) {
        return session -> {
            session.saveOrUpdate(value);
        };
    }

    @Override
    public Function<Session, Integer> delete(String value) {
        return session -> {
            Query query = session.createQuery("delete Transmission where partName =:id");
            query.setParameter("id", value);
            Integer result = query.executeUpdate();
            return result;
        };
    }

    @Override
    public Function<Session, Transmission> getPart(String value) {
        return session -> {
            Transmission result = session.get(Transmission.class, value);
            return result;
        };
    }

    @Override
    public Function<Session, List<Transmission>> getParts() {
        return session -> session.createQuery("from Transmission ").list();
    }
}
