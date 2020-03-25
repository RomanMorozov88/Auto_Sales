package services.daos.car_parts_dao;

import models.cars_parts.CarBody;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CarBodyDAO implements PartDAO<CarBody, String> {

    @Override
    public Consumer<Session> add(CarBody value) {
        return session -> {
            session.saveOrUpdate(value);
        };
    }

    @Override
    public Function<Session, Integer> delete(String value) {
        return session -> {
            Query query = session.createQuery("delete CarBody where partName =:id");
            query.setParameter("id", value);
            Integer result = query.executeUpdate();
            return result;
        };
    }

    @Override
    public Function<Session, CarBody> getPart(String value) {
        return session -> {
            CarBody result = session.get(CarBody.class, value);
            return result;
        };
    }

    @Override
    public Function<Session, List<CarBody>> getParts() {
        return session -> session.createQuery("from CarBody ").list();
    }
}
