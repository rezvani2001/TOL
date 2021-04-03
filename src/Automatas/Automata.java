package Automatas;

import Alphabets.AlphabetCase;
import States.StateCase;
import Transitions.TransitionCase;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Automata")
public class Automata {

    @XmlAttribute
    public String type;

    public AlphabetCase Alphabets;

    public StateCase States;

    public TransitionCase Transitions;
}
