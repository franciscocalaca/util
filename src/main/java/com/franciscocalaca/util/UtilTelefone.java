package com.franciscocalaca.util;

public class UtilTelefone {
	
	public static String ajustarTelefone(String telefone) {
		telefone = telefone.replaceAll("[^0-9]", "");
		if(telefone.startsWith("0")) {
			telefone = telefone.substring(1);
		}
		if (telefone == null) {
			return null;
		} else if (telefone.length() == 10) {
			return telefone;
		} else if (telefone.length() > 10) {
			String prefixo = telefone.substring(0, 2);
			String resto;
			if(prefixo.startsWith("1")) {
				resto = telefone.substring(telefone.length() - 9);
			}else {
				resto = telefone.substring(telefone.length() - 8);
			}
			return prefixo + resto;
		} else {
			return null;
		}
	}

}
