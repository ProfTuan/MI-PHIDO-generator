package edu.utmb.semantic.phidoconverter;

import edu.utmb.semantic.phidoconverter.model.DialogueModel;
import edu.utmb.semantic.phidoconverter.ontology.PhidoController;
import java.util.List;

public class App {

    public static void main(String[] args) {

        

        for (int i = 1; i <= 14; i++) {
            System.out.println("Working on .." + i);
            ImportDialogueModel idm = ImportDialogueModel.getInstance();
            List<DialogueModel> dataFromFile = idm.getDataFromFile("dialogue-worksheet.xlsx", i);

            PhidoController pc = PhidoController.getInstance();

            pc.insertDialogueModels(dataFromFile);

            pc.saveOntologyModel("section" + i +".owl");
        }

    }
}
