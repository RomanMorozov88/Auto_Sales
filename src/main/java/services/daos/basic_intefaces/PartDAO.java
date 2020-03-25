package services.daos.basic_intefaces;

import org.hibernate.Session;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Интерфейс для
 * добавления в\удаления из БД;
 * получения из БД
 * одной/всех записей.
 * @param <T>
 * @param <V>
 */
public interface PartDAO<T, V> {

    Consumer<Session> add(T value);

    Function<Session, Integer> delete(V value);


    Function<Session, T> getPart(V value);

    Function<Session, List<T>> getParts();

}