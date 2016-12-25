//18.11.16 21:10 v1.0
package com.translit.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public final class RecordListSlav {
    public static boolean ForceCreateDictionaryOnlyOnce = true;
    
    ArrayList<Record> dictionary;
    
    RecordListSlav() {
        dictionary = new ArrayList<>();
        getRecords();
    }
    
//------------------------------------------------------------------------------------------------
    public void getRecords() {
        FileInputStream fis = null;
        InputStreamReader fisr = null;
        boolean areWeOnServer = false;
                
        try {            
            File servDictionary = new File("/var/lib/tomcat8/webapps/ROOT/Dictionary_sec-XVII.txt");
            File localDictionary = new File("/Users/admin/Documents/Java/NetBeans_Projects/FirstWebApp/Dictionary_sec-XVII.txt");
            
            if (servDictionary.exists()) {
                //System.out.println("1. if - serverDictionary"); тнн 
                areWeOnServer = true;
                fis = new FileInputStream("/var/lib/tomcat8/webapps/ROOT/Dictionary_sec-XVII.txt");
                fisr = new InputStreamReader(fis, "Unicode");
                
                try (BufferedReader reader = new BufferedReader(fisr)) {
                    String line = null;
                    
                    while ((line = reader.readLine()) != null) {
                        addRecord(line);
                    }
                }                
            } else if(localDictionary.exists()) {
                //System.out.println("2. if - localDictionary");
                fis = new FileInputStream("/Users/admin/Documents/Java/NetBeans_Projects/FirstWebApp/Dictionary_sec-XVII.txt");
                fisr = new InputStreamReader(fis, "Unicode");
                
                try (BufferedReader reader = new BufferedReader(fisr)) {
                    String line = null;
                    
                    while ((line = reader.readLine()) != null) {
                        addRecord(line);
                    }
                }     
            }else {
                System.err.println("getRecords Error - Dictionary file not found !!! - CREATING DEFAULT ONE INSTEAD");
                forceCreateDictionaryFile(areWeOnServer);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            try {
                String[] defaultRecords = new String[5];
                defaultRecords[0] = "χс/Hristos\n";
                defaultRecords[1] = "їс/Iisus\n";
                defaultRecords[2] = "аρам/Aram\n";
                defaultRecords[3] = "аχаζ/Ahaz\n";
                defaultRecords[4] = "авρаамь/Avraam\n";

                for (String rec : defaultRecords) {
                    addRecord(rec);
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        } finally {
            if(fis != null){
                try{
                    fis.close();
                } catch(IOException e) {}
            }
            if(fisr != null){
                try {
                    fisr.close();
                } catch (IOException ex) {}
            }
        }     
    }
    
//------------------------------------------------------------------------------------------------
    public void forceCreateDictionaryFile(boolean areWeOnServer) {
        File defaultDictionary = null;
        FileOutputStream fos = null;

        String defaultDictionaryText = "ρовоамь/Rovoam\n"
                + "χс/Hristos\n"
                + "аρам/Aram\n"
                + "аχаζ/Ahaz\n"
                + "авρаамь/Avraam\n"
                + "авиꙋдь/Aviud\n"
                + "авїа/Avia\n"
                + "азоρь/Azor\n"
                + "аким/Achim\n"
                + "акимь/Achim\n"
                + "амин/Amin\n"
                + "аминадав/Aminadav\n"
                + "амон/Amon\n"
                + "асса/Assa\n"
                + "воѡз/Vooz\n"
                + "давид/David\n"
                + "давидь/David\n"
                + "двд/David\n"
                + "дѣρефи/de-ar fi\n"
                + "дꙋмнеѕеꙋл/Dumnezeul\n"
                + "дꙋмнеѕъꙋ/Dumnezeu\n"
                + "еѵглїѧ/evangheliia\n"
                + "зоρовавел/Zorovavel\n"
                + "кїамѫ/cheamă\n"
                + "маρїеи/Mariei\n"
                + "маρїѧ/Maria\n"
                + "манасїѧ/Manasia\n"
                + "матѳан/Matfan\n"
                + "нассонь/Nasson\n"
                + "салаѳїил/Salafiil\n"
                + "тнн/AAConv_Slav_Test\n"
                + "фаρес/Fares\n"
                + "фаρесь/Fares\n"
                + "фост/fost\n"
                + "скρисъ/scris\n"
                + "жидовѣще/jidoveste\n"
                + "їс/Iisus\n"
                + "ѡвид/Ovid\n"
                + "ѡζїѧ/Ozia";

        
        try{
            if(areWeOnServer){
                defaultDictionary = new File("/var/lib/tomcat8/webapps/ROOT/Dictionary_sec-XVII.txt");
            } else {
                defaultDictionary = new File("/Users/admin/Documents/Java/NetBeans_Projects/FirstWebApp/Dictionary_sec-XVII.txt");
            }
            
            fos = new FileOutputStream(defaultDictionary);
            
            try (Writer wout = new OutputStreamWriter(fos, "Unicode")) {
                wout.write(defaultDictionaryText);
            }
            
            if(RecordList.ForceCreateDictionaryOnlyOnce){
                RecordList.ForceCreateDictionaryOnlyOnce = false;
                getRecords();
            }
            
        } catch(IOException e){
            e.printStackTrace(System.err);
            
        } finally {
            if(fos != null){
                try{
                    fos.close();
                } catch(IOException ex){}
            }
        }  
    }
    
//------------------------------------------------------------------------------------------------
    public int searchSlav(String findMe) {
        try {
            for (int i = 0; i < dictionary.size(); i++) {
                if (findMe.equals(dictionary.get(i).getRussian())) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return -1;
        }
        return -1;
    }
    
//------------------------------------------------------------------------------------------------
    public void setRecords() {
        StringBuilder sb = null;
        
        try {
            sb = new StringBuilder();
            
            for (Record rec : dictionary) {
                sb.append(rec.getRussian());
                sb.append("/");
                sb.append(rec.getRomanian());
                sb.append("\r\n");
            }
            
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("Dictionary_sec-XVII.txt");
                try (Writer out = new OutputStreamWriter(fos, "Unicode")) {
                    out.write(sb.toString());
                }
                
            } catch (IOException e) {
                e.printStackTrace(System.err);
            } finally {
                if(fos != null){
                    try{
                        fos.close();
                    } catch(IOException ex){}
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            if(sb != null){
                sb = null;
            }
        }
    }
    
//------------------------------------------------------------------------------------------------
    public void addRecord(String lineToParse) {
        try{
            String[] tokens = lineToParse.split("/");
            Record nextRecord = new Record(tokens[0], tokens[1]);
            dictionary.add(nextRecord);
            
        } catch(Exception e){
            e.printStackTrace(System.err);
        }
    }
    
//------------------------------------------------------------------------------------------------
    public void insertRecord(String lineToParse) {
        try {
            String[] tokens = lineToParse.split("/");
            Record nextRecord = new Record(tokens[0], tokens[1]);
            
            int i = 0;
            
            while (nextRecord.getRussian().compareTo(dictionary.get(i).getRussian()) > 0 && i < dictionary.size() - 1) {
                i++;
            }
            if (i < dictionary.size() - 1) {
                dictionary.add(i, nextRecord);
            } else {
                dictionary.add(nextRecord);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
//------------------------------------------------------------------------------------------------
    public void simpleSort() {
        try{
            Collections.sort(dictionary);
        } catch(Exception e){
            e.printStackTrace(System.err);
        }
    }
    
    public void comparatorSort() {
        RecordCompare recordCompare = new RecordCompare();
        Collections.sort(dictionary, recordCompare);
    }
    
    public void Print() {
        for (Record rec : dictionary) {
            rec.Print();
        }
    }
    
    
    public void deleteRecord(int position) {
        dictionary.remove(position);
        //dictionary.trimToSize();
    }
    
    public String getRussian(int i) {
        return dictionary.get(i).getRussian();
    }
    
    public String getRomanian(int i) {
        return dictionary.get(i).getRomanian();
    }
    
    public void setRomanian(int i, String s) {
        dictionary.get(i).setRomanian(s);
    }
}

class RecordCompareSlav implements Comparator <Record> {
    @Override
    public int compare(Record one, Record two)
    {
        return one.getRussian().compareTo(two.getRussian());
    }
}

class RecordSlav implements Comparable <Record> {
    
    String russian = "\0";
    String romanian = "\0";
    
    RecordSlav (String newrussian, String newromanian)
    {
        russian = newrussian;
        romanian = newromanian;
    }
    
    public void Print()
    {
        System.out.println(russian + " " + romanian);
    }
    
    public boolean free()
    {
        return "\0".equals(russian);
    }
    
    @Override
    public int compareTo(Record other)
    {
        return russian.compareTo(other.getRussian());
    }
    
    public String getRussian() {
        return russian;
    }
    
    public void setRussian(String russian) {
        this.russian = russian;
    }
    
    public String getRomanian() {
        return romanian;
    }
    
    public void setRomanian(String romanian) {
        this.romanian = romanian;
    }
}
