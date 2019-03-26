package service;

import domain.Tema;
import domain.validator.TemaValidator;
import domain.validator.Validator;
import repository.CrudRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class TemaService {

    private CrudRepository<String, Tema> repoTema;

    public TemaService( CrudRepository<String, Tema> repoTema) {
        this.repoTema = repoTema;
    }

    /**
     *
     * @param id - id of object I am looking for
     * @return the object with Id = parameter id
     */
    public Tema findTema(String id){
        return repoTema.findOne(id);
    }

    /**
     *
     * @param t - the object that has to be added
     * @return null if t is added to list or t otherwise
     */
    public Tema addTema(Tema t){
        return repoTema.save(t);
    }

    /**
     *
     * @param t - the object whose deadline has to be updated
     * @return object updated
     */
    public Tema prelungireDeadline(Tema t){
        Calendar calendar=Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int facultyweek = week-39;
        int deadline = Integer.parseInt(t.getDeadline());
        if(facultyweek <= deadline) {
            deadline++;
            t.setDeadline(Integer.toString(deadline));
            repoTema.update(t);
            System.out.println("Deadline-ul a fost modificat");
        }
        return t;
    }

    /**
     *
     * @return list of homeworks
     */
    public ArrayList<Tema> allTeme(){
        //return repoTema.findAll();
        Iterable<Tema> i=repoTema.findAll();
        ArrayList<Tema> l=new ArrayList<>();
        i.forEach(t->l.add(t));
        return l;
    }
}
