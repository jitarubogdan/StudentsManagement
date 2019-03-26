package repository;

import domain.HasID;
import domain.Student;
import domain.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public abstract class XMLAbstractRepo <ID, E extends HasID<ID>> extends InMemoryRepository<ID,E> {
    String fileName;

    public XMLAbstractRepo(String filename,Validator<E> validator) {
        super(validator);
        this.fileName = filename;
        loadData();
    }

    private void loadData() {
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0; i < children.getLength(); i++){
                Node entityElement = children.item(i);
                if(entityElement instanceof Element){
                    E entity = createEntityFromElement((Element)entityElement);
                    super.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract E createEntityFromElement(Element entityElement);

    public void writeToFile(){
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("list");
            document.appendChild(root);
            super.findAll().forEach(m->{
                Element e = createElementFromEntity(document, m);
                root.appendChild(e);
            });

            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();
            transformer.transform(new DOMSource(document),new StreamResult(fileName));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public abstract Element createElementFromEntity(Document document, E m);

    @Override
    public E save(E entity){
        E stuff = super.save(entity);
        if (stuff == null){
            writeToFile();
        }
        return stuff;
    }
}
