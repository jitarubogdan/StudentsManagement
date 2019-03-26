package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validator.ValidationException;
import repository.CrudRepository;
import utils.ChangeEventType;
import utils.GradeChangeEvent;
import utils.Observable;
import utils.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotaService implements Observable<GradeChangeEvent> {

    private CrudRepository<String, Nota> repoNota;

    public NotaService(CrudRepository<String, Nota> repoNota){
        this.repoNota=repoNota;
    }

    /**
     *
     * @param nota - the grade that has to be added
     * @param tema - the homework I'm giving a grade
     * @param scutire - the number of weeks when the student was exempt
     * @return the grade after applying penalties
     * @throws ValidationException if homework can not be given
     */
    public float calculeazaNota(Nota nota, Tema tema,float scutire){
        float deadline = Float.parseFloat(tema.getDeadline());
        float data = (float) nota.getData();
        float notaProvizorie = nota.getNota();
        float intarziere = deadline - data + scutire;
        if(intarziere  < -2)
            throw new ValidationException("Tema nu mai poate fi predata! Au trecut mai mult de doua saptamani!");
        else {
            if (intarziere < 0)
                notaProvizorie = notaProvizorie + (float) 2.5 * intarziere;
        }
        return notaProvizorie;
    }

    /**
     *
     * @param nota - the grade that has to be added
     * @param student - the student I'm giving a grade
     * @param tema - the homework I'm giving a grade
     * @param scutire - the number of weeks when the student was exempt
     * @param feedback - a feedback for student if he got a grade
     * @return the grade if it is added or null otherwise
     * If grade is added, it writes in a file some dates about student and homework
     */
    public Nota addNota(Nota nota, Student student, Tema tema, float scutire,String feedback){
        float notaProvizorie = calculeazaNota(nota, tema, scutire);
        nota.setNota(notaProvizorie);
        Nota Ok = repoNota.save(nota);
        if(Ok == null){
            notifyObservers(new GradeChangeEvent(ChangeEventType.ADD,Ok));
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/dataStudent/"+student.getNume()+".txt", true))){
                bw.write("Tema:"+tema.getID());
                bw.newLine();
                bw.write("Nota:"+nota.getNota());
                bw.newLine();
                bw.write("Predata in saptamana:"+nota.getData());
                bw.newLine();
                bw.write("Deadline:"+tema.getDeadline());
                bw.newLine();
                bw.write("Feedback:"+feedback);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Ok;
    }

    /**
     *
     * @return list og grades
     */
    public List<Nota> allNoteL(){
        Iterable<Nota> i=repoNota.findAll();
        List<Nota> l=new ArrayList<>();
        i.forEach(t->l.add(t));
        return l;
    }

    public Iterable<Nota> allNote(){
        //return repoTema.findAll();
        return repoNota.findAll();
    }

    private List<Observer<GradeChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<GradeChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<GradeChangeEvent> e) {

    }

    @Override
    public void notifyObservers(GradeChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
