package services.daos.car_parts_dao;

import models.cars_parts.Engine;
import org.hibernate.Session;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Function;

public class EngineDAO implements PartDAO<Engine, String> {

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
