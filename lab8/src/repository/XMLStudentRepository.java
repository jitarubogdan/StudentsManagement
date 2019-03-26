package repository;

import domain.Student;
import domain.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class XMLStudentRepository extends XMLAbstractRepo<String, Student> {


    public XMLStudentRepository(String fileName,Validator<Student> validator){
        super(fileName,validator);
    }


    @Override
    public Element createElementFromEntity(Document document, Student s) {
        Element e = document.createElement("student");
        e.setAttribute("studentId",s.getID());

        Element name = document.createElement("name");
        name.setTextContent(s.getNume());
        e.appendChild(name);

        Element group = document.createElement("group");
        group.setTextContent(s.getGrupa());
        e.appendChild(group);

        Element email = document.createElement("email");
        email.setTextContent(s.getEmail());
        e.appendChild(email);

        return e;
    }

    @Override
    public Student createEntityFromElement(Element studentElement) {
        String studentId = studentElement.getAttribute("studentId");
        NodeList nods = studentElement.getChildNodes();
        String name = studentElement.getElementsByTagName("name")
                .item(0)
                .getTextContent();
        String group = studentElement.getElementsByTagName("group")
                .item(0)
                .getTextContent();
        String email = studentElement.getElementsByTagName("email")
                .item(0)
                .getTextContent();
        return new Student(studentId,name,group,email);
    }
}