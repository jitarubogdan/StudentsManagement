package domain.validator;

import domain.HasID;

public interface Validator<E> {

    /**
     *
     * @param entity
     * @throws ValidationException
     */
    void validate(E entity);
}
