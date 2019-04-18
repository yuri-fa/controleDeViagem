package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.RelatorioController;

public class ExportacaoRelatorio extends AppCompatActivity {

    CalendarView calendarView;
    Calendar dataInicio = Calendar.getInstance();
    Calendar dataFim = Calendar.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exportacao_relatorio);
        calendarView = findViewById(R.id.calendario);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dataInicio.set(year,month,dayOfMonth,0,0,0);
                dataFim.set(year,month,dayOfMonth,23,59,59);
            }
        });
    }

    public void exportarRelatorio(View view){
        boolean isSucesso = new RelatorioController(this).exportarRelatorio(dataInicio.getTime(),dataFim.getTime());
        if (isSucesso){
            Toast.makeText(this,"Relatorio exportado com sucesso",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Falha na exportaçao",Toast.LENGTH_LONG).show();
        }
    }
}
