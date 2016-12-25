//12.9.16 16:13 v0.7
package com.translit.src;

/**
 *
 * @author Richard Strauss
 */
public class TranslitRom {
    
    public static String toUnicode(char ch) {
        return String.format("\\u%04x", (int) ch);
    }
    
    public void transliterateRom(StringBuilder incStr, StringBuilder out) {
        char tPrevChar, sPrevChar, prevChar;
        char currentChar;
        char nextChar, sNextChar, tNextChar;
        char prevTr;
        String S1 = "абвдезилмноупрстэ\ua64bфхцшй\u012CЪАБВДЕЗИЛМНОПРСТУ\uA64AФНЦШ\u2019\u0027\u0466\ua666\u0457\u0407"
        + "\u03c1\u03c7\u046f\u0461\u0460\u0479\u012D\u0439\u021d\u021c"; // Ĭ Ꙧ ї Ї ρ χ ѯ ѡ Ѡ ѹ ĭ й ȝ Ȝ
        String S2 = "abvdezilmnouprstăufhțşi\u012CĂABVDEZILMNOPRSTUUFHȚȘ--ÂMiIrhzoOuiizZ";  //-----------Romanian translit--------------
        String S3;
        String S4;
        String S5 = "^$\u002c\u0020\u002e\u00ab\u00bb\u0009\u0028\u0029";
        
        //for(int u=0; u<incStr.length(); u++){
            //System.out.println(u + ". " + toUnicode(incStr.charAt(u)) + "  -  " + incStr.charAt(u) );
        //}
        
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
                        
                        
                    case '\u03A1': // upper(ρ)
                        out.append('R');
                        break;
                    
                    case '\u0475': // ѵ
                        out.append('i');
                        break;
                        
                    case '\u0473': // ѳ
                        out.append('t');
                        break;
                        

                        
                    case '\u045F': // џ
                        S3 = "eіиї";
                        nextChar = Character.toLowerCase(nextChar);
                        
                        if(S3.indexOf(nextChar) != -1){
                            out.append('g');
                        } else {
                            out.append('j');
                        }
                        break;
                        
                    
                        
//------------------------
                    case '\u03d2': // ϒ
                        out.append('i');
                        break;
                        
                    case 'ь':
                    case 'Ь':
                        break;
                        
                        //----------------------
                    case 'к':
                        if ((nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar == 'О')) {
                            out.append('k');
                            break;
                        }
                        
                        S3 = "\u0457еиюяЕИЮЯ";
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
                        
                        S3 = "еиюяЕИЮЯ";
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
                        S3 = "ieеиьяIEЕИЬЯ\u0457";
                        S4 = "аА\u0462\u0463";
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
                        
                        S3 = "ieеиьяIEЕИЬЯ\u0457";
                        S4 = "аА\u0462\u0463";   //Ѣ Ѣ
                        if (S3.indexOf(nextChar) != -1) {
                            break;
                        }
                        if (S4.indexOf(nextChar) != -1) {
                            out.append('e');
                            break;
                        }
                        out.append('i');
                        break;
                        //----------------------
                        
                    case 'г':
                        out.append('g');
                        S3 = "\u0457ёеияюьЁЕИЯЮЬ"; // ї
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('h');
                        }
                        break;
                        //----------
                        
                    case 'Г':
                        out.append('G');
                        S3 = "\u0457ёеияюьЁЕИЯЮЬ"; // ї
                        if (S3.indexOf(nextChar) != -1) {
                            if (Character.isUpperCase(nextChar)) {
                                out.append('H');
                            } else {
                                out.append('h');
                            }
                        }
                        break;
                        //----------------------
                        
                    case 'ы':
                        if ((sPrevChar == '^' && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && tNextChar == '$')) || (sPrevChar == '^' && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                            out.append('î');
                            out.append('i');
                            break;
                        }
                        
