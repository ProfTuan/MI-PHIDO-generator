/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utmb.semantic.phidoconverter.util;

import org.semanticweb.owlapi.model.IRI;

/**
 *
 * @author mac
 */
public class PhidoIRI {
    
    static public IRI phido_IRI_base = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#");
    
    static public IRI phido_IRI_assertive = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#Assertives_Utterance");
    
    static public IRI phido_IRI_commissive = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#Commissive_Utterance");
    
    static public IRI phido_IRI_declarative = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#Declaration_Utterance");
    
    static public IRI phido_IRI_expressive = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#Expressive_Utterance");
    
    static public IRI phido_IRI_representative = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#Representative_Utterance");
    
    static public IRI phido_IRI_systemutterance = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#System_Utterance");
    
    static public IRI phido_IRI_participantutterance = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#Participant_Utterance");
    
    static public IRI phido_IRI_precedes = IRI.create("http://uth.tmc.edu/sbmi/ontology/phido#precedes");
    
}
