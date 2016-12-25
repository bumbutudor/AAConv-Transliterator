//10.11.16 16:39  v0.6
package com.io.src;

import com.rtfparserkit.converter.text.StringTextConverter;
import com.rtfparserkit.parser.RtfStreamSource;
import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfHeader.font;
import com.tutego.jrtf.RtfHeaderFont;
import static com.tutego.jrtf.RtfPara.p;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.control.Alert;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Richard Strauss
 */
public class ReadWriteOtherFormats {

    private boolean translitSovietActualize, translitSovietDexStandard;
    private int TranslitPeriod = 0;

    
    public ReadWriteOtherFormats(){
        this.translitSovietActualize = true;
        this.translitSovietDexStandard = true;
    }
    
    public ReadWriteOtherFormats(boolean inActualize, boolean inDexStandard){
        this.translitSovietActualize = inActualize;
        this.translitSovietDexStandard = inDexStandard;
    }
    
//----------------------------------------------------------------------------------------------------------------    
    public String loadDocDocx(String FilePath) { 
        FileInputStream fis = null; //for both DOC and DOCX
        XWPFDocument docx = null;  //for docx
        XWPFWordExtractor extractX = null;  //for docx
        WordExtractor extractor = null; //for doc
        String docText = "Doc or Docx couldn't load";
        
        try {
            if (FilePath.substring(FilePath.length() - 1).equals("x")) {
                
                fis = new FileInputStream(new File(FilePath));
                docx = new XWPFDocument(fis);
                extractX = new XWPFWordExtractor(docx);
                docText = extractX.getText();
                
            } else if (FilePath.substring(FilePath.length() - 3).equals("doc")) {
                
                fis = new FileInputStream(new File(FilePath));
                HWPFDocument doc = new HWPFDocument(fis);
                extractor = new WordExtractor(doc);
                docText = extractor.getText();
            }
            
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return docText;
        } finally {
            if (extractX != null) {
                try {
                    extractX.close();
                } catch (Exception e) {}
            }
            if (docx != null) {
                try {
                    docx.close();
                } catch (IOException e) {}
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {}
            }
            if (extractor != null) {
                try {
                    extractor.close();
                } catch (IOException e) {}
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {}
            }
        }
        return docText;
    }
 
//----------------------------------------------------------------------------------------------------------------    
    public String loadRtf(String FilePath) { 
        StringTextConverter tc = null;
        InputStream is = null;
        String rtfText = "RTF load Error";
        
        if (FilePath != null) {
            try {
                tc = new StringTextConverter();
                is = new FileInputStream(FilePath);
                tc.convert(new RtfStreamSource(is));
                
                rtfText = tc.getText();
                
            } catch (IOException e) {
                e.printStackTrace(System.err);
                return rtfText;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e){}
                }
                tc = null;
            }
        }
        return rtfText;
    }
    
//----------------------------------------------------------------------------------------------------------------    
    public void saveDocFile(File file, String text) {
        try {
            if (text != null) {
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph tmpParagraph = document.createParagraph();
                XWPFRun tmpRun = tmpParagraph.createRun();
                
                if (text.contains("\n")) {
                    String[] lines = text.split("\n");
                    tmpRun.setText(lines[0], 0); // set first line into XWPFRun
                    for(int i=1;i<lines.length;i++){
                        tmpRun.addBreak();  // add break and insert new text
                        tmpRun.setText(lines[i]);
                    }
                } else {
                    tmpRun.setText(text, 0);
                }
                tmpRun.setFontSize(12);
                tmpRun.setFontFamily("Times New Roman");
                
                try {
                    String FileName = file.getName().toLowerCase();
                    if(FileName.endsWith(".doc")){
                        document.write(new FileOutputStream(file));
                    } else {
                        document.write(new FileOutputStream(file + ".doc"));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                    
                }finally {
                    try{
                        document.close();
                    }catch(IOException e){}
                    
                    tmpParagraph = null;
                    tmpRun = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    
//---------------------------------------------------------------------------------------------------------------- 
    public void saveDocxFile(File file, String text) { 
        try {
            if (text != null) {
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph tmpParagraph = document.createParagraph();
                XWPFRun tmpRun = tmpParagraph.createRun();
                
                if (text.contains("\n")) {
                    String[] lines = text.split("\n");
                    tmpRun.setText(lines[0], 0); // set first line into XWPFRun
                    
                    for(int i=1; i<lines.length; i++){
                        tmpRun.addBreak();  // add break and insert new text
                        tmpRun.setText(lines[i]);
                    }
                } else {
                    tmpRun.setText(text, 0);
                }
                tmpRun.setFontSize(12);
                tmpRun.setFontFamily("Times New Roman");
                
                try {
                    String FileName = file.getName().toLowerCase();
                    if(FileName.endsWith(".docx")){
                        document.write(new FileOutputStream(file));
                    } else {
                        document.write(new FileOutputStream(file + ".docx"));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                    
                } finally {
                    try{
                        document.close();
                    }catch(IOException e){}
                    tmpParagraph = null;
                    tmpRun = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
 
//----------------------------------------------------------------------------------------------------------------    
    public void saveRtfFile(File file, String text) throws IOException {
        try {
            if (text != null) {
                String FileName = file.getName().toLowerCase();
                
                if(FileName.endsWith(".rtf")){  
                    rtf().header(font(RtfHeaderFont.WINDINGS).at(1)).section(
                            p(text)
                    ).out(new FileWriter(file)); 
                } else {                
                    rtf().header(font(RtfHeaderFont.WINDINGS).at(1)).section(
                            p(text)
                    ).out(new FileWriter(file + ".rtf"));
                }
            } 
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

    }

//---------------------------------------------------------------------------------------------------------------- 

    
    public void setRWFPreferences(boolean inActualize, boolean inDexStandard){
        this.translitSovietActualize = inActualize;
        this.translitSovietDexStandard = inDexStandard;
    }
    
    public void setRWFPeriod(int inPeriod){
        this.TranslitPeriod = inPeriod;
    }
    
//----------------------------------------------------------------------------------------------------------------     
    
    public void showErrorGUI(String errorMethodTitle, String errorContent){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorMethodTitle + " Error!");
        alert.setHeaderText("Some Error Occurred!");
        alert.setContentText(errorContent);

        alert.showAndWait();
    }
    
    public void showInfoGUI(String errorMethodTitle, String errorContent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorMethodTitle);
        alert.setHeaderText("Something is Wrong!");
        alert.setContentText(errorContent);

        alert.showAndWait();
    }
    
    
    
    
//----------------------------------------------------------------------------------------------------------------       
    public double startCount(){
        try {
            double start = System.currentTimeMillis();
            return start;
            
        } catch (Exception e){
            
            e.printStackTrace(System.err);
            return 1;
        }
    }
    
    
    public void endCount(double start){
        double end, dif;
        try{
            end = System.currentTimeMillis( );
            dif = end - start;
            dif = dif/1000;
            System.out.println("---------------------------------\nTime difference is : " + dif + " s.\n---------------------------------\n");
            
        } catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    
}
