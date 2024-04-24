package br.com.wmw.util;

import totalcross.sys.Convert;
import totalcross.sys.InvalidNumberException;
import totalcross.ui.font.Font;
import totalcross.ui.font.FontMetrics;
import totalcross.util.BigDecimal;
import totalcross.util.Date;
import totalcross.util.Vector;

public class StringUtil {

    public static String getStringValue(char value) {
        return String.valueOf(value);
    }

    public static String getStringValue(int value) {
        return String.valueOf(value);
    }

    public static String getStringValue(long value) {
        return String.valueOf(value);
    }

    public static String getStringValue(Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    public static String getStringValue(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    public static String getStringValue(boolean value) {
        if (value) {
            return ValueUtil.VALOR_SIM;
        } else {
            return ValueUtil.VALOR_NAO;
        }
    }

    public static String getStringValue(String value, int max) {
        if (value == null) {
            return "";
        } else {
            if (value.length() > max) {
                return value.substring(0, max);
            } else {
                return value;
            }
        }
    }

    @SuppressWarnings("deprecation")
	public static String getStringValue(Date value) {
        if (ValueUtil.isEmpty(value)) {
            return "";
        }
        return String.valueOf(value);
    }

    public static String getStringValue(double value) {
        return getStringValue(value, ValueUtil.doublePrecisionInterface);
    }

    public static String getStringValue(double value, int precision) {
        return getStringValue(value, precision, false);
    }

    public static String getStringValueToInterface(int value) {
        return getStringValueSimple(value, 0, true);
    }

    public static String getStringValueToInterface(double value) {
        return getStringValueSimple(value, ValueUtil.doublePrecisionInterface, true);
    }

	public static String getStringValueToInterface(BigDecimal value) {
		return getStringValueSimple(ValueUtil.toDouble(value), ValueUtil.doublePrecisionInterface, true);
	}

    public static String getStringValueToInterface(double value, int precision) {
        return getStringValueSimple(value, precision, true);
    }
    
    public static String getStringValueToInterface(Date value) {
    	return getStringValue(value);
    }

    public static String getStringValueSimple(double value, int precision, boolean toCurrency) {
        return toCurrency ? Convert.toCurrencyString(value, precision) : Convert.toString(value, precision);
    }

    private static String getStringValue(double value, int precision, boolean toCurrency) {
        try {
            BigDecimal b = BigDecimal.valueOf(value == 0.0 ? value : (value + 0.000000001));
            b = b.setScale(precision, BigDecimal.ROUND_HALF_UP);
            return toCurrency ? Convert.toCurrencyString(b.toPlainString(), precision) : b.toPlainString();
        } catch (InvalidNumberException e) {
            return e.getMessage();
        }
		
    }



    public static char getCharValue(boolean value) {
        if (value) {
            return ValueUtil.FL_SIM;
        } else {
            return ValueUtil.FL_NAO;
        }
    }

    public static String getStringValuePositivo(double value) {
        value = value < 0 ? value * -1 : value;
        return getStringValue(value, ValueUtil.doublePrecisionInterface);
    }

    public static String getStringValuePositivo(int value) {
        value = value < 0 ? value * -1 : value;
        return String.valueOf(value);
    }

    //-----------------------------------------------------------------


    public static final String escapeXml(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        StringBuilder sb = new StringBuilder(1000);
        sb.setLength(0);
        for (int i = 0; i < n; i++) {
            char c = chars[i];
            switch (c) {
                case 34:
                    sb.append("&quot;");
                    break; // "
                case 38:
                    sb.append("&amp;");
                    break; // &
                case 39:
                    sb.append("&apos;");
                    break; // '
                case 60:
                    sb.append("&lt;");
                    break; // <
                case 62:
                    sb.append("&gt;");
                    break; // >
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

	public static String resume(final String str, final int tam) {
		if ((str == null) || (str.length() <= tam)) {
			return str;
		} else if(tam <= 0){
			return "";
		} else {
			return str.substring(0, tam) + " [...]";
		}
	}


    public static String delete(String value, char chDel) {
        final String charRemove = String.valueOf(chDel);
        return value.replace(charRemove, "");
    }

    public static String deleteSpecialChars(String value) {
        return value.replaceAll("[\\\\'§¢£\"]", "");
    }

	public static boolean startsWith(String text, final String value) {
		if (value == null) {
			return false;
		}
		text = getStringValue(text);
		return text.startsWith(value);
	}

    public static String fillZero(String value, int length, boolean trunc, boolean right) {
        if (value.length() > length) {
            if (trunc) {
                return value.substring(0, length);
            } else {
                return value;
            }
        }
        //--
        if (right) {
            int n = length - value.length();
            if (n > 0) {
                return value + Convert.dup('0', n);
            }
        } else {
            while (value.length() < length) {
                value = "0" + value;
            }
        }
        return value;
    }

    public static String fillZero(int value, int length) {
        return fillZero(value, length, false);
    }

    public static String fillZero(int value, int length, boolean trunc) {
        return fillZero(getStringValue(value), length, trunc, false);
    }

  

    /**
     * Método realiza a separação do texto de acordo com o caracter limite especificado
     *
     * @param str   Texto que será separado
     * @param limit Caracter limite que será buscado no texto para separá-lo
     * @return Array de String com o texto separado em cada posição
     */
    public static String[] split(String str, char limit) {
        return split(str, limit, false);
    }

    /**
     * Método realiza a separação do texto de acordo com o caracter limite especificado
     *
     * @param str      Texto que será separado
     * @param limit    Caracter limite que será buscado no texto para separá-lo
     * @param useEmpty Cria uma posição em branco no array retornado caso não haja texto entre dois caracteres limites
     * @return Array de String com o texto separado em cada posição
     */
    public static String[] split(String str, char limit, boolean useEmpty) {
        return split(str, limit, useEmpty, false);
    }

    /**
     * Método realiza a separação do texto de acordo com o caracter limite especificado
     *
     * @param str                      Texto que será separado
     * @param limit                    Caracter limite que será buscado no texto para separá-lo
     * @param useEmpty                 Cria uma posição em branco no array retornado caso não haja texto entre dois caracteres limites
     * @param ignoreLimitFirstPosition Se encontrar o caracter limite na primeira posição do texto, não faz a separação neste ponto e continua rotina
     * @return Array de String com o texto separado em cada posição
     */
    public static String[] split(String str, char limit, boolean useEmpty, boolean ignoreLimitFirstPosition) {
        if (str == null) {
            return new String[]{};
        }
        //--
        Vector list = new Vector();
        StringBuilder aux = new StringBuilder();
        char b[] = str.toCharArray();
        int sizeString = str.length();
        for (int i = 0; i < sizeString; i++) {
            if (b[i] != limit) {
                aux.append(b[i]);
                if (i == (sizeString - 1)) {
                    list.addElement(aux.toString());
                }
            } else {
                if (i == 0 && ignoreLimitFirstPosition) {
                    continue;
                }
                if (!ValueUtil.isEmpty(aux.toString()) || useEmpty) {
                    list.addElement(aux.toString());
                    aux = new StringBuilder();
                }
            }
        }
        //--
        String stringList[] = new String[list.size()];
        int size = list.size();
        for (int i = 0; i < size; i++) {
            stringList[i] = (String) list.items[i];
        }
        list = null;
        aux = null;
        return stringList;
    }

    public static int[] splitToInt(String str, char limit) {
        Vector list = new Vector();
        StringBuilder aux = new StringBuilder();
        char b[] = str.toCharArray();
        int sizeString = str.length();
        for (int i = 0; i < sizeString; i++) {
            if (b[i] != limit) {
                aux.append(b[i]);
                if (i == (sizeString - 1)) {
                    list.addElement(aux.toString());
                }
            } else {
                if (!ValueUtil.isEmpty(aux.toString())) {
                    list.addElement(aux.toString());
                    aux = new StringBuilder();
                }
            }
        }
        //--
        int stringList[] = new int[list.size()];
        int size = list.size();
        for (int i = 0; i < size; i++) {
            stringList[i] = ValueUtil.getIntegerValue((String) list.items[i]);
        }
        list = null;
        aux = null;
        return stringList;
    }

    public static String split(String str, char limit, int pos) {
        int beginIndex = 0;
        int endIndex = str.length();
        int countPos = 0;
        char[] c = str.toCharArray();
        int length = c.length;
        for (int i = 0; i < length; i++) {
            if (c[i] == limit) {
                countPos++;
                if (countPos == pos) {
                    beginIndex = i + 1;
                }
                if (countPos == (pos + 1)) {
                    endIndex = i;
                    break;
                }
            }
        }
        return str.substring(beginIndex, endIndex);
    }

    public static String[] splitTurbo(String str, char limit, int countTotal) {
        String[] strArray = new String[countTotal];
        int beginIndex = 0;
        int countPos = 0;
        int i = 0;
        char[] c = str.toCharArray();
        int length = c.length;
        do {
            if (c[i] == limit) {
                strArray[countPos] = new String(str.substring(beginIndex, i));
                countPos++;
                beginIndex = i + 1;
            }
            i++;
        } while (i < length);
        strArray[countPos] = new String(str.substring(beginIndex, str.length()));
        //--
        return strArray;
    }

    /**
     * Este split deve ser usado para strings concanetadas com
     * indexação
     *
     * @param concatedString
     * @param separator
     * @return String[]
     */
    public static String[] splitDualIndexed(String concatedString, char separator) {
        int length = concatedString.length();
        int middleIndex = (concatedString.charAt(length - 1));
        int endIndex = length - 2;
        //--
        //No Java Desktop é mais otimizado em relação a memoria utilizar nova string.
        //É mais lento no Pda mas, baseado no Desktop vamos manter
        //return new String[] {new String(concatedString.substring(0, middleIndex)), new String(concatedString.substring(middleIndex+1, endIndex))};
        return new String[]{concatedString.substring(0, middleIndex), concatedString.substring(middleIndex + 1, endIndex)};
    }


    public static String concatDualIndexed(String firstStr, String secondStr, char separator) {
        StringBuilder str = new StringBuilder();
        str.append(firstStr);
        str.append(separator);
        str.append(secondStr);
        str.append(separator);
        str.append((char) firstStr.length());
        return str.toString();
    }

    public static String concatStrings(String[] strings, char separator) {
        StringBuilder str = new StringBuilder();
        if (strings != null) {
            for (int i = 0; i < strings.length; i++) {
                str.append(strings[i]);
                if (i + 1 != strings.length) {
                    str.append(separator);
                }
            }
        }
        return str.toString();
    }


    /**
     * Retorna a String passada por parâmetro sem ' (aspa simples)
     *
     * @param str
     * @return String
     */
    public static String clearSpecialChars(String str) {
        if (!ValueUtil.isEmpty(str)) {
            return replaceAll(replaceAll(str, "\'", " "), "§", " ");
        } else {
            return str;
        }
    }

    public static String clearEnter(String str) {
        if (!ValueUtil.isEmpty(str)) {
            return replaceAll(replaceAll(str, "\n", "¢"), "\r", "£");
        } else {
            return str;
        }
    }

    public static String clearEnterException(String str) {
        if (!ValueUtil.isEmpty(str)) {
            return replaceAll(replaceAll(str, "\n", ""), "\r", "");
        } else {
            return str;
        }
    }

    @Deprecated
    /**
     * Utilizar direto value.replace do classe String
     * @param value
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceAll(String value, String regex, String replacement) {
        return value.replace(regex, replacement);
    }

    public static String replaceFirstOccurrence(String value, String regex, String replacement) {
        boolean found = false;
        StringBuilder aux = new StringBuilder();
        String character;
        char[] c = value.toCharArray();
        int length = c.length;
        for (int i = 0; i < length; i++) {
            character = getStringValue(c[i]);
            if (!found && regex.equals(character)) {
                aux.append(replacement);
                found = true;
            } else {
                aux.append(character);
            }
        }
        return aux.toString();
    }

    public static String replaceStringForBreakLine(String value) {
        StringBuilder aux = new StringBuilder();
        char[] c = value.toCharArray();
        int length = c.length;
        for (int i = 0; i < (length); i++) {
            if (i == (length - 1)) {
                aux.append(c[i]);
                break;
            }
            if ("|".equals(getStringValue(c[i]))) {
                aux.append('\n');
                i++;
            } else {
                aux.append(c[i]);
            }
        }
        return aux.toString();
    }

    public static String toString(String[] arrayString) {
        return toString(arrayString, ",");
    }

    public static String toString(String[] arrayString, String separador) {
        StringBuilder aux = new StringBuilder();
        int length = arrayString.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                aux.append(separador);
            }
            aux.append(arrayString[i]);
        }
        return aux.toString();
    }

    public static boolean contains(String str, String chrs) {
        int index = str.indexOf(chrs);
        return index != -1;
    }

    public static byte[] stringToByteArray(String[] strs) {
        byte[] result = new byte[strs.length];
        for (int i = 0; i < strs.length; i++) {
            result[i] = (byte) ValueUtil.getIntegerValue(strs[i]);
        }
        return result;
    }

    /**
     * Método usado para calcular o tamanho que um texto cabe em um determinado espaço,
     * e fazer a abreviação do restante que não couber colocando (...).
     * Leva em consideração o tamanho de fonte padrão do sistema para fazer o cálculo.
     *
     * @param str     Texto para abreviar
     * @param largura Largura da tela onde o texto será colocado
     * @return Texto abreviado
     */
    
    /**
     * Método usado para calcular o tamanho que um texto cabe em um determinado espaço,
     * e fazer a abreviação do restante que não couber colocando (...)
     *
     * @param str     Texto para abreviar
     * @param largura Largura da tela onde o texto será colocado
     * @param font    Objeto de Fonte para fazer o cálculo
     * @return Texto abreviado
     */
    public static String getStringAbreviada(String str, int largura, Font font) {
        if (str == null || font == null) {
            return str;
        }
        StringBuilder aux = new StringBuilder();
        String result = str;
        char[] c = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            aux.append(c[i]);
            if (font.fm.stringWidth(aux.toString()) > largura) {
                result = aux.toString().substring(0, aux.length() - 3) + "...";
                break;
            }
        }
        return result;
    }

	public static String getStringCortada(String str, int largura, Font font) {
		if (str == null || font == null || largura == 0) {
			return str;
		}
		StringBuilder aux = new StringBuilder();
		String result = str;
		char []c = str.toCharArray();
		int stringLength = str.length();
		for (int i=0; i < stringLength; i++) {
			aux.append(c[i]);
			if (font.fm.stringWidth(aux.toString()) >= largura - font.fm.stringWidth(getStringValue(c[stringLength - 1]))) {
				result = aux.toString().substring(0, aux.length());
				break;
			}
		}
		return result;
	}

    /**
     * Método usado para inserir um texto em uma determinada posição de outro texto.
     *
     * @param oldString    Texto antigo
     * @param insertString Texto que será inserido
     * @param pos          Posição que será inserido o texto
     * @return Nova String com o texto inserido na posição desejada
     */
    public static String insertStringPos(String oldString, String insertString, int pos) {
        String result = oldString;
        StringBuilder aux = new StringBuilder();
        aux.append(oldString.substring(0, pos));
        aux.append(insertString);
        aux.append(oldString.substring(pos, oldString.length()));
        if (aux != null) {
            result = aux.toString();
        }
        return result;
    }
    
    /**
     * Método para quebrar o texto em linhas com máximo de aproveitamento do espaço.
     * Uma palavra poderá ser cortada em qualquer parte.
     * Utilizados em raras ocasiões quando a largura é pequena.
     * Para quebra de linhas em geral, utilizar Convert.insertLineBreak
     *
     * @param texto   Texto a ser quebrado
     * @param tamanhoMaximoCaracteresLinha    Tamanho máximo de caracteres que cabe em uma linha
     * @param fm      Fonte do texto a ser calculado
     * @return Novo array de String com o texto quebrado
     */
	public static String[] breakLineTokenized(String texto, int tamanhoMaximoCaracteresLinha, FontMetrics fm) {
		int widthBaseChar = fm.charWidth('S');
		int maxWidthTextLine = widthBaseChar * tamanhoMaximoCaracteresLinha;
		int index = getIndexBreakLine(texto, fm, maxWidthTextLine);
		String delimitadorLinha = getDelimitadorLinha(index);
		String linha1;
		String linha2 = " ";
		if (texto.length() > index) {
			linha1 = texto.replaceAll(delimitadorLinha, "$1");
			linha2 = texto.replaceAll(delimitadorLinha, "$2");
		} else {
			linha1 = texto;
		}
		if (linha2.length() >= index) {
			index -= 3;
			linha2 =  linha2.replaceAll(getDelimitadorLinha(index), "$1");
			linha2 += "..."; 
		}
		return new String[] {linha1, linha2};
	}
	
	private static String getDelimitadorLinha(int index) {
		return "(.{"+ index +"})(.*)";
	}
	
	private static int getIndexBreakLine(String text, FontMetrics fm, int maxWidthTextLine) {
		int currentWidth = 0;
		for (int i = 0; i < text.length(); i++) {
	        currentWidth += fm.charWidth(text.charAt(i));
	        if (currentWidth > maxWidthTextLine) {
	        	return i - 1;
	        }
		}
		return text.length();
	}

    /**
     * Método que verifica se o texto contém somente chars válidos, de acordo com o conjunto de chars válidos passado por parâmetro.
     *
     * @param string     Texto que deseja verificar
     * @param validChars Conjunto de chars válidos
     * @return True se todos os chars do texto são válidos e false caso tenha algum char inválido no texto
     */
    public static boolean isValidAllChars(String string, String validChars) {
        boolean result = true;
        char[] c = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if ((validChars != null) && (validChars.indexOf(getStringValue(c[i])) == -1)) {
                result = false;
                break;
            }
        }
        return result;
    }

    // --
    public static String changeStringAccented(final String acentuada) {
        if (ValueUtil.isEmpty(acentuada)) {
            return "";
        }
        char[] acentuados = new char[]{'ç', 'á', 'à', 'ã', 'â', 'ä', 'é',
                'è', 'ê', 'ë', 'í', 'ì', 'î', 'ï', 'ó', 'ò', 'õ', 'ô', 'ö',
                'ú', 'ù', 'û', 'ü', 'ñ', 'Ç', 'Á', 'À', 'Ã', 'Â', 'Ä', 'É',
                'È', 'Ê', 'Ë', 'Í', 'Ì', 'Î', 'Ï', 'Ó', 'Ò', 'Õ', 'Ô', 'Ö',
                'Ú', 'Ù', 'Û', 'Ü', 'Ñ'};

        char[] naoAcentuados = new char[]{'c', 'a', 'a', 'a', 'a', 'a', 'e',
                'e', 'e', 'e', 'i', 'i', 'i', 'i', 'o', 'o', 'o', 'o', 'o',
                'u', 'u', 'u', 'u', 'n', 'C', 'A', 'A', 'A', 'A', 'A', 'E',
                'E', 'E', 'E', 'I', 'I', 'I', 'I', 'O', 'O', 'O', 'O', 'O',
                'U', 'U', 'U', 'U', 'N'};
        String valor = acentuada;
        for (int i = 0; i < acentuados.length; i++) {
            valor = valor.replace(acentuados[i], naoAcentuados[i]);
        }
        return valor;
    }

    public static String[] addStringToStringArray(String[] stringArray, String string) {
        if (ValueUtil.isNotEmpty(stringArray)) {
            int index = stringArray.length + (ValueUtil.isNotEmpty(string) ? 1 : 0);
            String[] parameters = new String[index];
            for (int i = 0; i < stringArray.length; i++) {
                parameters[i] = stringArray[i];
            }
            if (ValueUtil.isNotEmpty(string)) {
                parameters[index - 1] = string;
            }
            return parameters;
        }
        return stringArray;
    }

    public static String trimWhitespace(String str) {
        if (ValueUtil.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String removeCharsNotAlphanumerics(String text) {
        return changeStringAccented(text).replaceAll("[^A-z0-9]", "");
    }

	public static Vector splitStringIntoLines(String msg, int width, int limitLines, Font font) {
		Vector lines = new Vector(limitLines);
		String aux = "";
		int lineLength = 0;
		int pos = 0;
		while (ValueUtil.isNotEmpty(msg) && lines.size() < limitLines) {
			aux = StringUtil.getStringCortada(msg, width, font);
			lineLength = aux.length();
			pos = aux.lastIndexOf(" ");
			if (msg.length() > aux.length() && msg.charAt(lineLength) != ' ' && pos > -1) {
				aux = aux.substring(0, pos);
				lineLength = pos;
			}
			msg = msg.substring(lineLength).trim();
			lines.addElement(aux);
		}

		return lines;
	}
	
	public static char charAt(String str, int index) {
		if (str.length() - 1 > index) {
			return str.charAt(index);
		}
		return 0;
	}
	
    public static String montaFiltroBuscaLike(String dsFiltro, boolean usaPesquisaInicioString) {
    	if (ValueUtil.isEmpty(dsFiltro)) {
    		return "";
    	}
    	boolean onlyStartString = false;
    	if (usaPesquisaInicioString) {
    		if (dsFiltro.startsWith("*")) {
    			dsFiltro = dsFiltro.substring(1);
    		} else {
    			onlyStartString = true;
    		}
    	}
    	return onlyStartString ? dsFiltro + "%" : "%" + dsFiltro + "%";
    }

}
