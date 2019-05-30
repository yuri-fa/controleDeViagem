package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.ViagemController;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class RelatoriosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RelatorioViagensAdapter relatorioViagensAdapter;
    private CalendarView calendarView;
    private static Calendar dataInicio = Calendar.getInstance();
    private static Calendar dataFim = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorios);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewViagens);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        calendarView = findViewById(R.id.calendario_viagens);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dataInicio.set(year,month,dayOfMonth,0,0,0);
                dataFim.set(year,month,dayOfMonth,23,59,59);
            }
        });
    }

    public void pesquisarViagens(View view){
        List<Viagem> viagemList = new ViagemController(this).pesquisarViagens(dataInicio.getTime(),dataFim.getTime());
        if (viagemList == null || viagemList.size() < 1){
            Toast.makeText(view.getContext(),"Nenhuma viagem foi registrada na data informada",Toast.LENGTH_SHORT);
        }
        relatorioViagensAdapter = new RelatorioViagensAdapter(recyclerView,viagemList,this);
        recyclerView.setAdapter(relatorioViagensAdapter);
    }

    public static Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public static Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }
}
