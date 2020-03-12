package services.daos;

import models.Advertisement;
import models.Car;
import models.Owner;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;
import models.cars_parts.Transmission;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.EnlargedPartDAO;
import services.daos.basic_intefaces.PartDAO;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AdvertisementDAO
        implements EnlargedPartDAO<Advertisement, Integer>, PartDAO<Advertisement, Integer> {

    /**
     * Добавляем в БД новую заявку,
     * но перед этим проверяем- нет ли в таблице car
     * похожей по свойствам записи-
     * если есть- обновляем Car в новом объявлении.
     *
     * @param value
     * @return
     */
    @Override
    public Consumer<Session> add(Advertisement value) {
        return session -> {
            Car bufferCar = this.getCarFromDb(session, value.getAdCar());
            if (bufferCar != null) {
                value.setAdCar(bufferCar);
            }
            session.saveOrUpdate(value);
        };
    }

    @Override
    public Function<Session, Integer> delete(Integer value) {
        return session -> {
            Query query = session.createQuery("delete Advertisement where adId =:id");
            query.setParameter("id", value);
            Integer result = query.executeUpdate();
            return result;
        };
    }

    @Override
    public Function<Session, Advertisement> getPart(Integer value) {
        return session -> {
            Advertisement result = session.get(Advertisement.class, value);
            Hibernate.initialize(result.getAdCreator());
            Hibernate.initialize(result.getAdCreator().getRole());
            return result;
        };
    }

    @Override
    public Function<Session, List<Advertisement>> getParts() {
        return session -> {
            List<Advertisement> result = session.createQuery("from Advertisement").list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Advertisement buffer = (Advertisement) it.next();
                Hibernate.initialize(buffer.getAdCreator());
                Hibernate.initialize(buffer.getAdCreator().getRole());
            }
            return result;
        };
    }

    /**
     * Перегруженный метод для получения отфильтрованного списка.
     *
     * @param generalName
     * @param apartName
     * @param user
     * @return
     */
    public Function<Session, List<Advertisement>> getParts(String generalName, String apartName, Owner user) {
        return session -> {
            Query query = this.getStringCommand(session, generalName, apartName, user);
            List<Advertisement> result = query.list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Advertisement buffer = (Advertisement) it.next();
                Hibernate.initialize(buffer.getAdCreator());
                Hibernate.initialize(buffer.getAdCreator().getRole());
            }
            return result;
        };
    }

    /**
     * составной запрос для получения записи даже с учётом null.
     * Этот запрос нужен для поиска идентичной(кроме id) записи в таблице car.
     *
     * @param session
     * @param checkingCar
     * @return
     */
    private Car getCarFromDb(Session session, Car checkingCar) {
        Car result = null;
        String engCommand = "where ((:eng is null and engine is null) or engine = :eng)";
        String cbCommand = "and ((:cb is null and carBody is null) or carBody = :cb)";
        String trnsCommand = "and ((:trns is null and transmission is null) or transmission = :trns)";
        Query query = session.createQuery(
                "from Car " + engCommand + cbCommand + trnsCommand
        );
        query.setParameter("eng", checkingCar.getEngine());
        query.setParameter("cb", checkingCar.getCarBody());
        query.setParameter("trns", checkingCar.getTransmission());

        List<Car> list = query.list();
        if (list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }

    /**
     * Формирует Query из входящих данных для фильтра.
     *
     * @param session
     * @param generalName
     * @param apartName
     * @param user
     * @return
     */
    private Query getStringCommand(Session session, String generalName, String apartName, Owner user) {
        String command =
                "from Advertisement";
        String engCommand =
                " as adv where ((:eng is null and adv.adCar.engine is null) or adv.adCar.engine = :eng)";
        String cbCommand =
                " as adv where ((:cb is null and adv.adCar.carBody is null) or adv.adCar.carBody = :cb)";
        String trnsCommand =
                " as adv where ((:trns is null and adv.adCar.transmission is null) or adv.adCar.transmission = :trns)";
        String timeCommand =
                " as adv where adv.adTime between :startTime and :endTime";

        String userCommand = "((:user is null) or adCreator = :user)";
        String endCommand = " and ";

        Query queryResult = null;

        switch (generalName) {
            case ("engine"):
                Engine bufferEngine = new Engine();
                bufferEngine.setPartName(apartName);
                command += engCommand + endCommand + userCommand;
                queryResult = session.createQuery(command);
                queryResult.setParameter("eng", bufferEngine);
                break;
            case ("carbody"):
                CarBody bufferBody = new CarBody();
                bufferBody.setPartName(apartName);
                command += cbCommand + endCommand + userCommand;
                queryResult = session.createQuery(command);
                queryResult.setParameter("cb", bufferBody);
                break;
            case ("transmission"):
                Transmission bufferTransmission = new Transmission();
                bufferTransmission.setPartName(apartName);
                command += trnsCommand + endCommand + userCommand;
                queryResult = session.createQuery(command);
                queryResult.setParameter("trns", bufferTransmission);
                break;
            case ("period"):
                Integer period = Integer.parseInt(apartName);
                if (period != null && period > 0) {
                    LocalDateTime highBorder = LocalDateTime.now();
                    LocalDateTime lowBorder = highBorder.minusDays(period);
                    command += timeCommand + endCommand + userCommand;
                    queryResult = session.createQuery(command);
                    queryResult.setParameter("startTime", lowBorder);
                    queryResult.setParameter("endTime", highBorder);
                }
                break;
            default:
                endCommand = " where ";
                command += endCommand + userCommand;
                queryResult = session.createQuery(command);
                break;
        }
        queryResult.setParameter("user", user);
        return queryResult;
    }
}