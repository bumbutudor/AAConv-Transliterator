//12.9.16 16:15 v1.3 
package com.translit.src;

public class TranslitSlav {
        
    public static String toUnicode(char ch) {
        return String.format("\\u%04x", (int) ch);
    }
    
    
    public void transliterateSlav(StringBuilder incStr, StringBuilder out) {
        char tPrevChar, sPrevChar, prevChar;
        char currentChar;
        char nextChar, sNextChar, tNextChar;
        char prevTr;
        String S1 = "ФНЦШsΡабвдезийлмноупрстэфхцшАБВДЕЗИЛМНОПРСТЙ\u2019\u0027\uA64A\ua64b\u0223\u03c8\u045f\u012D\u0457\u0407\ua666\u03c1\u0460\u03c7\u046f\u0479\u021d\u021c"; //3- ψ џ ĭ ї Ї Ꙧ ρ Ѡ χ ѯ ѹ ȝ Ȝ
        String S2 = "FNȚȘzRabvdeziilmnouprstăfhțşABVDEZILMNOPRSTI--UuuşjiiIMrOhzuzZ";   //-----------Slav translit--------------
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
                        
                        //----------------------
                    case 'к':
                        if( (nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar =='О')){
                            out.append('k');
                            break;
                        }
                        
                        S3 = "\u0469\u0457еиюяЕИЮЯ"; //ѩ
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
                        if( (nextChar == 'и' || nextChar == 'И') && (sNextChar == 'л' || sNextChar == 'Л') && (tNextChar == 'о' || tNextChar =='О')){
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
                        
                        S3 = "ieеиьяIEЕИЬЯ";
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
                        
                    case 'ь':
                        if (prevChar == 'ч') {
                            out.append('i');
                            break;
                        }
                        //out.append('ă');
                        break;
                        //----------
                        
                    case 'Ь':
                        if (prevChar == 'ч') {
                            out.append('i');
                            break;
                        }
                        //out.append('Ă');
                        break;
                        //----------------------
                        
                    case 'ю':
                        if(prevChar == 'ї'){
                            out.append('u');
                            break;
                        }
                        out.append('i');
                        out.append('u');
                        break;
                        //----------
                        
                    case 'Ю':
                        out.append('i');
                        out.append('u');
                        break;
                        //----------------------
                        
                    case 'ж':
                        if (tPrevChar == 'г' && sPrevChar == 'ρ' && prevChar == 'и' && nextChar == 'е') {
                            out.append('j');
                            break;
                        }
                        
                        S3 = "еЕ";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('g');
                        } else {
                            out.append('j');
                        }
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
                        
                    case 'ъ':
                        if ((tPrevChar == '\u0461' && sPrevChar == 'с' && prevChar == '\u03c1' && nextChar == 'д' && sNextChar == '\u0457') || //ѡ ρ ї
                            (sPrevChar == 'ъ' && prevChar != '\u0020' && nextChar == 'н' && sNextChar == 'д') ||
                            ((tPrevChar == 'Ρ' || tPrevChar == 'ρ' || tPrevChar == 'р') && sPrevChar == 'о' && prevChar == 'м' && nextChar == 'н') ||
                            (tPrevChar == 'с' && sPrevChar == 'т' || prevChar == 'ρ' && nextChar == 'м') ||
                            ((prevChar == 'П' || prevChar == 'п')  && nextChar == 'н') ||
                            (prevChar == 'ф' && nextChar == 'н' && sNextChar == 'т') ||
                            (prevChar == 'т' && nextChar == 'н' && sNextChar == 'ѫ') ||
                            (sPrevChar == 'ꙋ' && prevChar == 'ρ' && nextChar == 'н') ||
                            (prevChar == 'ρ' && nextChar == 'н' && sNextChar == 'д') ||
                            (tPrevChar == 'п' && sPrevChar == 'ъ' && prevChar == 'м' && nextChar == 'н' && sNextChar == 'т')      
                                ) {
                            
                            out.append('â');
                            break;
                        }
                        
                        if (sPrevChar == '\u0020' && prevChar == 'с' && nextChar == 'н' && sNextChar == 'т') {
                            out.append('î');
                            break;
                        }
                        
                        if ((prevChar == 'с' && nextChar == 'к' && sNextChar == '\u0469' && tNextChar == 'м')
                            || //ѩ
                            (tPrevChar == '\ua65f' && sPrevChar == 'т' && prevChar == 'ρ')) { //ꙟ
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
                        if ((tPrevChar == '\u0461' && sPrevChar == 'с' && prevChar == '\u03c1' && nextChar == 'д' && sNextChar == '\u0457') || //ѡ ρ ї
                            (sPrevChar == 'ъ' && prevChar != '\u0020' && nextChar == 'н' && sNextChar == 'д') ||
                            ((tPrevChar == 'Ρ' || tPrevChar == 'ρ' || tPrevChar == 'р') && sPrevChar == 'о' && prevChar == 'м' && nextChar == 'н') ||
                            (tPrevChar == 'с' && sPrevChar == 'т' || prevChar == 'ρ' && nextChar == 'м') ||
                            ((prevChar == 'П' || prevChar == 'п')  && nextChar == 'н') ||
                            (prevChar == 'ρ' && nextChar == 'н' && sNextChar == 'д')    ) {
                            out.append('â');
                            break;
                        }
                        
                        if (sPrevChar == '\u0020' && prevChar == 'с' && nextChar == 'н' && sNextChar == 'т') {
                            out.append('î');
                            break;
                        }
                        
                        if ((prevChar == 'с' && nextChar == 'к' && sNextChar == '\u0469' && tNextChar == 'м')
                            || //ѩ
                            (tPrevChar == '\ua65f' && sPrevChar == 'т' && prevChar == 'ρ')) { //ꙟ
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
                        
                        //----------------------
                        
                    case 'ы':
                        if ((sPrevChar == '^' && (nextChar == 'н' && (sNextChar == 'е' || sNextChar == 'ь') && tNextChar == '$')) || (sPrevChar == '^' && nextChar == 'н' && sNextChar == 'и' && tNextChar == 'л')) {
                            out.append('î');
                            out.append('i');
                            break;
                        }
                        
                        if (S5.indexOf(prevChar) != -1 || S5.indexOf(nextChar) != -1) {
                            out.append('î');
                        } else if((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')){
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
                        } else if((tPrevChar == 'р' || tPrevChar == 'Р') && (sPrevChar == 'о' || sPrevChar == 'О') && (prevChar == 'м' || prevChar == 'М')){
                            out.append('Â');
                        }
                        break;
                        //----------------------
                        
                    case '\u0461': // ѡ
                        if (sPrevChar == 'н' && prevChar == 'о') {
                            out.append('u');
                            out.append('ă');
                            break;
                        }
                        out.append('o');
                        break;
                        //----------
                        
                    case '\u0460': // Ѡ
                        if (sPrevChar == 'н' && prevChar == 'о') {
                            out.append('U');
                            out.append('Ă');
                            break;
                        }
                        out.append('O');
                        break;
                        //----------------------
                        
                    case '\ua697': // ꚗ
                        out.append('ș');
                        out.append('t');
                        break;
                        //----------
                        
                    case '\ua696': // Ꚗ
                        out.append('Ș');
                        out.append('T');
                        break;
                        //----------------------
                    
                    case '\u2191': // ↑
                    case '\ua65f': // ꙟ
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('î');
                            out.append('m');
                            break;
                        }
                        
                        S4 = "леввмтсдкцџчгЛЕВМДТСКЦЏЧГ";
                        if ((S4.indexOf(nextChar) != -1) || (S5.indexOf(nextChar) != -1)) {
                            out.append('î');
                            out.append('n');
                            break;
                        }
                        out.append('î');
                        break;
                        //----------
                        
                    case '\ua65e': // Ꙟ
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('Î');
                            out.append('m');
                            break;
                        }
                        
                        S4 = "леввмтсдкцџчЛЕВМДТСКЦЏЧ";
                        if ((S4.indexOf(nextChar) != -1) || (S5.indexOf(nextChar) != -1)) {
                            out.append('Î');
                            out.append('n');
                            break;
                        }
                        out.append('Î');
                        break;
                        //----------------------
                        
                    case '\u046b': // ѫ 
                        if((sPrevChar == 'о' && prevChar == 'ρ' && nextChar == 'м') ||
                           (prevChar == 'ζ' && nextChar == 'н')){ //оρѫм
                            out.append('â');
                            break;
                        }
                        
                        S3 = "рпРП";
                        if (S3.indexOf(nextChar) != -1) {
                            out.append('î');
                            out.append('m');
                            break;
                        }
                        
                        S4 = "левмтсдкцЛЕВМДТСКЦ";
                        if (S5.indexOf(prevChar) != -1) {
                            if ((S4.indexOf(nextChar) != -1) || (S5.indexOf(nextChar) != -1)) {
                                out.append('î');
                                out.append('n');
                                break;
                            }
                        }
                        out.append('ă');
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
                        if (S5.indexOf(prevChar) != -1) {
                            if ((S4.indexOf(nextChar) != -1) || (S5.indexOf(nextChar) != -1)) {
                                out.append('Î');
                                out.append('n');
                                break;
                            }
                        }
                        out.append('Î');
                        break;
                        //----------------------
                        
                    case '\u0463': //ѣ
                        if (prevChar == 'ч') {
                            break;
                        }
                        
                        if ((tPrevChar == '\ua64b' && sPrevChar == 'п' && prevChar == '\u03c1' && nextChar == 'л') || //ꙋ ρ
                            (tPrevChar == '\u045f' && sPrevChar == '\ua64b' && prevChar == 'д' && nextChar == 'ц') || //џ ꙋ
                            (prevChar == 'л' && nextChar == 'џ' && sNextChar == 'е') ||
                            (sPrevChar == 'к' && prevChar == 'м' && nextChar == 'л') ||
                            (sPrevChar == 'о' && prevChar == 'в' && nextChar == 'щ') ||
                            (sPrevChar == 'а' && prevChar == 'ρ' && S5.indexOf(nextChar) != -1) ||
                            ((nextChar == 'а'))) {
                            out.append('e');
                            break;
                        }
                        
                        out.append('e');
                        out.append('a');
                        break;
                        //----------
                        
                    case '\u0462': // Ѣ
                        if (prevChar == 'ч') {
                            break;
                        }
                        
                        if ((tPrevChar == '\ua64b' && sPrevChar == 'п' && prevChar == '\u03c1' && nextChar == 'л') || //ꙋ ρ
                            (tPrevChar == '\u045f' && sPrevChar == '\ua64b' && prevChar == 'д' && nextChar == 'ц') || //џ ꙋ
                            (prevChar == 'л' && nextChar == 'џ' && sNextChar == 'е') ||
                            (sPrevChar == 'к' && prevChar == 'м' && nextChar == 'л') ) {
                            out.append('e');
                            break;
                        }
                        
                        if (prevChar == 'ч' || prevChar == 'Ч') {
                            out.append('A');
                            break;
                        }
                        out.append('E');
                        out.append('A');
                        break;
                        //----------------------
                        
                    case '\u0469': // ѩ
                        if (nextChar == '$') {
                            S3 = "cdgjlmtţCDGJLMTŢ";
                        } else {
                            S3 = "gjlnrsştţyGJLNRSŞTŢY";
                        }
                        prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                        if (prevTr != 'i' && nextChar != '$') {
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
                        
                    case '\u0467': // ѧ
                        if((prevChar == 'т' && S5.indexOf(nextChar) != -1) ||
                           (prevChar == 'ρ' && S5.indexOf(nextChar) != -1) ){
                            out.append('e');
                            out.append('a');
                            break;
                        }
                        
                        if ((sPrevChar == '\ua65f' && prevChar == 'в' && nextChar == 'ч') || //ꙟ
                            (S5.indexOf(prevChar) != -1 && nextChar == 'с' && sNextChar == 'т')    
                                ){ 
                            out.append('e');
                            break;
                        }
                        
                        prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                        if (prevTr == 'i') {
                            out.append('a');
                            break;
                        }
                        out.append('i');
                        out.append('a');
                        break;
                        //----------
                        
                    case '\u0466': // Ѧ
                        if(tPrevChar == 'Т' && sPrevChar == 'Ρ' && prevChar == 'Е'){
                            out.append('I');
                            out.append('A');
                            break;
                        }
                        
                        if((prevChar == 'т' && S5.indexOf(nextChar) != -1) ||
                           (prevChar == 'ρ' && S5.indexOf(nextChar) != -1) ){
                            out.append('e');
                            out.append('a');
                            break;
                        }
                        
                        if (sPrevChar == '\ua65f' && prevChar == 'в' && nextChar == 'ч') { //ꙟ
                            out.append('e');
                            break;
                        }
                        prevTr = (out.length() == 0) ? '^' : out.charAt(out.length() - 1);
                        if (prevTr == 'i') {
                            out.append('a');
                            break;
                        }
                        out.append('i');
                        out.append('a');
                        break;    
                        //----------------------
                        
                    case '\u03b6': // ζ
                        if (tPrevChar == 'в' && sPrevChar == '\u0457' && prevChar == '\u0467') { //ї ѧ
                            out.append('ț');
                            break;
                        }
                        out.append('z');
                        break;
                        //----------
                        
                    case '\u0396': // Ζ (CAPPITAL 'ζ')   
                        if (tPrevChar == 'в' && sPrevChar == '\u0457' && prevChar == '\u0467') { //ї ѧ
                            out.append('ț');
                            break;
                        }
                        out.append('Z');
                        break;    
                        //----------------------                       
                        
                    
                    case '\u0475': // ѵ
                        if(prevChar == 'г' && nextChar == 'п' && sNextChar == 'е'){
                            out.append('h');
                            out.append('i');
                            break;
                        }
                        
                        if( (prevChar == 'Е' || prevChar == 'е') && nextChar == 'г' && sNextChar == 'л' ){
                            out.append('v');
                            break;
                        }
                        
                        out.append('i');
                        break;
                        
                    case '\u0474': // Ѵ
                        if(prevChar == 'г' && nextChar == 'п' && sNextChar == 'е'){
                            out.append('h');
                            out.append('i');
                            break;
                        }
                        
                        if( (prevChar == 'Е' || prevChar == 'е') && nextChar == 'г' && sNextChar == 'л' ){
                            out.append('v');
                            break;
                        }
                        
                        out.append('i');
                        break;
                        
                        
                    case '\u0472': // ѳ
                    case '\u0473':
                        if(sPrevChar == 'в' && prevChar == 'а' && nextChar == 'а'){
                            out.append('t');
                            break;
                        }
                        out.append('f');
                        break;
                        
                    default:
                        out.append(currentChar);
                        break;
                        
                } //SWITCH
            } //if-ELSE
        } //FOR
    } //END TRANSLITERATE-SLAV   
    
} // END main class
