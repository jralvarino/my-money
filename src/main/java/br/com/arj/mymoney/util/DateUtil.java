package br.com.arj.mymoney.util;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	private DateUtil() {

	}

	public static Date getProximoMes(Date dataVencimento) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataVencimento);
		cal.add(Calendar.MONTH, 1);

		return cal.getTime();
	}

}
