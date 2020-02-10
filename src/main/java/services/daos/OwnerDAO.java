package services.daos;

import models.Owner;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.EnlargedPartDAO;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class OwnerDAO
        implements EnlargedPartDAO<Owner, String>, PartDAO<Owner, String> {

    @Override
    public Consumer<Session> add(Owner value) {
        return session -> {
            session.saveOrUpdate(value);
        };
    }

    @Override
    public Function<Session, Integer> delete(String value) {
        return session -> {
            Query query = session.createQuery("delete Owner where ownerName =:name");
            query.setParameter("name", value);
            Integer result = query.executeUpdate();
            return result;
        };
    }

    @Override
    public Function<Session, Owner> getPart(String value) {
        return session -> {
            Query query = session.createQuery("from Owner where ownerName =:name ");
            query.setParameter("name", value);
            Owner result = (Owner) query.uniqueResult();
            Hibernate.initialize(result.getRole());
            Hibernate.initialize(result.getAdvertisements());
            return result;
        };
    }

    @Override
    public Function<Session, List<Owner>> getParts() {
        return session -> session.createQuery("from Owner ").list();
    }

}