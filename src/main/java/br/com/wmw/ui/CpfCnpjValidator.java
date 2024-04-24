package br.com.wmw.ui;

import br.com.wmw.util.StringUtil;
import br.com.wmw.util.ValueUtil;

public final class CpfCnpjValidator {
	
	public static final int LENGHT_CPF_CLEAN = 11;
	public static final int LENGHT_CNPJ_CLEAN = 14;
	public static final int LENGHT_RUC_CLEAN = 9;

	private CpfCnpjValidator(){

	}

	public static String cleanCpfCnpjRuc(String value) {
		if (ValueUtil.isEmpty(value)) return value;
		value = replaceAll(value,"-","");
		value = replaceAll(value,"/","");
		value = replaceAll(value,".","");
		return value;
	}

	public static String replaceAll(String value, String old, String novo) {
		if (ValueUtil.isEmpty(value) || ValueUtil.isEmpty(old)) return value;
		StringBuffer aux = new StringBuffer(40);
	    char c[] = value.toCharArray();
	    char cOld = old.charAt(0);
	    int len = c.length;
		for (int i = 0; i < len; i++) {
			aux.append((c[i] == cOld) ? novo : c[i]);
		}
		return aux.toString();
	}

	public static boolean isCpf(String value) {
		return (!ValueUtil.isEmpty(value) && cleanCpfCnpjRuc(value).length() == LENGHT_CPF_CLEAN);
	}

	public static boolean isCnpj(String value) {
		return (!ValueUtil.isEmpty(value) && cleanCpfCnpjRuc(value).length() == LENGHT_CNPJ_CLEAN);
	}
	
	public static boolean isRuc(String value) {
		return (ValueUtil.isNotEmpty(value) && cleanCpfCnpjRuc(value).length() == LENGHT_RUC_CLEAN);
	}

	public static boolean isCpfValid(String value) {
		if (!isCpf(value)) return false;
		if (value.length() > LENGHT_CPF_CLEAN) {
			value = cleanCpfCnpjRuc(value);
		}
		
		int d1 = 0, d2 = 0, digito1 = 0, digito2 = 0, resto = 0, digitoCPF;
		String nDigResult;
		int sizeFor = value.length() - 1;
		for (int n_Count = 1; n_Count < sizeFor; n_Count++) {
			digitoCPF = ValueUtil.getIntegerValue(value.substring(n_Count - 1, n_Count));
			// Multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por
			// diante.
			d1 = d1 + (11 - n_Count) * digitoCPF;

			// Para o segundo digito repita o procedimento incluindo o primeiro digito
			// calculado no passo anterior.
			d2 = d2 + (12 - n_Count) * digitoCPF;
		}

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o
		// resultado anterior.
		digito1 = (resto < 2) ? 0 : 11 - resto;

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o
		// resultado anterior.
		digito2 = (resto < 2) ? 0 : 11 - resto;

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = value.substring(value.length() - 2, value.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// Verificar se todos os numeros são iguais
		char ch = value.charAt(0);
		int size = 0;
		char c[] = value.toCharArray();
		int len = c.length;
		for (int i = 0; i < len; i++) {
			if (ch == c[i]) {
				size++;
			}
		}
		if (size == value.length()) {
			return false;
		}
		// Comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
		return nDigVerific.equals(nDigResult);
	}

	public static boolean isCnpjValid(String value) {
		if (!isCnpj(value)) return false;
		if (value.length() > LENGHT_CNPJ_CLEAN) {
			value = cleanCpfCnpjRuc(value);
		}
        if (value.equals("00000000000000") || value.equals("11111111111111") || value.equals("22222222222222") || value.equals("33333333333333") || 
        	value.equals("44444444444444") || value.equals("55555555555555") || value.equals("66666666666666") || value.equals("77777777777777") || 
            value.equals("88888888888888") || value.equals("99999999999999")) { 
        	return false; 
        }
        
		int soma = 0, dig;
		String cnpj_calc = value.substring(0,12);
		char[] chr_cnpj = value.toCharArray();

		/* Primeira parte */
		for (int i = 0; i < 4; i++) {
			if ((chr_cnpj[i]-48 >= 0) && (chr_cnpj[i]-48 <= 9)) {
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1)) ;
			}
		}
		for (int i = 0; i < 8; i++) {
			if ((chr_cnpj[i+4]-48 >=0) && (chr_cnpj[i+4]-48 <=9)) {
				soma += (chr_cnpj[i+4] - 48) * (10 - (i + 1)) ;
			}
		}
		dig = 11 - (soma % 11);
		cnpj_calc += ((dig == 10) || (dig == 11)) ? "0" : StringUtil.getStringValue(dig);

		/* Segunda parte */
		soma = 0;
		for (int i = 0; i < 5; i++) {
			if ((chr_cnpj[i]-48 >=0) && (chr_cnpj[i]-48 <=9)) {
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1)) ;
			}
		}
		for (int i = 0; i < 8; i++) {
			if ((chr_cnpj[i+5]-48 >=0) && (chr_cnpj[i+5]-48 <=9)) {
				soma += (chr_cnpj[i+5] - 48) * (10 - (i + 1)) ;
			}
		}
		dig = 11 - (soma % 11);
		cnpj_calc += ((dig == 10) || (dig == 11)) ? "0" : StringUtil.getStringValue(dig);

		return value.equals(cnpj_calc);
	}

	public static boolean validateCnpjCpf(String value){
		return (isCpf(value)) ? isCpfValid(value) : (isCnpj(value)) ? isCnpjValid(value) : false;
	}
	
	public static String invertirCadena(String cadena) {
        String cadenaInvertida = "";
        for (int x = cadena.length() - 1; x >= 0; x--) {
            cadenaInvertida = cadenaInvertida + cadena.charAt(x);
        }
        return cadenaInvertida;
    }
	
	public static boolean isRucValido(String nuRuc) {
		if (nuRuc.length() > LENGHT_RUC_CLEAN) {
			nuRuc = cleanCpfCnpjRuc(nuRuc);
		}
		String digitoVerificador = nuRuc.substring(nuRuc.length() - 1, nuRuc.length());
		String valorValidacao = nuRuc.substring(0, nuRuc.length() - 1 );
		return ValueUtil.valueEquals(digitoVerificador, nuDigitoVerificarRuc(valorValidacao));
	}
 
	private static String nuDigitoVerificarRuc(String numeroDigitado) {
		String numero = "";

		for (int i = 0; i < numeroDigitado.length(); i++) {
			char c = numeroDigitado.charAt(i);
			if(Character.isDigit(c)) {
				numero += c;
			} else {
				numero += (int) c;
			}
		}

		int k = 2;
		int total = 0;
		int numeroAux = 0;

		for(int i = numero.length() - 1; i >= 0; i--) {
			k = k > 11 ? 2 : k;
			numeroAux = numero.charAt(i) - 48;
			total += numeroAux * k++;
		}

		int resto = total % 11;
		resto = resto > 1 ? 11 - resto : 0;

		return StringUtil.getStringValue(resto);
	}
	
}