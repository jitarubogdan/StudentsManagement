package domain;

public class Tema implements HasID<String>{
    private String id;
    private String descriere;
    private String deadline;
    private String receive;

    public Tema(String id, String descriere, String deadline, String receive) {
        this.id = id;
        this.descriere = descriere;
        this.deadline = deadline;
        this.receive = receive;
    }

    @Override
    public String toString() {
        return "id=" + id + "|" + "desciere=" + descriere + "|" + "deadline=" + deadline + "|" + "receive=" + receive;
    }

    public void execute(){ }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
}
