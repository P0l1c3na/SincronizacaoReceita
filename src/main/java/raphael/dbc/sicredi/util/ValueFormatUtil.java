package raphael.dbc.sicredi.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class ValueFormatUtil {

    private ValueFormatUtil() {
    }

    public static Double stringToDouble(String value) throws ParseException {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            Number number = format.parse(value);
            return number.doubleValue();
        } catch (ParseException e) {
            log.error("Erro ao transformar o valor {} pra double ", value, e);
            throw e;
        }

    }

    public static String doubleToString(Double value) {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            nf.setGroupingUsed(false);
            nf.setMinimumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            return df.format(value);
        } catch (Exception e){
            log.error("Erro ao transformar o valor {} pra string ", value, e);
            throw e;
        }
    }
}
