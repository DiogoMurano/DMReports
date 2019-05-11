package dev.diogomurano.reports.api;

public interface DAO<T> {

    T get(String reference);

    boolean exists(String reference);

    void save(T t);

    void update(T t);

    void delete(T t);
}
