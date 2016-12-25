//15.11.16 15:15 v0.6
package com.io.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 *
 * @author Richard Strauss
 */
public class LoadSaveFiles {
    private String loadedText, saveText;
    private boolean translitSovietActualize, translitSovietDexStandard;
    
    
    public LoadSaveFiles(){
        this.translitSovietActualize = true;
        this.translitSovietDexStandard = true;
    }
    
    public LoadSaveFiles(boolean inActualize, boolean inDexStandard){
        this.translitSovietActualize = inActualize;
        this.translitSovietDexStandard = inDexStandard;
    }

    
//----------------------------------------------------------------------------------------------    
    public void LoadFile(File inFile) {
        try {
            if (inFile.exists() && !inFile.isDirectory()) {
                String filePath = inFile.getAbsolutePath().toLowerCase();

                if (filePath.toLowerCase().endsWith(".txt")) {
                    this.loadedText = loadPlainText(inFile.getAbsolutePath());
                } else {
                    ReadWriteOtherFormats rd = new ReadWriteOtherFormats();
                    
                    if (filePath.endsWith(".rtf")) {
                        loadedText = rd.loadRtf(inFile.getAbsolutePath());

                    } else if (filePath.endsWith(".docx") || filePath.endsWith(".doc")) {
                        loadedText = rd.loadDocDocx(inFile.getAbsolutePath());
                    } else {
                        loadedText = "File format exception!";
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
//----------------------------------------------------------------------------------------------    
    public void SaveFile(File inFile, String extension){
        try{
            extension = extension.toLowerCase();
            
            if(extension.equals(".txt")){
                savePlainText(inFile, saveText);
            } else {
                ReadWriteOtherFormats rwf = new ReadWriteOtherFormats();
                
                switch (extension) {
                    case ".docx":
                        rwf.saveDocxFile(inFile, saveText);
                        break;
                        
                    case ".doc":
                        rwf.saveDocFile(inFile, saveText);
                        break;
                        
                    case ".rtf":
                        rwf.saveRtfFile(inFile, saveText);
                        break;
                        
                    default:
                        System.out.println("Extension OUT OF BOUNDS: " + extension);
                        break;
                }
            }
        
        }catch(IOException ex){
            ex.printStackTrace(System.err);
        }
    
    }
    
//----------------------------------------------------------------------------------------------      
    public String loadPlainText(String FilePath) { 
        String plainText = "";
        FileInputStream fis = null;
        InputStreamReader fisr = null;
        
        try {
            fis = new FileInputStream(FilePath);
            fisr = new InputStreamReader(fis, "Unicode");
            
            try (BufferedReader reader = new BufferedReader(fisr)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    plainText+=line;
                    plainText+="\r\n"; //append newLine
                }
            }catch(Exception exc){
                exc.printStackTrace(System.err);
            }
            return plainText;
            
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (fisr != null) {
                try {
                    fisr.close();
                } catch (IOException e){}
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e){}
            }
            
        }
        return plainText;
    }
    
//----------------------------------------------------------------------------------------------------------------   
    public void savePlainText(File file, String outText) { 
        FileOutputStream fos = null;
        
        try {
            String FileName = file.getName().toLowerCase();

            if(FileName.endsWith(".txt")){
                fos = new FileOutputStream(file);
                try (Writer wout = new OutputStreamWriter(fos, "Unicode")) {
                    wout.write(outText);
                }
            } else {
                fos = new FileOutputStream(file + ".txt");
                try (Writer wout = new OutputStreamWriter(fos, "Unicode")) {
                    wout.write(outText);
                } 
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } finally {
            if(fos != null){
                try{
                    fos.close();
                }catch(Exception e){}
            }
        }
    }
    
//----------------------------------------------------------------------------------------------------------------  
    public void setSaveText(String inText){
        this.saveText = inText;
    }
    
    public String getLoadedText(){
        return this.loadedText;
    }
    
        
    public void setLSFPreferences(boolean inActualize, boolean inDexStandard){
        this.translitSovietActualize = inActualize;
        this.translitSovietDexStandard = inDexStandard;
    }
//----------------------------------------------------------------------------------------------------------------  
    
}
