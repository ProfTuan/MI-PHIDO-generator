/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utmb.semantic.phidoconverter;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import edu.utmb.semantic.phidoconverter.model.DialogueModel;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author mac
 */
public class ImportDialogueModel {
    
    private static ImportDialogueModel INSTANCE = null;
    
    private ImportDialogueModel(){
        
    }
    
    public static ImportDialogueModel getInstance(){
        
        if(INSTANCE == null){
            INSTANCE = new ImportDialogueModel();
        }
        
        return INSTANCE;
        
    }
    
    public void getDataFromFileDefault(String file){
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .addListDelimiter(";")
                .sheetIndex(1)
                .build();
        
        InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(file);
        
        List<DialogueModel> fromExcel = Poiji.fromExcel(fileStream, PoijiExcelType.XLSX, DialogueModel.class, options);
        
        System.out.println(fromExcel.size());
    }
    
    public List<DialogueModel> getDataFromFile(String file, int sheetIndex){
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                .settings()
                .addListDelimiter(";")
                .sheetIndex(sheetIndex)
                .build();
        
        InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(file);
        
        List<DialogueModel> fromExcel = Poiji.fromExcel(fileStream, PoijiExcelType.XLSX, DialogueModel.class, options);
        
        return fromExcel;
    }
    
    public static void main(String[] args) {
        
       
        
        ImportDialogueModel idm = ImportDialogueModel.getInstance();
        idm.getDataFromFileDefault("dialogue-worksheet.xlsx");
    }
    
}
