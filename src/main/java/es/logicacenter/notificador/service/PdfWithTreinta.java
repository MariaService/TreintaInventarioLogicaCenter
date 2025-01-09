package es.logicacenter.notificador.service;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfWithTreinta {

	public String  GenerarPdf(String paranmetro, List<String> news, String nombreTienda) {
		// Ruta donde se guardará el PDF
		String pdfFilePath = paranmetro;

		// Crear un documento
		Document document = new Document();

		try {
			// Crear un escritor para el documento
			PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));

			// Abrir el documento
			document.open();

			// Agregar un título
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
			Paragraph title = new Paragraph("Inventario " + nombreTienda, titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			document.add(new Paragraph("\n")); // Salto de línea
			PdfPTable table = new PdfPTable(1); // Número de columnas
			// Encabezado de la tabla
			PdfPCell headerCell = new PdfPCell(
					new Paragraph("Descripción", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 200)); // Color de fondo gris claro
			table.addCell(headerCell);

			// Datos de la tabla (dinámicos)
			List<String> data = news; // Método que devuelve los datos
			for (String cellData : data) {
				table.addCell(cellData);
			}

			// Agregar la tabla al documento
			document.add(table);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		} finally {
			// Cerrar el documento
			document.close();
			
		}
		return pdfFilePath;
	}
}
