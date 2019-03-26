package domain.validator;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {

    @Override
    public void validate(Tema entity){
        int d=Integer.parseInt(entity.getDeadline());
        int r=Integer.parseInt(entity.getReceive());
    String err="";
        if(entity.getID()==null)
            err+="Id este vid!\n";
        if(entity.getDescriere()==null)
            err+="Descrierea este vida!\n";
        if(entity.getDeadline()==null)
            err+="Deadline-ul este vid!\n";
        if(entity.getReceive()==null)
            err+="Receiv-ul este vid!\n";
        if(!entity.getID().matches("[0-9]+"))
            err+="Id invalid, trebuie sa fie format doar din cifre!\n";
        if(!entity.getDescriere().matches("[a-zA-Z ]+"))
            err+="Descriere invalida, trebuie sa fie formata doar din litere!\n";
        if(d>14 || d<1)
            err+="Deadline invalid, trebuie sa fie un numar cuprins intre 1 si 14\n";
        if(r>14 || r<1)
            err+="Receive invalid, trebuie sa fie un numar cuprins intre 1 si 14\n";
        if(r>d)
            err+="Deadline-ul trebuie sa fie mai mare decat data primirii\n";
        if(d-r>2)
            err+="Intre deadline si receive trebuie sa fie maximum doua saptamani\n";
    if(!err.equals(""))
        throw new ValidationException(err);
    }
}
