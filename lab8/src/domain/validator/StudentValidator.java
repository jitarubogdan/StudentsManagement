package domain.validator;

import domain.Student;

public class StudentValidator implements Validator<Student>{

    @Override
    public void validate(Student entity){
        String err="";
        if(entity.getID()==null)
            err+="Id este vid!\n";
        if(entity.getNume()==null)
            err+="Numele este vid!\n";
        if(entity.getGrupa()==null)
            err+="Grupa este vida!\n";
        if(entity.getEmail()==null)
            err+="Email-ul este vid!\n";
        if(!entity.getID().matches("[1-9]+"))
            err+="Id invalid, trebuie sa fie format doar din cifre!\n";
        if(!entity.getNume().matches("[a-zA-Z ]+"))
            err+="Nume invalid, trebuie sa fie format doar din litere!\n";
        if(!entity.getGrupa().matches("[2][2][1-7]"))
            err+="Grupa invalida, trebuie sa fie un numar cuprins intre 221 si 227\n";
        if(!entity.getEmail().matches("[a-z]{2}ir[0-9]{4}@scs.ubbcluj.ro"))
            err+="Email invalid, trebuie sa apartina de scs.ubbcluj.ro\n";
        if(!err.equals(""))
            throw new ValidationException(err);
    }
}
