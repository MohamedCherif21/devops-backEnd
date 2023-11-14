package com.example.cocomarket.services;

import com.example.cocomarket.entity.Contrat;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ContratPDFExport {

    private List<Contrat> listContrat;


    public ContratPDFExport(List<Contrat> listContrat) {
        this.listContrat = listContrat;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("User ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date Debut Contrat", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date Fin Contrat", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Commision ",font ));
        table.addCell(cell);


    }

    private void writeTableData(PdfPTable table) {
        for (Contrat user : listContrat) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getDescription());
            table.addCell(user.getDateFinContrat().toString());
            table.addCell(user.getDateDebutContrat().toString());
            table.addCell(user.getCommision().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Contrat", font);
        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}

