//4.10.16 17:52  v0.4
package com.translit.src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Richard Strauss
 */
public class TranslitSoviet {
    
    private String pattern;
    private boolean DexStandardType = true; //TRUE - New Standard,  FALSE - Old Standard
    private boolean ActualizeTransText = true; // 'true' - translate with actualization,  'false' - w/o actualization
    
    
//-------------------------------------------------------------------------- Setters & Getters    
    public void setActualizaTextPreference(boolean inActualizeText) {
        this.ActualizeTransText = inActualizeText;
    }
    
    public void setDexStandardTypePreference(boolean inDexStandardType) {
        this.DexStandardType = inDexStandardType;
    }
    
    public void setPrefixes(String inPrefs) {  //11.2.16 setPattern Prefixes
        this.pattern = inPrefs;
    }


//------------------------------------------------------------------------------
    public void transliterateSoviet(StringBuilder incStr, StringBuilder out) {
        
        Pattern ptr;
        
        if (pattern != null) {
            ptr = Pattern.compile(pattern);
        } else {
            ptr = Pattern.compile("ынтр|суп|динтр|Ром|ром|суб|не");
            //System.err.println("Prefixes Pattern is Null. Using default pattern Instead.");
        }
        Matcher match = ptr.matcher(incStr);
        
        if (!ActualizeTransText) {
            //------------------------------------------------------------------------ ORIGINAL ORTOGRAPHY ------------------------------------------
            char tPrevChar, sPrevChar, prevChar;
            char currentChar;
            char nextChar, sNextChar, tNextChar;
            char prevTr;
            String S1 = "абвджӂийлмнопрстуфхцъьэзшеАБВДЕЖӁЗИЙЛМНОПРСТУФХЦШЪЬЭ\'\u2019\u0451\u0401";
            String S2 = "abvdjgiilmnoprstufhț\'iăzșeABVDEJGZIILMNOPRSTUFHȚȘ\'IĂ--eE";
            String S3;
            String S4;
            String S5 = "^$\u002c\u0020\u002e\u00ab\u00bb\u0009\u0028\u0029";
            
            for (int i = 0; i < incStr.length(); i++) {
                
                tPrevChar = (i > 2) ? incStr.charAt(i - 3) : '^';
                sPrevChar = (i > 1) ? incStr.charAt(i - 2) : '^';
                prevChar = (i > 0) ? incStr.charAt(i - 1) : '^';
                currentChar = incStr.charAt(i);
                nextChar = (i < incStr.length() - 1) ? incStr.charAt(i + 1) : '$';
                sNextChar = (i < incStr.length() - 2) ? incStr.charAt(i + 2) : '$';
                tNextChar = (i < incStr.length() - 3) ? incStr.charAt(i + 3) : '$';
                
                int p = S1.indexOf(currentChar);
                if (p != -1) {
                    out.append(S2.charAt(p));
                } else {
                    switch (currentChar) {
                            
                        case '\u00ac': //¬
                            break;
                            
                            //----------------------
                        case 'ы':
                            //if (S5.indexOf(sPrevChar) != -1 && (prevChar == 'с' || prevChar == 'С') && nextChar == 'н' && sNextChar == 'т') {
                            //    out.append('u');
                            //    break;
                            //}
                            
                            if((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')){
                                out.append('â');
                                break;
                            }
                            
                            out.append('î');
                            break;
                            //----------
                            
                        case 'Ы':
                            //if (S5.indexOf(sPrevChar) != -1 && (prevChar == 'с' || prevChar == 'С') && nextChar == 'н' && sNextChar == 'т') {
                            //    out.append('U');
                            //    break;
                            //}
                            
                            if((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')){
                                out.append('Â');
                                break;
                            }
                            
                            out.append('Î');
                            break;
                            //----------------------
                            
                        case 'г':
                            out.append('g');
                            S3 = "ёеияюьЁЕИЯЮЬ";
                            if (S3.indexOf(nextChar) != -1) {
                                out.append('h');
                            }
                            break;
                            //----------
                            
                        case 'Г':
                            out.append('G');
                            S3 = "ёеияюьЁЕИЯЮЬ";
                            if (S3.indexOf(nextChar) != -1) {
                                if (Character.isUpperCase(nextChar)) {
                                    out.append('H');
                                } else {
                                    out.append('h');
                                }
                            }
                            break;
                            //----------------------
                            
                        case 'к':
                            if ((nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar == 'О')) {
                                out.append('k');
                                break;
                            }
                            
                            S3 = "еиюяьЕИЮЯЬ";
                            S4 = "сзСЗ";
                            
                            if (S3.indexOf(nextChar) != -1) {
                                out.append('c');
                                out.append('h');
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('x');
                                i++;
                                break;
                            }
                            out.append('c');
                            break;
                            //----------
                            
                        case 'К':
                            if ((nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar == 'О')) {
                                out.append('K');
                                break;
                            }
                            
                            S3 = "еиюяьЕИЮЯЬ";
                            S4 = "сзСЗ";
                            
                            if (S3.indexOf(nextChar) != -1) {
                                out.append('C');
                                if (Character.isUpperCase(nextChar)) {
                                    out.append('H');
                                } else {
                                    out.append('h');
                                }
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('X');
                                i++;
                                break;
                            }
                            out.append('C');
                            break;
                            //----------------------
                            
                        case 'ч':
                            out.append('c');
                            S3 = "еиьяЕИЬЯ";
                            S4 = "аА";
                            if (S3.indexOf(nextChar) != -1) {
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('e');
                                break;
                            }
                            out.append('i');
                            break;
                            //----------
                            
                        case 'Ч':
                            out.append('C');
                            S4 = "аА";
                            S3 = "еиьяЕИЬЯ";
                            if (S3.indexOf(nextChar) != -1) {
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('E');
                                break;
                            }
                            out.append('I');
                            break;
                            //----------------------
                            
                        case 'щ':
                            out.append('c');
                            out.append('h');
                            break;
                            //----------
                            
                        case 'Щ':
                            out.append('C');
                            out.append('h');
                            break;
                            //----------------------
                            
                        case 'ю':
                            out.append('i');
                            out.append('u');
                            break;
                            //----------
                            
                        case 'Ю':
                            out.append('I');
                            out.append('u');
                            break;
                            //----------------------
                            
                        case 'я':
                            tPrevChar = Character.toLowerCase(tPrevChar);
                            sPrevChar = Character.toLowerCase(sPrevChar);
                            prevChar = Character.toLowerCase(prevChar);
                            nextChar = Character.toLowerCase(nextChar);
                            sNextChar = Character.toLowerCase(sNextChar);
                            
                            if (prevChar == 'и' || prevChar == 'И') {
                                out.append('a');
                                break;
                            }
                            
                            if(tPrevChar == 'а' && sPrevChar == 'р' && prevChar == 'и' && nextChar == 'т'){
                                out.append('a');
                                break;
                            }
                            
                            if ((tPrevChar == 'с' && sPrevChar == 'е' && prevChar == 'ц' && nextChar == '$')  //frumuseția вариятэ
                                ||
                                ((sPrevChar == 'а' || sPrevChar == 'А') && prevChar == 'в' && nextChar == '$') //avia
                                ||
                                (prevChar == 'ц' && nextChar == 'н') //cetățian
                                ||                                                                             //plasiază лумяскэ мулцимя
                                (sPrevChar == 'а' && prevChar == 'с' && nextChar == 'з')
                                ||
                                (sPrevChar == 'у' && prevChar == 'м' && nextChar == 'с')
                                ||
                                (tPrevChar == 'р' && sPrevChar == 'е' && prevChar == 'д')
                                ||
                                (tPrevChar == 'а' && sPrevChar == 'ч' && prevChar == 'е' && S5.indexOf(nextChar) != -1 )
                                ||
                                (sPrevChar == 'и' && prevChar == 'м' && S5.indexOf(nextChar) != -1)
                                )
                            {
                                out.append('e');
                                out.append('a');
                                break;
                            }
                            
                            
                            if( (S5.indexOf(sPrevChar) == -1 && S5.indexOf(prevChar) == -1) && (S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)){
                                out.append('e');
                                out.append('a');
                                
                                break;
                            }
                            
                            
                            if (nextChar == '$') {
                                S3 = "cdegjlmnrştţCDEGJLMNRŞTȚ";
                            } else {
                                S3 = "gjlnrştyGJLNRŞTY";
                            }
                            prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                            
                            
                            if ((prevTr != 'i' && prevTr != 'I') || nextChar != '$') {
                                if (S3.indexOf(prevTr) != -1) {
                                    if ( (prevTr == 'e' || prevTr == 'E')  ) {
                                        if (sPrevChar == 'д' || sPrevChar == 'ч' || sPrevChar == 'Д' || sPrevChar == 'Ч') {
                                            out.append('e');
                                        } else {
                                            out.append('i');
                                        }
                                        out.append('a');
                                        break;
                                    }
                                    out.append('e');
                                } else {
                                    out.append('i');
                                }
                            }
                            
                            out.append('a');
                            break;
                            //----------
                            
                        case 'Я':
                            
                            if (prevChar == 'и' || prevChar == 'И') {
                                out.append('A');
                                break;
                            }
                            
                            if(tPrevChar == 'а' && sPrevChar == 'р' && prevChar == 'и' && nextChar == 'т'){
                                out.append('A');
                                break;
                            }
                            
                            if ((tPrevChar == 'с' && sPrevChar == 'е' && prevChar == 'ц' && nextChar == '$')  //frumuseția вариятэ
                                ||
                                ((sPrevChar == 'а' || sPrevChar == 'А') && prevChar == 'в' && nextChar == '$') //avia
                                ||
                                (prevChar == 'ц' && nextChar == 'н') //cetățian
                                ||                                                                             //plasiază лумяскэ мулцимя
                                (sPrevChar == 'а' && prevChar == 'с' && nextChar == 'з')
                                ||
                                (sPrevChar == 'у' && prevChar == 'м' && nextChar == 'с')
                                ||
                                (tPrevChar == 'р' && sPrevChar == 'е' && prevChar == 'д')
                                ||
                                (tPrevChar == 'а' && sPrevChar == 'ч' && prevChar == 'е' && S5.indexOf(nextChar) != -1 )
                                ||
                                (sPrevChar == 'и' && prevChar == 'м' && S5.indexOf(nextChar) != -1)
                                )
                            {
                                out.append('e');
                                out.append('a');
                                break;
                            }
                            
                            
                            if( (S5.indexOf(sPrevChar) == -1 && S5.indexOf(prevChar) == -1) && (S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)){
                                out.append('e');
                                out.append('a');
                                break;
                            }
                            
                            
                            if (nextChar == '$') {
                                S3 = "cdegjlmnrştţCDEGJLMNRŞTȚ";
                            } else {
                                S3 = "gjlnrştyGJLNRŞTY";
                            }
                            
                            prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                            
                            
                            if ((prevTr != 'i' && prevTr != 'I') || nextChar != '$') {
                                if (S3.indexOf(prevTr) != -1) {
                                    if (prevTr == 'e' || prevTr == 'E') {
                                        if (sPrevChar == 'д' || sPrevChar == 'ч' || sPrevChar == 'Д' || sPrevChar == 'Ч') {
                                            out.append('E');
                                        } else {
                                            out.append('I');
                                        }
                                        
                                        if (Character.isUpperCase(prevTr) || Character.isUpperCase(nextChar)) {
                                            out.append('A');
                                        } else {
                                            out.append('a');
                                        }
                                        break;
                                    }
                                    
                                    out.append('E');
                                } else {
                                    out.append('I');
                                }
                            }
                            
                            if (Character.isUpperCase(prevTr) || Character.isUpperCase(nextChar)) {
                                out.append('A');
                            } else {
                                out.append('a');
                            }
                            break;
                            //----------------------
                            
                        default:
                            out.append(currentChar);
                    } // SWITCH
                } //if/ELSE
            } //FOR
            
            //------------------------------------------------------------------------ ACTUALIZE ORTOGRAPHY ------------------------------------------
        } else {
            //------------------------------------------------------------------------ ACTUALIZE ORTOGRAPHY ------------------------------------------
            
            char tPrevChar, sPrevChar, prevChar;
            char currentChar;
            char nextChar, sNextChar, tNextChar;
            char prevTr;
            String S1 = "абвджӂийлмнопрстуфхцъьэАБВДЖӁИЙЛМНОПРСТУФХЦЪЬЭ\'\u2019\u0451\u0401";
            String S2 = "abvdjgiilmnoprstufhț\'iăABVDJGIILMNOPRSTUFHȚ\'IĂ--eE";
            String S3;
            String S4;
            String S5 = "^$\u002c\u0020\u002e\u00ab\u00bb\u0009\u0028\u0029";
            
            for (int i = 0; i < incStr.length(); i++) {
                tPrevChar = (i > 2) ? incStr.charAt(i - 3) : '^';
                sPrevChar = (i > 1) ? incStr.charAt(i - 2) : '^';
                prevChar = (i > 0) ? incStr.charAt(i - 1) : '^';
                currentChar = incStr.charAt(i);
                nextChar = (i < incStr.length() - 1) ? incStr.charAt(i + 1) : '$';
                sNextChar = (i < incStr.length() - 2) ? incStr.charAt(i + 2) : '$';
                tNextChar = (i < incStr.length() - 3) ? incStr.charAt(i + 3) : '$';
                
                
                int p = S1.indexOf(currentChar);
                if (p != -1) {
                    out.append(S2.charAt(p));
                } else {
                    switch (currentChar) {
                            
                            //----------------------
                        case 'ы':
                            if (S5.indexOf(sPrevChar) != -1 && (prevChar == 'с' || prevChar == 'С') && nextChar == 'н' && sNextChar == 'т') { //сынт - sunt
                                out.append('u');
                                break;
                            }
                            
                            if((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')){
                                out.append('â');
                                break;
                            }
                            
                            if (DexStandardType) {  //----NEW STANDARD---------------------------------------------------
                                
                                if ((S5.indexOf(sPrevChar) != -1 && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && S5.indexOf(tNextChar) != -1))
                                    || (S5.indexOf(sPrevChar) != -1 && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                                    out.append('â');
                                    out.append('i');
                                    break;
                                }
                                    
                                if (S5.indexOf(prevChar) != -1 || S5.indexOf(nextChar) != -1 ||  match.find()) {
                                    out.append('î');
                                } else {
                                    out.append('â');
                                }
                                
                                break;

                                
        /*------*/          } else {  //---------OLD STANDARD --------------------------------------------------------------------

                                if ((S5.indexOf(sPrevChar) != -1 && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && S5.indexOf(tNextChar) != -1))  //1.2.16 пыне - pîine
                                    || (S5.indexOf(sPrevChar) != -1 && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                                    out.append('î');
                                    out.append('i');
                                    break;
                                }

                                out.append('î');
                            }
                            break;
                            
//----------
                        case 'Ы':
                            if (S5.indexOf(sPrevChar) != -1 && (prevChar == 'с' || prevChar == 'С') && nextChar == 'н' && sNextChar == 'т') { //сынт - sunt
                                out.append('U');
                                break;
                            }
                            if((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')){
                                out.append('Â');
                                break;
                            }
                            
                            if (DexStandardType) { //------NEW STANDARD -------------------------------------------
                                
                                if ((S5.indexOf(sPrevChar) != -1 && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && S5.indexOf(tNextChar) != -1))
                                    || (S5.indexOf(sPrevChar) != -1 && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                                    out.append('Â');
                                    out.append('i');
                                    break;
                                }
                                
                                if (S5.indexOf(prevChar) != -1 || S5.indexOf(nextChar) != -1 ||  match.find()) {
                                    out.append('Î');
                                } else {
                                    out.append('Â');
                                }
                                
                                break;
                                
                            } else {  //---------OLD STANDARD ------------------------------------------------------------

                                if ((S5.indexOf(sPrevChar) != -1 && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && S5.indexOf(tNextChar) != -1))  //1.2.16 пыне - pîine
                                    || (S5.indexOf(sPrevChar) != -1 && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                                    out.append('Î');
                                    out.append('i');
                                    break;
                                }
                                
                                out.append('Î');
                            }
                            break;
//----------------------
                        case 'г':
                            out.append('g');
                            S3 = "ёеияюьЁЕИЯЮЬ";
                            if (S3.indexOf(nextChar) != -1) {
                                out.append('h');
                            }
                            break;
                            
                            //----------
                        case 'Г':
                            out.append('G');
                            S3 = "ёеияюьЁЕИЯЮЬ";
                            if (S3.indexOf(nextChar) != -1) {
                                if (Character.isUpperCase(nextChar)) {
                                    out.append('H');
                                } else {
                                    out.append('h');
                                }
                            }
                            break;
                            //----------------------
                            
                        case 'к':
                            if ((nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar == 'О')) {
                                out.append('k');
                                break;
                            }
                            
                            S3 = "еиюяьЕИЮЯЬ";
                            S4 = "сзСЗ";
                            
                            if (S3.indexOf(nextChar) != -1) {
                                out.append('c');
                                out.append('h');
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('x');
                                i++;
                                break;
                            }
                            out.append('c');
                            break;
                            //----------
                            
                        case 'К':
                            if ((nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar == 'О')) {
                                out.append('K');
                                break;
                            }
                            
                            S3 = "еиюяьЕИЮЯЬ";
                            S4 = "сзСЗ";
                            
                            if (S3.indexOf(nextChar) != -1) {
                                out.append('C');
                                if (Character.isUpperCase(nextChar)) {
                                    out.append('H');
                                } else {
                                    out.append('h');
                                }
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('X');
                                i++;
                                break;
                            }
                            out.append('C');
                            break;
                            //----------------------
                            
                        case 'ч':
                            out.append('c');
                            S3 = "еиьяЕИЬЯ";
                            S4 = "аА";
                            if (S3.indexOf(nextChar) != -1) {
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('e');
                                break;
                            }
                            out.append('i');
                            break;
                            //----------
                            
                        case 'Ч':
                            out.append('C');
                            S4 = "аА";
                            S3 = "еиьяЕИЬЯ";
                            if (S3.indexOf(nextChar) != -1) {
                                break;
                            }
                            if (S4.indexOf(nextChar) != -1) {
                                out.append('E');
                                break;
                            }
                            out.append('I');
                            break;
                            //----------------------
                            
                        case 'ш':
                            if ((tPrevChar == 'о' && sPrevChar == 'т' && prevChar == 'у') && (nextChar == '$' || nextChar == '\u0020')) {
                                out.append('ș');
                                out.append('i');
                                break;
                            }
                            out.append('ș');
                            break;
                            //----------
                            
                        case 'Ш':
                            if ((tPrevChar == 'О' && sPrevChar == 'Т' && prevChar == 'У') && (nextChar == '$' || nextChar == '\u0020')) {  //31.3.16
                                out.append('Ș');
                                out.append('I');
                                break;
                            }
                            out.append('Ș');
                            break;
                            //----------------------
                            
                        case 'щ':
                            out.append('c');
                            out.append('h');
                            break;
                            //----------
                            
                        case 'Щ':
                            out.append('C');
                            out.append('h');
                            break;
                            //----------------------
                            
                        case 'ю':
                            out.append('i');
                            out.append('u');
                            break;
                            //----------
                            
                        case 'Ю':
                            out.append('I');
                            out.append('u');
                            break;
                            //----------------------
                            
                        case 'е':
                            if ((tPrevChar == 'к' && sPrevChar == 'р' && prevChar == 'е' && nextChar == 'р') //невое конституе Реешинд трэеск
                                ||
                                (sPrevChar == 'в' && prevChar == 'о' && S5.indexOf(nextChar) != -1)
                                ||
                                (tPrevChar == 'и' && sPrevChar == 'т' && prevChar == 'у' && S5.indexOf(nextChar) != -1)
                                ||
                                (prevChar == 'е' && S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)
                                ||
                                (prevChar == 'э' && nextChar == 'с')
                                ||
                                (prevChar == 'у' && S5.indexOf(nextChar) != -1)    
                                )
                            {
                                out.append('i');
                                out.append('e');
                                break;
                            }
                            
                            if ( (sPrevChar == '^') && ((prevChar == 'в' || prevChar == 'В') && (nextChar == 'ц' || nextChar == 'Ц')) ) {
                                out.append('i');
                            }
                            
                            out.append('e');
                            break;
                            //----------
                            
                        case 'Е':
                            if ((tPrevChar == 'к' && sPrevChar == 'р' && prevChar == 'е' && nextChar == 'р') //невое конституе Реешинд
                                ||
                                (sPrevChar == 'в' && prevChar == 'о' && S5.indexOf(nextChar) != -1)
                                ||
                                (tPrevChar == 'и' && sPrevChar == 'т' && prevChar == 'у' && S5.indexOf(nextChar) != -1)
                                ||
                                (prevChar == 'е' && S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)
                                ||
                                (prevChar == 'у' && S5.indexOf(nextChar) != -1)    
                                )
                            {
                                out.append('i');
                                out.append('e');
                                break;
                            }
                            
                            
                            if (sPrevChar == '^') {
                                if ((prevChar == 'в' || prevChar == 'В') && (nextChar == 'ц' || nextChar == 'Ц')) {
                                    out.append('I');
                                }
                            }
                            
                            out.append('E');
                            break;
                            //----------------------
                            
                        case 'я':
                            
                            if (prevChar == 'и' || prevChar == 'И') {
                                out.append('a');
                                break;
                            }
                            
                            if(tPrevChar == 'а' && sPrevChar == 'р' && prevChar == 'и' && nextChar == 'т'){
                                out.append('a');
                                break;
                            }
                            
                            if ((tPrevChar == 'с' && sPrevChar == 'е' && prevChar == 'ц' && nextChar == '$')  //frumuseția вариятэ
                                ||
                                ((sPrevChar == 'а' || sPrevChar == 'А') && prevChar == 'в' && nextChar == '$') //avia
                                ||
                                (prevChar == 'ц' && nextChar == 'н') //cetățian
                                ||                                                                             //plasiază лумяскэ мулцимя
                                (sPrevChar == 'а' && prevChar == 'с' && nextChar == 'з')
                                ||
                                (sPrevChar == 'у' && prevChar == 'м' && nextChar == 'с')
                                ||
                                (tPrevChar == 'р' && sPrevChar == 'е' && prevChar == 'д')
                                ||
                                (tPrevChar == 'а' && sPrevChar == 'ч' && prevChar == 'е' && S5.indexOf(nextChar) != -1 )
                                ||
                                (sPrevChar == 'и' && prevChar == 'м' && S5.indexOf(nextChar) != -1)
                                )
                            {
                                out.append('e');
                                out.append('a');
                                break;
                            }
                            
                            
                            if( (S5.indexOf(sPrevChar) == -1 && S5.indexOf(prevChar) == -1) && (S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)){
                                out.append('e');
                                out.append('a');
                                
                                break;
                            }
                            
                            
                            if (nextChar == '$') {
                                S3 = "cdegjlmnrştţCDEGJLMNRŞTȚ";
                            } else {
                                S3 = "gjlnrştyGJLNRŞTY";
                            }
                            prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                            
                            
                            if ((prevTr != 'i' && prevTr != 'I') || nextChar != '$') {
                                if (S3.indexOf(prevTr) != -1) {
                                    if ( (prevTr == 'e' || prevTr == 'E')  ) {
                                        if (sPrevChar == 'д' || sPrevChar == 'ч' || sPrevChar == 'Д' || sPrevChar == 'Ч') {
                                            out.append('e');
                                        } else {
                                            out.append('i');
                                        }
                                        out.append('a');
                                        break;
                                    }
                                    out.append('e');
                                } else {
                                    out.append('i');
                                }
                            }
                            
                            out.append('a');
                            break;
                            //----------
                            
                        case 'Я':
                            
                            if (prevChar == 'и' || prevChar == 'И') {
                                out.append('A');
                                break;
                            }
                            
                            if(tPrevChar == 'а' && sPrevChar == 'р' && prevChar == 'и' && nextChar == 'т'){
                                out.append('A');
                                break;
                            }
                            
                            if ((tPrevChar == 'с' && sPrevChar == 'е' && prevChar == 'ц' && nextChar == '$')  //frumuseția вариятэ
                                ||
                                ((sPrevChar == 'а' || sPrevChar == 'А') && prevChar == 'в' && nextChar == '$') //avia
                                ||
                                (prevChar == 'ц' && nextChar == 'н') //cetățian
                                ||                                                                             //plasiază лумяскэ мулцимя
                                (sPrevChar == 'а' && prevChar == 'с' && nextChar == 'з')
                                ||
                                (sPrevChar == 'у' && prevChar == 'м' && nextChar == 'с')
                                ||
                                (tPrevChar == 'р' && sPrevChar == 'е' && prevChar == 'д')
                                ||
                                (tPrevChar == 'а' && sPrevChar == 'ч' && prevChar == 'е' && S5.indexOf(nextChar) != -1 )
                                ||
                                (sPrevChar == 'и' && prevChar == 'м' && S5.indexOf(nextChar) != -1)
                                )
                            {
                                out.append('e');
                                out.append('a');
                                break;
                            }
                            
                            
                            if( (S5.indexOf(sPrevChar) == -1 && S5.indexOf(prevChar) == -1) && (S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)){
                                out.append('e');
                                out.append('a');
                                break;
                            }
                            
                            
                            if (nextChar == '$') {
                                S3 = "cdegjlmnrştţCDEGJLMNRŞTȚ";
                            } else {
                                S3 = "gjlnrştyGJLNRŞTY";
                            }
                            
                            prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                            
                            if (prevChar == 'и' || prevChar == 'И') {
                                out.append('A');
                                break;
                            }
                            
                            if ((prevTr != 'i' && prevTr != 'I') || nextChar != '$') {
                                if (S3.indexOf(prevTr) != -1) {
                                    if (prevTr == 'e' || prevTr == 'E') {
                                        if (sPrevChar == 'д' || sPrevChar == 'ч' || sPrevChar == 'Д' || sPrevChar == 'Ч') {
                                            out.append('E');
                                        } else {
                                            out.append('I');
                                        }
                                        
                                        if (Character.isUpperCase(prevTr) || Character.isUpperCase(nextChar)) {
                                            out.append('A');
                                        } else {
                                            out.append('a');
                                        }
                                        break;
                                    }
                                    
                                    out.append('E');
                                } else {
                                    out.append('I');
                                }
                            }
                            
                            if (Character.isUpperCase(prevTr) || Character.isUpperCase(nextChar)) {
                                out.append('A');
                            } else {
                                out.append('a');
                            }
                            break; 
                            //----------------------                             
                            
                        case 'з':
                            if ( (sPrevChar == 'л' || sPrevChar == 'н' || sPrevChar == 'Л' || sPrevChar == 'Н') && 
                                (prevChar == 'и' || prevChar == 'И') && (nextChar == 'м' || nextChar == 'М')) {
                                out.append('s');
                                break;
                            }
                            out.append('z');
                            break;
                            //----------
                            
                        case 'З':
                            if ( (sPrevChar == 'л' || sPrevChar == 'н' || sPrevChar == 'Л' || sPrevChar == 'Н') && 
                                (prevChar == 'и' || prevChar == 'И') && (nextChar == 'м' || nextChar == 'М')) {
                                out.append('S');
                                break;
                            }
                            out.append('Z');
                            break;
                            //----------------------
                            
                        default:
                            out.append(currentChar);
                    }
                }
                
            }//for 
            
            
        } // else ACTUALIZE
    } // end transliterate
} //end main class


//System.out.print( (S5.indexOf(prevChar) != -1) +" "+ (S5.indexOf(nextChar) != -1) +" "+  (match.find()) + ": ");
//System.out.println(tPrevChar + "" + sPrevChar + "" + prevChar + "" + currentChar + "" + nextChar + "" + sNextChar + "" + tNextChar + "  -  î");