package services.daos.car_parts_dao;

import models.cars_parts.Engine;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class EngineDAO implements PartDAO<Engine, String> {

    @Override
    public Consumer<Session> add(Engine value) {
        return session -> {
            session.saveOrUpdate(value);
        };
    }

    @Override
    public Function<Session, Integer> delete(String value) {
        return session -> {
            Query query = session.createQuery("delete Engine where partName =:id");
            query.setParameter("id", value);
            Integer result = query.executeUpdate();
            return result;
        };
    }

    @Override
    public Function<Session, Engine> getPart(String value) {
        return session -> {
            Engine result = session.get(Engine.class, value);
            return result;
        };
    }

    @Override
    public Function<Session, List<Engine>> getParts() {
        return session -> session.createQuery("from Engine ").list();
    }
}
