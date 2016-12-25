//26.10.16 16:38 v0.1
package com.translit.src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Richard Strauss
 */
public class Translit {
    private String cyrillicText, latinText;
    private boolean translitSovietActualize, translitSovietDexStandard;
    
    public Translit(String inCyrillicText){
        this.cyrillicText = inCyrillicText;
    }
    
    public Translit(){
        this.cyrillicText = "";
        this.latinText = "";
    }
    
//------------------------------------------------------------------------------------------------     
    public void transliterate(int period){
        try{
            StringBuilder cyrillicSb = new StringBuilder();
            StringBuilder latinSb = new StringBuilder();

            if (period >= 0 && period <= 3) {

                switch (period) {
                    case 0: {
                        cyrillicSb.append(cyrillicText);

                        if (translitSovietActualize) {
                            sovietTranslitByWords(cyrillicSb, latinSb);
                        } else {
                            TranslitSoviet ts = new TranslitSoviet();
                            ts.setActualizaTextPreference(translitSovietActualize);
                            ts.setDexStandardTypePreference(translitSovietDexStandard);
                            ts.transliterateSoviet(cyrillicSb, latinSb);
                        }

                        this.latinText = latinSb.toString();
                    }
                    break;

                    case 1: {
                        TranslitTranz tz = new TranslitTranz();
                        cyrillicSb.append(cyrillicText);
                        tz.transliterateTranz(cyrillicSb, latinSb);
                        this.latinText = latinSb.toString();
                    }
                    break;

                    case 2: {
                        TranslitRom tr = new TranslitRom();
                        cyrillicSb.append(cyrillicText);
                        tr.transliterateRom(cyrillicSb, latinSb);
                        this.latinText = latinSb.toString();
                    }
                    break;

                    case 3: {
                        if (translitSovietActualize) {
                            slavTranslitByWords(this.cyrillicText, cyrillicSb, latinSb);  // χс
                        } else {
                            TranslitSlav tsv = new TranslitSlav();
                            cyrillicSb.append(cyrillicText);
                            tsv.transliterateSlav(cyrillicSb, latinSb);
                            this.latinText = latinSb.toString();
                        }
                        this.latinText = latinSb.toString();
                    }
                    break;

                    default:
                        System.err.println("Transliterate Period OUT OF BOUNDS: " + period);
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.latinText = "Transliterate Error !!! \n" + ex.toString();
        }
    }
    
    
//------------------------------------------------------------------------------------------------    
    public void sovietTranslitByWords(StringBuilder inSb, StringBuilder outSb){
        RecordList recordList = new RecordList();
        TranslitSoviet ts = new TranslitSoviet();
        ts.setActualizaTextPreference(translitSovietActualize);
        ts.setDexStandardTypePreference(translitSovietDexStandard);
                        
        inSb.append(" ");
        int i = 0;
        int position = -1;
        boolean capLetter, JChar;
        char firstChar;
        
        StringBuilder currentStr;

        try {
            int size = inSb.length();
            char currentChar = inSb.charAt(0);
            
            while (i < size - 1) {

                if ((currentChar < '\u0410' || currentChar > '\u044F') && (currentChar != 'Ӂ' && currentChar != 'ӂ')) // А - я
                {
                    if (currentChar == '\u0009') { //TAB
                        outSb.append(' ');
                    } else {
                        outSb.append(currentChar);
                    }

                } else {
                    currentStr = new StringBuilder();
                    try {
                        capLetter = false;
                        JChar = false;

                        while ((currentChar >= '\u0410' && currentChar <= '\u044F' && (i < size - 1))
                                || (currentChar == 'Ӂ' || currentChar == 'ӂ')) {
                            currentStr.append(currentChar);
                            currentChar = inSb.charAt(++i);
                        }

                        firstChar = currentStr.charAt(0);

                        if ((firstChar >= '\u0410' && firstChar <= '\u042F')) {
                            capLetter = true;
                            currentStr.setCharAt(0, (char) (firstChar += 32));
                        }
                        if (firstChar == 'Ӂ') {
                            JChar = true;
                            currentStr.setCharAt(0, (char) (firstChar += 1));
                        }

                        position = recordList.search(currentStr.toString().toLowerCase());

                        if (position < 0) {
                            if (capLetter) {
                                currentStr.setCharAt(0, (char) (firstChar -= 32));
                            }
                            if (JChar) {
                                currentStr.setCharAt(0, (char) (firstChar -= 1));
                            }

                            //----------------------------------------------------------
                            ts.transliterateSoviet(currentStr, outSb);
                            //----------------------------------------------------------
                        } else if (capLetter || JChar) {  

                            currentStr = new StringBuilder(recordList.getRomanian(position));
                            currentStr.setCharAt(0, (char) (currentStr.charAt(0) - 32));
                            outSb.append(currentStr);

                        } else {
                            outSb.append(recordList.getRomanian(position));
                        }

                        switch (currentChar) {
                            case '¬':
                                outSb.append('\u200b');
                                break;

                            case '\u0009':
                                outSb.append(' ');
                                break;

                            default:
                                outSb.append(currentChar);
                                break;
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    } finally {
                        currentStr.delete(0, currentStr.length());
                    }
                } // ELSE

                if (i != size - 1) {
                    currentChar = inSb.charAt(++i);
                }
            }// WHILE 

        } catch (Exception e) {
            e.printStackTrace(System.err);
            outSb.append("Soviet Transliteration Error.");
        } finally {
            currentStr = null;
        }
    }  
    
    
//------------------------------------------------------------------------------------------------    
    public void slavTranslitByWords(String inText, StringBuilder inSb, StringBuilder outSb){  
        RecordListSlav recordListSlav;
        TranslitSlav ts = new TranslitSlav();
        String[] SlavWords = null;
        String[] SlavWordsLatin = null;
        String text = null;

        try {
            recordListSlav = new RecordListSlav(); //Slav Dictionary
            recordListSlav.simpleSort();
            text = inText;
            int position = -1;

            SlavWords = inText.split("(?U)[^\\p{Alpha}0-9↑']+");
            SlavWords = removeDuplicate(SlavWords);

            SlavWordsLatin = new String[SlavWords.length];

            Arrays.sort(SlavWords, Comparator.comparing(String::length).reversed());

            for (int i = 0; i < SlavWords.length; i++) {
                inSb.append(SlavWords[i]);

                position = recordListSlav.searchSlav(SlavWords[i].toLowerCase());

                if (position < 0) {
                    ts.transliterateSlav(inSb, outSb);
                    SlavWordsLatin[i] = outSb.toString();
                } else {
                    SlavWordsLatin[i] = recordListSlav.getRomanian(position);
                    if (!(SlavWordsLatin[i].equals("Iisus") || SlavWordsLatin[i].equals("Hristos"))) {
                        for (int j = 0; j < SlavWords[i].length(); j++) {
                            if (Character.isUpperCase(SlavWords[i].charAt(j))) {
                                char[] wordArray = SlavWordsLatin[i].toCharArray();
                                wordArray[j] = Character.toUpperCase(wordArray[j]);
                                SlavWordsLatin[i] = new String(wordArray);
                            }
                        }
                    }
                }

                outSb.delete(0, outSb.length());
                inSb.delete(0, inSb.length());
            }

            for (int i = 0; i < SlavWords.length; i++) {
                text = text.replace(SlavWords[i], SlavWordsLatin[i]);
            }

            outSb.append(text);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            outSb.append("buildTextSb - case 4 - Smth's NOT WORKING !!!");
        } finally {
            SlavWords = null;
            SlavWordsLatin = null;
            text = null;
            recordListSlav = null;
        }
    }
    
//-----------------------------------------------------------------------    
    public static String[] removeDuplicate(String[] words) {
        Set<String> wordSet = new LinkedHashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }
        return wordSet.toArray(new String[wordSet.size()]);
    }
    
    
    
//-------------------------------------------------------------------------------------------- 
    public void setTranslitPreferences(boolean inActualize, boolean inDexStandard){
        this.translitSovietActualize = inActualize;
        this.translitSovietDexStandard = inDexStandard;
    }
    
    public void setCyrillicText(String inText){
        this.cyrillicText = inText;
    }

    public void setLatinText(String inText){
        this.latinText = inText;
    }
    
    public String getCyrillicText(){
        return this.cyrillicText;
    }
    
    public String getLatinText(){
        return this.latinText;
    }
      
}
