package domain.validator;

import domain.Nota;

public class NotaValidator implements Validator<Nota>{

    @Override
    public void validate(Nota entity) {
        String err="";
        if(entity.getID()==null)
            err+="Id este vid!\n";
        if(entity.getIds()==null)
            err+="Id-ul studentului este vid!\n";
        if(entity.getIdt()==null)
            err+="Id-ul temei este vid!\n";
        if(entity.getNota()==null)
            err+="Nota este vida!\n";
        if(entity.getData()==null)
            err+="Data este vida!\n";
        if(!entity.getIds().matches("[1-9]+"))
            err+="Id invalid, trebuie sa fie format doar din cifre!\n";
        if(!entity.getIdt().matches("[1-9]+"))
            err+="Id invalid, trebuie sa fie format doar din cifre!\n";
        if(entity.getNota()<1 || entity.getNota()>10)
            err+="Nota trebuie sa fie cuprinsa intre 1 si 10!\n";
        if(entity.getData()<1 || entity.getData()>14)
            err+="Data trebuie sa fie un numar cuprins intre 1 si 14!\n";
        if(!err.equals(""))
            throw new ValidationException(err);
    }
}
