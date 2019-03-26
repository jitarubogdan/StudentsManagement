package repository;

import domain.Nota;
import domain.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLNotaRepository extends XMLAbstractRepo<String, Nota> {

    public XMLNotaRepository(String filename, Validator<Nota> validator) {
        super(filename, validator);
    }

    @Override
    public Nota createEntityFromElement(Element entityElement) {
        NodeList nods = entityElement.getChildNodes();
        String ids = entityElement.getElementsByTagName("student")
                .item(0)
                .getTextContent();
        String idt = entityElement.getElementsByTagName("tema")
                .item(0)
                .getTextContent();
        String nota = entityElement.getElementsByTagName("nota")
                .item(0)
                .getTextContent();
        String data = entityElement.getElementsByTagName("data")
                .item(0)
                .getTextContent();
        return new Nota(ids,idt,Float.parseFloat(nota),Integer.parseInt(data));
    }

    @Override
    public Element createElementFromEntity(Document document, Nota m) {
        Element e = document.createElement("nota");

        Element ids = document.createElement("student");
        ids.setTextContent(m.getIds());
        e.appendChild(ids);

        Element idt = document.createElement("tema");
        idt.setTextContent(m.getIdt());
        e.appendChild(idt);

        Element nota = document.createElement("nota");
        nota.setTextContent(m.getNota().toString());
        e.appendChild(nota);

        Element data = document.createElement("data");
        data.setTextContent(m.getData().toString());
        e.appendChild(data);

        return e;
    }
}
