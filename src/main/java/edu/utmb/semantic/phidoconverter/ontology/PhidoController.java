/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utmb.semantic.phidoconverter.ontology;

import edu.utmb.semantic.phidoconverter.model.DialogueModel;
import edu.utmb.semantic.phidoconverter.util.PhidoIRI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 *
 * @author mac
 */
public class PhidoController {
    
    static private PhidoController INSTANCE = null;
    
    private OWLOntologyManager manager = null;
    private OWLOntology ontology = null;
    private OWLDataFactory factory = null;
    
    private PhidoController(){
        
        manager = OWLManager.createOWLOntologyManager();
        
        
        URL phido_resource = this.getClass().getResource("/phido_v1.owl");
        IRI iri = IRI.create(phido_resource);
        
        try {
            ontology = manager.loadOntology(iri);
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(PhidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        factory = ontology.getOWLOntologyManager().getOWLDataFactory();
        
    }
    
    static public PhidoController getInstance(){
        
        if(INSTANCE == null){
            INSTANCE = new PhidoController();
        }
        
        return INSTANCE;
        
    }
    
    public void insertDialogueModels(List<DialogueModel> dml)
    {
        
        addUtteranceInstaces(dml);
        
        linkUtteranceInstances(dml);
    }
    
    private void addUtteranceInstaces(List<DialogueModel> dml){
        
        //create the data
        System.out.println("Adding instances....");
        for(DialogueModel dm :dml)
        {
            //System.out.println("\t Working on .." + dm.getUtterance());
            //check if the class exists
            //String classification = dm.getClassification();
            validateUtteranceClass(dm);
            
            //add Information
            
            //  create class
            OWLClass dm_class = factory.getOWLClass(PhidoIRI.phido_IRI_base + dm.getClassification());
            //  create labels
            OWLAnnotation label_class = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral(dm.getUtterance()));
            // create indvidiual
            OWLNamedIndividual i_utterance = factory.getOWLNamedIndividual(PhidoIRI.phido_IRI_base + dm.getUID());
            
            //attach individual to label
            OWLAnnotationAssertionAxiom individual_label = factory.getOWLAnnotationAssertionAxiom(i_utterance.getIRI(), label_class);
            
            manager.applyChange(new AddAxiom(ontology, individual_label));
            
            //attach individual to class
            OWLClassAssertionAxiom class_individual = factory.getOWLClassAssertionAxiom(dm_class, i_utterance);
            
            manager.applyChange(new AddAxiom(ontology, class_individual));
            
        }
    }
    
    private void linkUtteranceInstances(List <DialogueModel> dml)
    {
        //create the links
        System.out.println("Adding instances links...");
        for(DialogueModel dm: dml){
            System.out.println("\t Working on .." + dm.getUtterance());
            
            OWLNamedIndividual source = factory.getOWLNamedIndividual(PhidoIRI.phido_IRI_base + dm.getUID());
            
            OWLObjectProperty precedes = factory.getOWLObjectProperty(PhidoIRI.phido_IRI_precedes);
            
            for(String t_id: dm.getTargetIds()){
                System.out.println("\t\t target id: "+ t_id);
                
                OWLNamedIndividual target = factory.getOWLNamedIndividual(PhidoIRI.phido_IRI_base + t_id);
                OWLObjectPropertyAssertionAxiom connector = factory.getOWLObjectPropertyAssertionAxiom(precedes, source, target);
                
                manager.applyChange(new AddAxiom(ontology, connector));
                
            }
            
            
        }
        
    }
    
    private void validateUtteranceClass(DialogueModel dm){
        
        
        
        if(!ontology.containsClassInSignature(IRI.create(PhidoIRI.phido_IRI_base +dm.getClassification()))){
            //create class
            
            OWLClass new_class = factory.getOWLClass(IRI.create(PhidoIRI.phido_IRI_base + dm.getClassification()));
            
            //addsublass (use Searle and Patricipant and Utterance)
            //Searle
            for(String searle :dm.getSearleClasses()){
                OWLClass s_class = factory.getOWLClass(IRI.create(PhidoIRI.phido_IRI_base+searle));
                OWLSubClassOfAxiom subaxiom = factory.getOWLSubClassOfAxiom(new_class, s_class);
                ontology.add(subaxiom);
                
            }
            
            for(String utt:dm.geApptUtteranceClass()){
                OWLClass u_class = factory.getOWLClass(IRI.create(PhidoIRI.phido_IRI_base+utt));
                OWLSubClassOfAxiom subaxiom = factory.getOWLSubClassOfAxiom(new_class, u_class);
                ontology.add(subaxiom);
            }
            
        }
        else{
            
        }
        
        
    }
    
    
   
    
    public void saveOntologyModel(String fileName)
    {
        
        
        OWLDocumentFormat ontologyFormat = manager.getOntologyFormat(ontology);
        RDFXMLDocumentFormat rdfFormat = new RDFXMLDocumentFormat();
        
        rdfFormat.copyPrefixesFrom(ontologyFormat.asPrefixOWLDocumentFormat());
        
        try {
            manager.saveOntology(ontology, rdfFormat, new FileOutputStream(new File(fileName)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PhidoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(PhidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        
        
        
    }
    
}


