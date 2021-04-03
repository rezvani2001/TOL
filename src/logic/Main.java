package logic;

import Automatas.*;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {
    public static Automata automata;
    public static void main(File file) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Automata.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            automata = (Automata) jaxbUnmarshaller.unmarshal(file);


            System.out.println("type " + automata.type);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}  