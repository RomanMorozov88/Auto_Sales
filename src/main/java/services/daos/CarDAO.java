package services.daos;

import models.Car;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.StoreDB;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class CarDAO {

    public Function<Session, List<Car>> getCarEngine(Car car) {
        return session -> {
            Query query = session.createQuery(
                    "from Car where engine =:eng"
            );
            query = query.setParameter("eng", car.getEngine());
            List<Car> result = query.list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Car buffer = (Car) it.next();
                Hibernate.initialize(buffer.getEngine());
                Hibernate.initialize(buffer.getCarBody());
                Hibernate.initialize(buffer.getTransmission());
            }
            return result;
        };
    }

    public Function<Session, List<Car>> getCarBody(Car car) {
        return session -> {
            Query query = session.createQuery(
                    "from Car where carBody =: cb"
            );
            query = query.setParameter("cb", car.getCarBody());
            List<Car> result = query.list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Car buffer = (Car) it.next();
                Hibernate.initialize(buffer.getEngine());
                Hibernate.initialize(buffer.getCarBody());
                Hibernate.initialize(buffer.getTransmission());
            }
            return result;
        };
    }

    public Function<Session, List<Car>> getCarTransmission(Car car) {
        return session -> {
            Query query = session.createQuery(
                    "from Car where transmission =: trns"
            );
            query = query.setParameter("trns", car.getTransmission());
            List<Car> result = query.list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Car buffer = (Car) it.next();
                Hibernate.initialize(buffer.getEngine());
                Hibernate.initialize(buffer.getCarBody());
                Hibernate.initialize(buffer.getTransmission());
            }
            return result;
        };
    }
}