                        if (S5.indexOf(prevChar) != -1 || S5.indexOf(nextChar) != -1) {
                            out.append('î');
                        } else if ((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')) {
                            out.append('â');
                        }
                        break;
                        //----------
                        
                    case 'Ы':
                        if ((sPrevChar == '^' && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && tNextChar == '$')) || (sPrevChar == '^' && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                            out.append('Î');
                            out.append('i');
                            break;
                        }
                        
                        if (S5.indexOf(prevChar) != -1 || S5.indexOf(nextChar) != -1) {
                            out.append('Î');
                        } else if ((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')) {
                            out.append('Â');
                        }
                        break;
                        
                        
//----------------------
                    case 'я':
                        if (prevChar == 'и' || prevChar == 'И' || prevChar == '\u0457') { //ї
                            //System.out.println("\'" + tPrevChar + "" + sPrevChar + "" + prevChar + "" + currentChar + "" + nextChar + "" + sNextChar + "" + tNextChar + "\'");
                            //System.out.println("\' " + toUnicode(tPrevChar) + " " + toUnicode(sPrevChar) + " " + toUnicode(prevChar) + " " + toUnicode(currentChar) + " " + toUnicode(nextChar) + " " + toUnicode(sNextChar) + " " + toUnicode(tNextChar) + " \'");
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
                            out.append('E');
                            out.append('a');
                            break;
                        }
                        
                        
                        if( (S5.indexOf(sPrevChar) == -1 && S5.indexOf(prevChar) == -1) && (S5.indexOf(nextChar) == -1 && S5.indexOf(sNextChar) == -1)){
                            out.append('E');
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
                    case 'ж':
                        S3 = "еЕ";
                       // if (S3.indexOf(nextChar) != -1) {
                       //    out.append('g');
                       // } else {
                            out.append('j');
                       // }

                        break;
                        //----------
                        
                    case 'Ж':
                        S3 = "еЕ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('G');
                        } else {
                            out.append('J');
                        }
                        break;
                        //----------------------
                        
                    case 'щ':
                        out.append('ș');
                        out.append('t');
                        break;
                        //----------
                        
                    case 'Щ':
                        out.append('Ș');
                        out.append('t');
                        break;
                        //----------------------
                        
                    case 'ӂ':
                        S3 = "еиЕИ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('g');
                        }
                        break;
                        //----------
                        
                    case 'Ӂ':
                        S3 = "еиЕИ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('G');
                        }
                        break;
                        //----------------------
                        
                    case 'ю':
                        out.append('i');
                        out.append('u');
                        break;
                        //----------
                    case 'Ю':
                        out.append('I');
                        out.append('U');
                        break;
                        //----------------------
                        
