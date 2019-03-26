package domain;

import java.time.LocalDateTime;

public class Nota implements HasID<String>{
    private String id;
    private String ids;
    private String idt;
    private Float nota;
    private Integer data;


    public Nota(String ids, String idt, Float nota, Integer data) {
        this.id = ids+"_"+idt;
        this.ids = ids;
        this.idt = idt;
        this.nota = nota;
        this.data = data;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "student=" + ids + "|" + "tema=" + idt + "|" + "nota=" + nota + "|" + "data=" + data;
    }
}
