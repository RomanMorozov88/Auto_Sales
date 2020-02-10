package services.daos.basic_intefaces;

import org.hibernate.Session;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Интерфейс для добавления в\удаления из БД.
 * @param <T>
 * @param <V>
 */
public interface EnlargedPartDAO<T, V> {

    Consumer<Session> add(T value);

    Function<Session, Integer> delete(V value);

}