package repository;

import domain.Tema;
import domain.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLTemaRepository extends XMLAbstractRepo<String, Tema>{


    public XMLTemaRepository(String filename, Validator<Tema> validator) {
        super(filename, validator);
    }

    @Override
    public Tema createEntityFromElement(Element entityElement) {
        String temaId = entityElement.getAttribute("temaId");
        NodeList nods = entityElement.getChildNodes();
        String desriere = entityElement.getElementsByTagName("descriere")
                .item(0)
                .getTextContent();
        String deadline = entityElement.getElementsByTagName("deadline")
                .item(0)
                .getTextContent();
        String receive = entityElement.getElementsByTagName("receive")
                .item(0)
                .getTextContent();
        return new Tema(temaId,desriere,deadline,receive);
    }

    @Override
    public Element createElementFromEntity(Document document, Tema m) {
        Element e = document.createElement("tema");
        e.setAttribute("temaId",m.getID());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(m.getDescriere());
        e.appendChild(descriere);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(m.getDeadline());
        e.appendChild(deadline);

        Element receive = document.createElement("receive");
        receive.setTextContent(m.getReceive());
        e.appendChild(receive);

        return e;
    }
}
