package services.daos;

import models.Advertisement;
import models.Owner;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.EnlargedPartDAO;
import services.daos.basic_intefaces.PartDAO;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AdvertisementDAO
        implements EnlargedPartDAO<Advertisement, Integer>, PartDAO<Advertisement, Integer> {

    @Override
    public Consumer<Session> add(Advertisement value) {
        return session -> {
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
}