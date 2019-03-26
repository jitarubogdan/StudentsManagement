package repository;

import domain.Tema;
import domain.validator.Validator;

import java.util.List;

public class InFileTemaRepository extends AbstractFileRepository<String, Tema> {

    public InFileTemaRepository(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    public Tema extractEntity(List<String> attr) {
        String id = attr.get(0).split("=")[1];
        String descriere = attr.get(1).split("=")[1];
        String deadline = attr.get(2).split("=")[1];
        String receive = attr.get(3).split("=")[1];
        Tema t = new Tema(id,descriere,deadline,receive);
        return t;
    }
}
