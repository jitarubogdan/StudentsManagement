package repository;

import domain.HasID;
import domain.validator.ValidationException;
import domain.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends HasID<ID>> implements CrudRepository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator){
        this.validator = validator;
        entities = new HashMap<ID,E>();
    }

    @Override
    public E findOne(ID id) {
        if(id == null)
            throw new IllegalArgumentException("Id must be not null!");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null!");
        validator.validate(entity);
        if(entities.get(entity.getID())!=null){
            throw new ValidationException("Exista deja!");
            //System.out.println("Exista deja!");
            //return entity;
        }
        entities.put(entity.getID(),entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(id == null)
            throw new IllegalArgumentException("Id must not be null!");
        if(entities.get(id)!=null){
            return entities.remove(id);
        }
        return null;
    }

    @Override
    public E update(E entity) {
        if(entity==null)
            throw new IllegalArgumentException("Entity must not be null!");
        validator.validate(entity);
        if(entities.get(entity.getID())!=null){
            entities.put(entity.getID(),entity);
            return null;
        }
        return entity;
    }
}
