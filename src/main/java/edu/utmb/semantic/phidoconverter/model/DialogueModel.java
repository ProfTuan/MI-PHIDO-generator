/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utmb.semantic.phidoconverter.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author mac
 */
public class DialogueModel {

    @ExcelRow
    private int row;

    //UID
    @ExcelCell(0)
    private String UID = "";

    //Utterance string
    @ExcelCell(1)
    private String utteranceString;

    //Class
    @ExcelCell(2)
    private String classString;

    //TargetType
    @ExcelCell(5)
    private String targetType;

    //Target Number
    @ExcelCell(6)
    private List<String> targets;

    //Subclass (if new)
    @ExcelCell(7)
    private List<String> searleClasses;

    @ExcelCell(8)
    private List<String> appUtteranceClasses;
    
    //Speech Task
    //Target (Type + Number) = Set of ids
    private Set<String> targetIds = null;

//
    public DialogueModel() {

        targetIds = new HashSet<String>();
    }

    public DialogueModel(String uid, String utterance, String classification) {

        setUID(uid);
        setUtterance(utterance);
        setClassification(classification);
    }

    public void setUID(String uid) {
        this.UID = uid;
    }

    public String getUID() {
        return this.UID;
    }

    private void processTargetIds() {

        targetIds = new HashSet<String>();

        if (targets != null) {
            targets.forEach(s -> 
            {

                targetIds.add(targetType + "-" + s.toUpperCase());
            });
        }

    }

    public void setUtterance(String utterance) {
        this.utteranceString = utterance;
    }

    public String getUtterance() {
        return this.utteranceString;
    }
    
    private String cleanUpClassifcationString(String thing){

        if(thing.contains(" ")){
            thing = thing.replaceAll(" ", "_");
            
        }
        
        return thing;
    }

    public void setClassification(String classification) {
        
        if(classification.contains(" ")){
            classification = classification.replaceAll(" ", "_");
            System.out.println("replacement happened here");
        }
        
        this.classString = classification;
    }

    public String getClassification() {
        
        //this.replaceSpaceWithUnderscore(this.classString);
        
        return cleanUpClassifcationString(this.classString);
    }
    
    public List<String> getSearleClasses(){
        return cleanListString(this.searleClasses);
    }
    
    public List<String> geApptUtteranceClass(){
        return cleanListString(this.appUtteranceClasses);
    }
    
    private List<String> cleanListString(List<String> strings){
        
        if(strings !=null){
            strings.forEach(str->{
            if(str.contains(" ")){
                str = str.replaceAll(" ", "_");
            }
            
        });
            return strings;
        }
        else{
            return new ArrayList<String>();
        }
        
        
    }

    public Set<String> getTargetIds() {

        if (targetIds.isEmpty()) 
        {
            this.processTargetIds();
        }

        return targetIds;
    }

  

    public static void main(String[] args) {

    }
}
