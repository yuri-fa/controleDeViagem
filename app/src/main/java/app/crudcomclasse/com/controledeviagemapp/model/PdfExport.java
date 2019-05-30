package app.crudcomclasse.com.controledeviagemapp.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PdfExport {

    private static final String enderecoArquivo = Environment.getExternalStorageDirectory()+"/relatorios/relatorio_{nomeArquivo}.pdf";
    public static String FONT = Environment.getExternalStorageDirectory()+"resources/fonts/FreeSans.ttf";
    private static Font fontCabecalho = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,15, BaseColor.BLACK);
    private static Font fontCabecalhoColuna = FontFactory.getFont(FontFactory.TIMES_ITALIC,11, BaseColor.BLACK);
    private static Font fontColuna = FontFactory.getFont(FontFactory.TIMES_ROMAN,9, BaseColor.BLACK);

    private int tamanhoPadrao = 3;
    private int tamanhoDoCabecalho = 0;
    private List<Relatorio> relatoriosDeExportacaoList = null;


    public PdfExport(){

    }

    public String gerarRelatorio(List<Relatorio> relatorioList, Long diaDoRelatorio, Context context){
        Date date = new Date();
        date.setTime(diaDoRelatorio);
        relatoriosDeExportacaoList = relatorioList;
        if (verificandoPermissoes(context)){
            tamanhoDoCabecalho();
        }
        if (tamanhoDoCabecalho > 0) {
            try {
              return  criarArquivo(date);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
                return null;
            }
        }
        return null;
    }

    private boolean verificandoPermissoes(Context context) {
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Sem permissao para armazenagem", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String criarArquivo(Date data) throws IOException, DocumentException{
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        String nomeArquivoNovo = enderecoArquivo.replace("{nomeArquivo}",format.format(data));

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nomeArquivoNovo));
        document.open();
        Paragraph paragraph = new Paragraph("Relatório " + format.format(data),fontCabecalho);
        document.add(paragraph);
        paragraph = new Paragraph(" ");
        document.add(paragraph);

        PdfPTable table = new PdfPTable(tamanhoDoCabecalho + tamanhoPadrao);
        PdfPCell cell = new PdfPCell();
        Paragraph label = new Paragraph("Motorista",fontCabecalhoColuna);
        label.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(label);
        table.addCell(cell);

        cell = new PdfPCell();
        label = new Paragraph("Veículo",fontCabecalhoColuna);
        label.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(label);
        table.addCell(cell);

        colunasDoCabecalho(table);

        cell = new PdfPCell();
        label = new Paragraph("Peso Total",fontCabecalhoColuna);
        label.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(label);
        table.addCell(cell);

        colunasGeral(table);
        document.add(table);

        document.close();
        return nomeArquivoNovo;
    }

    private int tamanhoDoCabecalho() {
        for (Relatorio relatorio : relatoriosDeExportacaoList) {
            if (relatorio.getPlacas().size() > tamanhoDoCabecalho) {
                tamanhoDoCabecalho = relatorio.getPlacas().size();
            }
        }
        return tamanhoDoCabecalho;
    }

    private void colunasDoCabecalho(PdfPTable table){
        for (int i =0; i < tamanhoDoCabecalho;i++){
            PdfPCell cell = new PdfPCell();
            Paragraph label = new Paragraph("Peso da Viagem",fontCabecalhoColuna);
            label.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(label);
            table.addCell(cell);
        }
    }
    private void colunasGeral(PdfPTable table){
        for(Relatorio relatorio : relatoriosDeExportacaoList){
            PdfPCell cell = new PdfPCell();
            Paragraph label = new Paragraph(relatorio.getMotorista(),fontColuna);
            cell.addElement(label);
            table.addCell(cell);

            cell = new PdfPCell();
            label = new Paragraph(relatorio.getVeiculo(),fontColuna);
            cell.addElement(label);
            table.addCell(cell);


            for(String placa : relatorio.getPlacas()){
                cell = new PdfPCell();
                label = new Paragraph(placa,fontColuna);
                cell.addElement(label);
                table.addCell(cell);
            }

            int quantidadeDeColunasVazias = (tamanhoDoCabecalho - tamanhoPadrao) - relatorio.getPlacas().size();

            for (int colunaVazia = 0; colunaVazia < quantidadeDeColunasVazias; colunaVazia++){
                cell = new PdfPCell();
                label = new Paragraph("0",fontColuna);
                cell.addElement(label);
                table.addCell(cell);
            }
            cell = new PdfPCell();
            label = new Paragraph(relatorio.getPesoTotal(),fontColuna);
            cell.addElement(label);
            table.addCell(cell);
        }
    }
}
