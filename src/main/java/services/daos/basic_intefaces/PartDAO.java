package services.daos.basic_intefaces;

import org.hibernate.Session;

import java.util.List;
import java.util.function.Function;

/**
 * Интерфейс для получения из БД
 * одной/всех записей.
 * @param <T>
 * @param <V>
 */
public interface PartDAO<T, V> {

    Function<Session, T> getPart(V value);

    Function<Session, List<T>> getParts();
}