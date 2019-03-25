package app.crudcomclasse.com.controledeviagemapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ControleViagemUtil {

    public static String formatarDataParaString(Date data){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(data);
    }

    public static Date formatarStringParaData(String data){
        Date date = new Date();
        date.setTime(Long.parseLong(data));
        return date;
    }

}
