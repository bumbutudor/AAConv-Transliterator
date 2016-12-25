//12.9.16 16:12 v0.3
package com.translit.src;

/**
 *
 * @author Richard Strauss
 */
public class TranslitTranz {
    
    public void transliterateTranz(StringBuilder incStr, StringBuilder out) {
        char tPrevChar, sPrevChar, prevChar;
        char currentChar;
        char nextChar, sNextChar, tNextChar;
        char prevTr;
        String S1 = "абвдзийлмнопрст\u012d\ua64bфхцш\u0467\u012CАБВДЗИЛМНОПРСТ\uA64AФНЦШ\u2019\u0027\u0466\u046A\u046b";  //ĭ ѫ ѧ Ĭ Ꙋ Ѧ Ѫ ѫ
        String S2 = "abvdziilmnoprstiufhţşâ\u012CABVDZILMNOPRSTUFHȚȘ--ÂÂâ";  //-----------Tranzitional translit--------------
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
                    case 'к':
                        if ((nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar == 'О')) {
                            out.append('k');
                            break;
                        }
                        
                        S3 = "iеиюяIЕИЮЯ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('c');
                            out.append('h');
                            break;
                        }
                        if (nextChar == 'с') {
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
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('C');
                            out.append('h');
                            break;
                        }
                        if (nextChar == 'с') {
                            out.append('X');
                            i++;
                            break;
                        }
                        out.append('C');
                        break;
                        //----------------------
                    case 'ч':
                        out.append('c');
                        S3 = "\u012dieеиьIEЕИЬ";
                        if (S3.indexOf(nextChar) != -1) {
                            break;
                        }
                        if (nextChar == 'а') {
                            out.append('e');
                            break;
                        }
                        out.append('i');
                        break;
                        //----------
                        
                    case 'Ч':
                        out.append('C');
                        S3 = "еeиьЕEИЬ";
                        if (S3.indexOf(nextChar) != -1) {
                            break;
                        }
                        if (nextChar == 'а') {
                            out.append('e');
                            break;
                        }
                        out.append('i');
                        break;
                        //----------------------
                        
                    case 'e':
                        if (sPrevChar == 'в' && prevChar == 'o' && nextChar == '\u0020') {
                            out.append('i');
                            out.append('e');
                            break;
                        }
                        out.append('e');
                        break;
                        //----------
                        
                    case 'Е':
                        if (sPrevChar == 'в' && prevChar == 'o' && nextChar == '\u0020') {
                            out.append('i');
                            out.append('e');
                            break;
                        }
                        out.append('E');
                        break;
                        //----------------------
                        
                    case 'г':
                        out.append('g');
                        S3 = "еияюьЕИЯЮЬ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('h');
                        }
                        break;
                        //----------
                        
                    case 'Г':
                        out.append('G');
                        S3 = "еияюьЕИЯЮЬ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('h');
                        }
                        break;
                        //----------------------
                        
                    case 'ж':
                        S3 = "еиЕИ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('g');
                        } else {
                            out.append('j');
                        }
                        break;
                        //----------
                        
                    case 'Ж':
                        S3 = "еиЕИ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('G');
                        } else {
                            out.append('J');
                        }
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
                        
                    case 'ъ':
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
                        
                    case 'Ъ':
                        tPrevChar = Character.toLowerCase(tPrevChar);
                        sPrevChar = Character.toLowerCase(sPrevChar);
                        prevChar = Character.toLowerCase(prevChar);
                        
                        if (tPrevChar == 'ꙟ' && sPrevChar == 'т' && prevChar == 'ρ') {
                            out.append('E');
                            break;
                        }
                        if (prevChar == 'o') {
                            out.append('U');
                            out.append('Ă');
                            break;
                        }
                        out.append('Ă');
                        break;
                      
//----------------------
                    case 'я':
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
                        }
                        out.append('a');
                        break;
//----------
                        
                    case 'Я':
                        if (nextChar == '$') {
                            S3 = "cdgjlmtţCDGJLMTŢ";
                        } else {
                            S3 = "gjlnrsştţyGJLNRSŞTŢY";
                        }
                        prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                        if (prevChar != 'i' && nextChar != '$') {
                            if (S3.indexOf(prevTr) != -1) {
                                out.append('E');
                            } else {
                                out.append('I');
                            }
                            out.append('a');
                        } else {
                            out.append('A');
                        }
                        break;
//----------------------
                        
                    case '\uA65F': // ꙟ
                        S3 = "врВР";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('î');
                            out.append('m');
                            break;
                        }
                        S3 = "мнМН";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('î');
                            break;
                        }
                        out.append('î');
                        break;
                        //----------
                        
                    case '\ua65e': // CAPTITAL LETER 'ꙟ'
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
                        
                    case '\u045F': // џ
                        out.append('g');
                        S3 = "еияюьЕИЯЮЬ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('h');
                        }
                        break;
                        //----------
                        
                    case '\u040F': // Џ
                        out.append('G');
                        S3 = "еияюьЕИЯЮЬ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('H');
                        }
                        break;
                        //----------------------
                        
                    case '\uA657': // ꙗ
                        out.append('i');
                        out.append('a');
                        break;
                        //----------
                        
                    case '\uA656': // Ꙗ
                        out.append('I');
                        out.append('a');
                        break;
                        //----------------------
                        
                    case '\u0465': // ѥ
                        out.append('i');
                        out.append('e');
                        break;
                        //----------
                        
                    case '\u0464': // Ѥ
                        out.append('I');
                        out.append('e');
                        break;
                        //----------------------
                        
                    case '\u0463': // ѣ
                        out.append('e');
                        out.append('a');
                        break;
                        //----------
                        
                    case '\u0462': // Ѣ
                        out.append('E');
                        out.append('a');
                        break;
                        //----------------------                            
                        
                    default:
                        out.append(currentChar);
                        //System.out.println(currentChar);
                        
                } //SWITH
            } //if-ELSE
        } //FOR
    } //END TRANSLITERATE-TRANZ
    
} //END main class