                    case '\ua65f': // ꙟ
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('î');
                            out.append('m');
                            break;
                        }
                        
                        S4 = "левмтсдкцчЛЕВМДТСКЦЧ";
                        if (S5.indexOf(prevChar) != -1) {
                            if ((S4.indexOf(nextChar) != -1) || (S5.indexOf(nextChar) != -1)) {
                                out.append('î');
                                out.append('n');
                                break;
                            }
                        }
                        out.append('î');
                        break;
                        //----------
                        
                    case '\ua65e': // Ꙟ
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            if(Character.isLowerCase(nextChar)){
                                out.append('Î');
                                out.append('m');
                            } else {
                                out.append('Î');
                                out.append('M');
                            }
                            break;
                        }
                        
                        S4 = "левмтсдкцЛЕВМДТСКЦ";
                        if (prevChar == '\u0020') {
                            if ((S4.indexOf(nextChar) != -1) || (nextChar == '\u0020')) {
                                if(Character.isLowerCase(nextChar)){
                                    out.append('Î');
                                    out.append('n');
                                } else {
                                    out.append('Î');
                                    out.append('N');
                                }
                                break;
                            }
                        }
                        out.append('Î');
                        break;
                        //----------------------
                        
                    case '\u0463': //ѣ
                        S3 = "iϊиеч";
                        prevChar = Character.toLowerCase(prevChar);
                        
                        if (S3.indexOf(prevChar) != -1) {
                            out.append('a');
                            break;
                        }
                        
                        out.append('e');
                        out.append('a');
                        break;
                        //----------
                        
                    case '\u0462': // Ѣ
                        S3 = "iϊиеч";
                        prevChar = Character.toLowerCase(prevChar);
                        
                        if (S3.indexOf(prevChar) != -1) {
                            out.append('A');
                            break;
                        }
                        
                        out.append('E');
                        out.append('A');
                        break;
                        
                        //----------------------
                    case '\u044a': // ъ
                        if (tPrevChar == 'ꙟ' && sPrevChar == 'т' && prevChar == 'ρ') {
                            out.append('e');
                            break;
                        }
                        
                        if (prevChar == 'o') {
                            out.append('u');
                            out.append('ă');
                            break;
                        }
                        
                        out.append('ă');
                        break;
                        //----------
                        
                    case '\u042a': // Ъ
                        if (tPrevChar == 'ꙟ' && sPrevChar == 'т' && prevChar == 'ρ') {
                            out.append('E');
                            break;
                        }
                        
                        if (prevChar == 'o') {
                            out.append('U');
                            out.append('A');
                            break;
                        }
                        
                        out.append('Ă');
                        break;
                        //----------------------
                        
                    case '\u0467': // ѧ
                        out.append('i');
                        out.append('a');
                        break;
                        //----------
                        
                    case '\u0466': // Ѧ
                        out.append('I');
                        out.append('A');
                        break;
                        //----------------------
                        
                    case '\u046b': // ѫ
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('î');
                            out.append('m');
                            break;
                        }
                        
                        S4 = "левмтсдкцЛЕВМДТСКЦ";
                        if (prevChar == '\u0020') {
                            if ((S4.indexOf(nextChar) != -1) || (nextChar == '\u0020')) {
                                out.append('î');
                                out.append('n');
                                break;
                            }
                        }
                        out.append('î');
                        break;
                        //----------
                        
                    case '\u046a': // Ѫ
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('Î');
                            out.append('m');
                            break;
                        }
                        
                        S4 = "левмтсдкцЛЕВМДТСКЦ";
                        if (prevChar == '\u0020') {
                            if ((S4.indexOf(nextChar) != -1) || (nextChar == '\u0020')) {
                                out.append('Î');
                                out.append('n');
                                break;
                            }
                        }
                        out.append('Î');
                        break;
                        //----------------------
                        
                    case '\u0469': // ѩ
                        if (nextChar == '$') {
                            S3 = "cdgjlmtţCDGJLMTŢ";
                        } else {
                            S3 = "gjlnrsştţyGJLNRSŞTŢY";
                        }
                        prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                        if (prevChar != 'i' && nextChar != '$') {
                            if (S3.indexOf(prevTr) != -1) {
                                out.append('e');
                            } else {
                                out.append('i');
                            }
                            out.append('a');
                        } else {
                            out.append('a');
                        }
                        break;
                        //----------
                        
                    case '\u0468': // Ѩ   
                        
                        if (nextChar == '$') {
                            S3 = "cdgjlmtţCDGJLMTŢ";
                        } else {
                            S3 = "gjlnrsştţyGJLNRSŞTŢY";
                        }
                        prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                        if (prevChar != 'i' && nextChar != '$') {
                            if (S3.indexOf(prevTr) != -1) {
                                out.append('e');
                            } else {
                                out.append('i');
                            }
                            out.append('a');
                        } else {
                            out.append('a');
                        }
                        break;
                        //---------------------- 
                        
                    case '\ua697': // ꚗ
                        out.append('ș');
                        out.append('t');
                        break;
                        //----------
                        
                    case '\ua696': // Ꚗ
                        out.append('Ș');
                        out.append('t');
                        break;
                        //----------------------
                        
                    default:
                        out.append(currentChar);
                        break;
                        
                } //SWITCH
            } //if-ELSE
        } //FOR
    } //END TRANSLITERATE-ROM
    
}
