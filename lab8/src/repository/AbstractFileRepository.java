package repository;

import domain.HasID;
import domain.validator.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepository<ID, E extends HasID<ID>> extends InMemoryRepository<ID,E>  {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator){
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    private void loadData(){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine()) != null){
                List<String> attr = Arrays.asList(linie.split("\\|"));
                E e=extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> attributes);

    public void clear(String fileName){
        try(PrintWriter w = new PrintWriter(fileName)){
            w.print("");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public E save(E entity){
        E e=super.save(entity);
        if(e == null)
            writeToFile(entity);
        return e;
    }

    @Override
    public E delete(ID id){
        E e = super.delete(id);
        if(e != null) {
            clear(fileName);
           // for(E entity:findAll())
             //   writeToFile(entity);
            findAll().forEach(entity->writeToFile(entity));
        }
        return e;
    }

    @Override
    public E update(E entity){
        E e = super.update(entity);
        if(e == null) {
            clear(fileName);
            //for(E en:findAll())
             //   writeToFile(en);
            findAll().forEach(en->writeToFile(en));
        }
        return e;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
            bw.write(entity.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<E> findAll(){
        return super.findAll();
    }
}

