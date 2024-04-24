package br.com.wmw.util;


import java.util.HashMap;
import java.util.List;

import totalcross.sys.Convert;
import totalcross.sys.InvalidNumberException;
import totalcross.sys.Vm;
import totalcross.util.BigDecimal;
import totalcross.util.Date;
import totalcross.util.Hashtable;
import totalcross.util.Vector;

public final class ValueUtil {

	public static final String VALOR_NI = "";
	public static final String VALOR_SIM = "S";
	public static final String VALOR_NAO = "N";
	public static final String VALOR_ZERO = "0";
	public static final String VALOR_NULL = "null";
	public static final String VALOR_EMBRANCO = " ";
	public static final char FL_NI = '\0';
	public static final char FL_SIM = 'S';
	public static final char FL_NAO = 'N';

	public static final char MINUTES = 'M';
	public static final char SECONDS = 'S';
	public static final char HOURS = 'H';

	public static int doublePrecision = 2;
	public static int doublePrecisionInterface = 2;
	public static final int DOUBLE_PRECISION_ARMAZENAMENTO = 7;
	public static final String VALID_ALPHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
	public static final String VALID_NUMBER_CHARS = "1234567890";

	private ValueUtil() {
		// -- 
	}

	public static boolean isEmpty(String[] values) {
		return (values == null) || (values.length <= 0);
	}
	
	public static boolean isEmpty(int[] values) {
		return (values == null) || (values.length <= 0);
	}

	public static boolean isEmpty(Object[] values) {
		return (values == null) || (values.length <= 0);
	}
	
	public static boolean isEmpty(List<?> values) {
		return (values == null) || (values.size() <= 0);
	}

	/**
	 * Deprecated devido a desempenho. Sugestão do Guich(Totalcross) de não ter um método
	 * chamado milhares de vezes e que faz apenas um checagem simples
	 */
	@Deprecated
	public static boolean isEmpty(Object value) {
		return value == null;
	}
	
	public static boolean isEmpty(Hashtable value) {
		return value == null || value.size() < 1;
	}
	
	public static boolean isEmpty(HashMap<?, ?> value) {
		return value == null || value.isEmpty();
	}

	public static boolean isEmpty(String value) {
		return (value == null) || "".equals(value);
	}

	/**
	 * Deprecated devido a desempenho. Sugestão do Guich(Totalcross) de não ter um método
	 * chamado milhares de vezes e que faz apenas um checagem simples
	 */
	@Deprecated
	public static boolean isEmpty(int value) {
		return value == 0;
	}

	public static boolean isEmpty(char value) {
		return value == FL_NI;
	}

	/**
	 * Deprecated devido a desempenho. Sugestão do Guich(Totalcross) de não ter um método
	 * chamado milhares de vezes e que faz apenas um checagem simples
	 */
	@Deprecated
	public static boolean isEmpty(double value) {
		return value == 0;
	}
	
	public static boolean isEmpty(Double value) {
		return value == null || value == 0;
	}
	
	public static boolean isEmpty(BigDecimal value) {
		return value == null || value.compareTo(BigDecimal.ZERO) == 0;
	}

	public static boolean isEmpty(Vector value) {
		return (value == null) || (value.size() == 0);
	}

	public static boolean isNotEmpty(String[] values) {
		return !isEmpty(values);
	}

	public static boolean isNotEmpty(Object[] values) {
		return !isEmpty(values);
	}
	
	public static boolean isNotEmpty(List<?> values) {
		return !isEmpty(values);
	}

