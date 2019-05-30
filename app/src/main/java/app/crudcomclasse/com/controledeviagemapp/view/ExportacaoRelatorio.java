package app.crudcomclasse.com.controledeviagemapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.Calendar;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.RelatorioController;

public class ExportacaoRelatorio extends AppCompatActivity {

    CalendarView calendarView;
    Calendar dataInicio = Calendar.getInstance();
    Calendar dataFim = Calendar.getInstance();
    ImageButton btnexportar;
    PDFView pdfView;
    Uri relatorio;

    @SuppressLint("WrongViewCast")
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
        btnexportar = findViewById(R.id.btnexportar);
        btnexportar.setVisibility(View.GONE);

        if (relatorio != null){
            File file = new File(relatorio.getPath());
            file.delete();
        }

    }

    public void exportarRelatorio(View view){
        dataInicio.set(Calendar.HOUR,00);
        dataInicio.set(Calendar.MINUTE,00);
        dataInicio.set(Calendar.SECOND,00);

        dataFim.set(Calendar.HOUR_OF_DAY,23);
        dataFim.set(Calendar.MINUTE,59);
        dataFim.set(Calendar.SECOND,59);



        String caminhoDoArquivo = new RelatorioController(this).exportarRelatorio(dataInicio.getTime().getTime(),dataFim.getTime().getTime());
        if (caminhoDoArquivo != null){
            Toast.makeText(this,"Relatório exportado com sucesso",Toast.LENGTH_LONG).show();
            calendarView = findViewById(R.id.calendario);
            pdfView = findViewById(R.id.pdf);
            calendarView.setVisibility(View.GONE);
            btnexportar.setVisibility(View.VISIBLE);
            File arquivo  = new File(caminhoDoArquivo);
            relatorio = Uri.fromFile(arquivo);
            pdfView.fromUri(relatorio).load();

        }else{
            Toast.makeText(this,"Falha na exportaçao",Toast.LENGTH_LONG).show();
        }
    }

    public void compartilharRelatorio(View view){
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, relatorio);
            sendIntent.setType("application/pdf");
            startActivity(Intent.createChooser(sendIntent, "Compartilhar"));
        }catch(Exception e ){
            e.getCause();
            e.printStackTrace();
        }
    }

    public void acaoVoltar(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
