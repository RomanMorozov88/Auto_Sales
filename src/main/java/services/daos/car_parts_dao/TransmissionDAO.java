package services.daos.car_parts_dao;

import models.cars_parts.Transmission;
import org.hibernate.Session;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Function;

public class TransmissionDAO implements PartDAO<Transmission, String> {

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