	/**
	 * Deprecated devido a desempenho. Sugestão do Guich(Totalcross) de não ter um método
	 * chamado milhares de vezes e que faz apenas um checagem simples
	 */
	@Deprecated
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}
	
	public static boolean isNotEmpty(Hashtable value) {
		return !isEmpty(value);
	}

	public static boolean isNotEmpty(HashMap<?,?> value) {
		return !isEmpty(value);
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * Deprecated devido a desempenho. Sugestão do Guich(Totalcross) de não ter um método
	 * chamado milhares de vezes e que faz apenas um checagem simples
	 */
	@Deprecated
	public static boolean isNotEmpty(int value) {
		return !isEmpty(value);
	}

	public static boolean isNotEmpty(char value) {
		return !isEmpty(value);
	}

	/**
	 * Deprecated devido a desempenho. Sugestão do Guich(Totalcross) de não ter um método
	 * chamado milhares de vezes e que faz apenas um checagem simples
	 */
	@Deprecated
	public static boolean isNotEmpty(double value) {
		return !isEmpty(value);
	}
	
	public static boolean isNotEmpty(Double value) {
		return !isEmpty(value);
	}
	
	public static boolean isNotEmpty(BigDecimal value) {
		return !isEmpty(value);
	}

	public static boolean isNotEmpty(Date value) {
		return !isEmpty(value);
	}

	public static boolean isNotEmpty(Vector value) {
		return !isEmpty(value);
	}

	public static short getShortValue(String value) {
		try {
			return Convert.toShort(value);
		} catch (Throwable e) {
			return 0;
		}
	}

	public static int getIntegerValue(String value) {
		try {
			return Convert.toInt(value);
		} catch (Throwable e) {
			return 0;
		}
	}

	// Pega somente a parte inteira do valor;
	public static int getIntegerValueTruncated(double value) {
		return (int) value;
	}



	public static int getIntegerValueRoundUp(double value) {
		try {
			BigDecimal bd = BigDecimal.valueOf(value - 0.000000001).setScale(0, BigDecimal.ROUND_UP);
			return bd.intValue();
		} catch (Throwable e) {
			return 0;
		}
	}

	public static long getLongValue(String value) {
		try {
			return Convert.toLong(value);
		} catch (Throwable e) {
			return 0;
		}
	}

	


	public static double getDoubleSimpleValue(String value) {
		try {
			return Convert.toDouble(value);
		} catch (Throwable e) {
			return 0;
		}
	}


	/**
	 * Converte uma string com um valor numérico(1.500,00), retirando o
	 * caractere de milhar e trocando a vírgula para ponto,
	 * caso a string tenha um separador de decimal (',')
	 * 
	 * @param value
	 * @return String sem caractere de milhar e com a vírgula substituída por
	 *         ponto. Ex: 1500.00
	 */


	public static BigDecimal getBigDecimalValue(double value) {
		try {
			return BigDecimal.valueOf(value);
		} catch (Throwable e) {
			return BigDecimal.ZERO;
		}
	}



	public static boolean getBooleanValue(String value) {
		if (isNotEmpty(value)) {
			return VALOR_SIM.equals(value.toUpperCase());
		}
		return VALOR_SIM.equals(value);
	}

	// ------------------------------------------------------

	/**
	 * Converte o valor passado para millisegundos. Por exemplo: int millisecs =
	 * ValueUtil.toMillisegs(ValueUtil.MINUTES , 10);
	 * 
	 * @param type
	 *            Tipo do valor que está chegando por parâmetro: MINUTES ,
	 *            SECONDS , HOURS.
	 * @param value
	 *            Valor que deseja converter para millisegundos.
	 * @return int
	 * */
	public static int toMillisecs(char type, int value) {
		if (ValueUtil.isNotEmpty(value)) {
			if (type == MINUTES) {
				return value * 60000;
			} else if (type == SECONDS) {
				return value * 1000;
			} else if (type == HOURS) {
				return value * 3600000;
			}
		}
		return 0;
	}

	public static boolean valueEquals(Object value1, Object value2) {
		return (value1 == value2) || ((value1 != null) && value1.equals(value2));
	}
	
	public static boolean valueEqualsIfNotNull(Object value1, Object value2) {
		return value1 != null && value2 != null && valueEquals(value1, value2);
	}
	
	public static boolean valueNotEqualsIfNotNull(Object value1, Object value2) {
		return value1 != null && value2 != null && !valueEquals(value1, value2);
	}
	
	public static boolean valueEquals(int value1, int value2) {
		return value1 == value2;
	}

	public static boolean valueEquals(double value1, double value2) {
		return value1 == value2;
	}

	// -----------------------------------------------------------------

	public static Object find(Vector list, Object obj) {
		int index = list.indexOf(obj);
		if (index >= 0) {
			return list.items[index];
		} else {
			return null;
		}
	}

	public static boolean isValidNumberChar(char c) {
		return (VALID_NUMBER_CHARS == null) || (VALID_NUMBER_CHARS.indexOf(c) != -1);
	}

	public static boolean isValidNumber(String s) {
		if (isEmpty(s)) {
			return false;
		}
		// cria um array de char
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			// verifica se o char não é um dígito
			if (!ValueUtil.isValidNumberChar(c[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidAlphaNumericChar(char c) {
		return (VALID_ALPHA_CHARS == null) || (VALID_ALPHA_CHARS.indexOf(c) != -1);
	}

	public static String getValidNumbers(String value) {
		StringBuffer retorno = new StringBuffer();
		if (value != null) {
			char[] c = value.toCharArray();
			for (int i = 0; i < value.length(); i++) {
				if (isValidNumberChar(c[i])) {
					retorno.append(c[i]);
				}
			}
		}
		return retorno.toString();
	}
	
	public static String getValidAlphaNumerics(String value) {
		char[] c = value.toCharArray();
		StringBuffer retorno = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			if (isValidNumberChar(c[i]) || isValidAlphaNumericChar(c[i])) {
				retorno.append(c[i]);
			}
		}
		return retorno.toString();
	}
	
	public static double getDoubleValueTruncated(double value, int nuCasasDecimais) {
		double result = value;
	    String arg = "" + value;
	    int idx = arg.indexOf('.');
	    if (idx != -1) {
	        if (arg.length() > idx + nuCasasDecimais) {
	            arg = arg.substring(0, idx + nuCasasDecimais + 1);
	            result  = ValueUtil.getDoubleSimpleValue(arg);
	        }
	    }
	    return result;
	}
	
	public static String removeWhitespace(String value) {
		return value.replaceAll("\\s+", "");
	}
	
	
	public static boolean isRowkeyEmpty(String rowkey) {
		if (rowkey == null) return true;
		String[] values = rowkey.split(";");
		for (String value : values) {
			if (value.equals("null") || value == null) {
				return true;
			}
		}
		return false;
	}
	
    
	
	public static double toDouble(BigDecimal valor) {
		try {
			return valor != null ? valor.doubleValue() : 0d;
		} catch (InvalidNumberException e) {
			return 0d;
		}
	}
	
	public static int getIntOrDefaultValue(final String stringValue, final int defaultValue) {
		try {
			int intValue = ValueUtil.getIntegerValue(stringValue);
			return intValue > 0 ? intValue : defaultValue;
		} catch (Throwable e) {
			return defaultValue;
		}
	}
	
	
	public static int toSecs(int init) {
		return (Vm.getTimeStamp() - init) / 1000;
	}
	
}
