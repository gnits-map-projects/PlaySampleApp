package dao;


// DAO interface supporting basic CRUD operations
// All DAOs should implement this interface
public interface CrudDao<K, E> {

    void persist(E entity);

    E update(E entity);

    void deleteById(K id);

    E findById(K id);

}
