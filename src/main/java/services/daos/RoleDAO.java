package services.daos;

import models.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import services.daos.basic_intefaces.PartDAO;

import java.util.List;
import java.util.function.Function;

public class RoleDAO
        implements PartDAO<Role, String> {

    @Override
    public Function<Session, Role> getPart(String value) {
        return session -> {
            Query query = session.createQuery("from Role where name =:name ");
            query.setParameter("name", value);
            Role result = (Role) query.uniqueResult();
            return result;
        };
    }

    @Override
    public Function<Session, List<Role>> getParts() {
        return session -> session.createQuery("from Role ").list();
    }

}