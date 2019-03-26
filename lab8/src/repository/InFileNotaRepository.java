package repository;

import domain.Nota;
import domain.Student;
import domain.validator.Validator;

import java.util.List;

public class InFileNotaRepository extends AbstractFileRepository<String, Nota> {

    public InFileNotaRepository(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    public Nota extractEntity(List<String> attr) {
        String ids = attr.get(0).split("=")[1];
        String idt = attr.get(1).split("=")[1];
        String nota = attr.get(2).split("=")[1];
        String data = attr.get(3).split("=")[1];
        float notaint = Float.parseFloat(nota);
        int dataint = Integer.parseInt(data);
        Nota n = new Nota(ids,idt,notaint,dataint);
        return n;
    }
}
