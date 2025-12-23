// com.rec.service.PdfReportService.java
package com.rec.service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rec.dto.FaceMatchDto;

@Service
public class PdfReportService {

	@Value("${aws.s3.bucket.name}")
	private String criminalBucket;

	@Value("${aws.s3.region}")
	private String region;

	public byte[] generateMatchReport(FaceMatchDto bestMatch, String sketchImageUrl) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter.getInstance(document, baos);
		document.open();

// Title
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
		Paragraph title = new Paragraph("Facial Recognition Match Report", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(20f);
		document.add(title);

// Time
		Font infoFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
		String ts = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Paragraph info = new Paragraph("Report generated: " + ts, infoFont);
		info.setSpacingAfter(15f);
		document.add(info);

// ================= IMAGES ROW (Sketch + Best match) =================
		if (sketchImageUrl != null && !sketchImageUrl.isEmpty() && bestMatch != null
				&& bestMatch.getImageUrl() != null) {

			Image sketchImg = Image.getInstance(new URL(sketchImageUrl));
			Image matchImg = Image.getInstance(new URL(bestMatch.getImageUrl()));

			float pageWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
			float targetWidth = pageWidth / 2f - 10f;

			sketchImg.scaleToFit(targetWidth, 300f);
			matchImg.scaleToFit(targetWidth, 300f);

			PdfPTable imgTable = new PdfPTable(2);
			imgTable.setWidthPercentage(100);
			imgTable.setSpacingAfter(20f);

			PdfPCell sketchCell = new PdfPCell(sketchImg, true);
			sketchCell.setBorder(Rectangle.NO_BORDER);
			sketchCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell matchCell = new PdfPCell(matchImg, true);
			matchCell.setBorder(Rectangle.NO_BORDER);
			matchCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			imgTable.addCell(sketchCell);
			imgTable.addCell(matchCell);

			document.add(imgTable);
		}

// ================= MATCH DETAILS =================
		Font headingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);

// Heading in colored cell
		PdfPTable matchHeadingTable = new PdfPTable(1);
		matchHeadingTable.setWidthPercentage(100);
		PdfPCell matchHeadingCell = new PdfPCell(new Phrase("Match Details", headingFont));
		matchHeadingCell.setBackgroundColor(BaseColor.DARK_GRAY);
		matchHeadingCell.setPaddingTop(6f);
		matchHeadingCell.setPaddingBottom(6f);
		matchHeadingCell.setBorder(Rectangle.NO_BORDER);
		matchHeadingTable.addCell(matchHeadingCell);
		document.add(matchHeadingTable);

		PdfPTable matchTable = new PdfPTable(2);
		matchTable.setWidthPercentage(100);
		matchTable.setSpacingBefore(10f);
		matchTable.setSpacingAfter(20f);

		Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
		Font valFont = new Font(Font.FontFamily.HELVETICA, 11);

		addRow(matchTable, "Person ID:", bestMatch != null ? bestMatch.getPersonId() : "N/A", labelFont, valFont);
		addRow(matchTable, "Similarity:",
				bestMatch != null ? String.format("%.2f%%", bestMatch.getSimilarity()) : "N/A", labelFont, valFont);
		addRow(matchTable, "Name:", nonNull(bestMatch != null ? bestMatch.getPersonName() : null), labelFont, valFont);
		addRow(matchTable, "Notes:", nonNull(bestMatch != null ? bestMatch.getPersonNotes() : null), labelFont,
				valFont);

		document.add(matchTable);

// ================= PERSONAL DETAILS =================
		PdfPTable detailHeadingTable = new PdfPTable(1);
		detailHeadingTable.setWidthPercentage(100);
		PdfPCell detailHeadingCell = new PdfPCell(new Phrase("Personal Details", headingFont));
		detailHeadingCell.setBackgroundColor(BaseColor.DARK_GRAY);
		detailHeadingCell.setPaddingTop(6f);
		detailHeadingCell.setPaddingBottom(6f);
		detailHeadingCell.setBorder(Rectangle.NO_BORDER);
		detailHeadingTable.addCell(detailHeadingCell);
		document.add(detailHeadingTable);

		PdfPTable detailTable = new PdfPTable(2);
		detailTable.setWidthPercentage(100);
		detailTable.setSpacingBefore(10f);
		detailTable.setSpacingAfter(20f);

		addRow(detailTable, "Gender:", nonNull(bestMatch != null ? bestMatch.getGender() : null), labelFont, valFont);
		addRow(detailTable, "Height:",
				bestMatch != null && bestMatch.getHeight() != null ? bestMatch.getHeight() + " cm" : "N/A", labelFont,
				valFont);
		addRow(detailTable, "Identification Marks:",
				nonNull(bestMatch != null ? bestMatch.getIdentificationMarks() : null), labelFont, valFont);
		addRow(detailTable, "Date of Birth:", nonNull(bestMatch != null ? bestMatch.getDateOfBirth() : null), labelFont,
				valFont);

		document.add(detailTable);

// footer
		Font footFont = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY);
		Paragraph foot = new Paragraph("Automatically generated by Forensic Face Detection System", footFont);
		foot.setAlignment(Element.ALIGN_CENTER);
		document.add(foot);

		document.close();
		return baos.toByteArray();
	}

	private void addRow(PdfPTable t, String label, String value, Font labelFont, Font valFont) {
		PdfPCell l = new PdfPCell(new Phrase(label, labelFont));
		l.setPadding(6f);
		l.setBackgroundColor(new BaseColor(240, 240, 240));

		PdfPCell v = new PdfPCell(new Phrase(value, valFont));
		v.setPadding(6f);

		t.addCell(l);
		t.addCell(v);
	}

	private String nonNull(String v) {
		return v != null && !v.isEmpty() ? v : "N/A";
	}

//	public byte[] generateMatchReport(FaceMatchDto bestMatch, String sketchS3Key) throws DocumentException {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//		PdfWriter.getInstance(document, baos);
//		document.open();
//
//		// Title
//		Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
//		Paragraph title = new Paragraph("Facial Recognition Match Report", titleFont);
//		title.setAlignment(Element.ALIGN_CENTER);
//		title.setSpacingAfter(20);
//		document.add(title);
//
//		// Report info
//		Font infoFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
//		String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//		Paragraph reportInfo = new Paragraph("Report Generated: " + timestamp, infoFont);
//		reportInfo.setSpacingAfter(20);
//		document.add(reportInfo);
//
//		// Separator line
//		document.add(new Paragraph(" "));
//
//		// Match Details Table
//		Font headingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
//		Paragraph heading = new Paragraph("Match Details", headingFont);
//		heading.setSpacingBefore(10f);
//		heading.setSpacingAfter(10f);
//		document.add(heading);
//
//		PdfPTable table = new PdfPTable(2);
//		table.setWidthPercentage(100);
//		table.setSpacingBefore(10);
//		table.setSpacingAfter(20);
//
//		Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
//		Font valueFont = new Font(Font.FontFamily.HELVETICA, 11);
//
//		addTableRow(table, "Person ID:", bestMatch.getPersonId(), labelFont, valueFont);
//		addTableRow(table, "Similarity Score:", String.format("%.2f%%", bestMatch.getSimilarity()), labelFont,
//				valueFont);
//		addTableRow(table, "Name:", bestMatch.getPersonName() != null ? bestMatch.getPersonName() : "N/A", labelFont,
//				valueFont);
//		addTableRow(table, "Notes:", bestMatch.getPersonNotes() != null ? bestMatch.getPersonNotes() : "N/A", labelFont,
//				valueFont);
//
//		document.add(table);
//
//		// Personal Details heading in colored cell
//		PdfPTable detailHeadingTable = new PdfPTable(1);
//		detailHeadingTable.setWidthPercentage(100);
//
//		PdfPCell detailHeadingCell = new PdfPCell(new Phrase("Personal Details", headingFont));
//		detailHeadingCell.setBackgroundColor(BaseColor.DARK_GRAY);
//		detailHeadingCell.setPaddingTop(8f);
//		detailHeadingCell.setPaddingBottom(8f);
//		detailHeadingCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		detailHeadingCell.setBorder(Rectangle.NO_BORDER);
//
//		detailHeadingTable.addCell(detailHeadingCell);
//		document.add(detailHeadingTable);
//
//		// Personal Details Table
//		PdfPTable detailTable = new PdfPTable(2);
//		detailTable.setWidthPercentage(100);
//		detailTable.setSpacingBefore(10f);
//		detailTable.setSpacingAfter(20f);
//
//		addTableRow(detailTable, "Gender:", bestMatch.getGender() != null ? bestMatch.getGender() : "N/A", labelFont,
//				valueFont);
//		addTableRow(detailTable, "Height:", bestMatch.getHeight() != null ? bestMatch.getHeight() + " cm" : "N/A",
//				labelFont, valueFont);
//		addTableRow(detailTable, "Identification Marks:",
//				bestMatch.getIdentificationMarks() != null ? bestMatch.getIdentificationMarks() : "N/A", labelFont,
//				valueFont);
//		addTableRow(detailTable, "Date of Birth:",
//				bestMatch.getDateOfBirth() != null ? bestMatch.getDateOfBirth() : "N/A", labelFont, valueFont);
//
//		document.add(detailTable);
//
//		document.add(new Paragraph(" "));
//
//		// Footer
//		Font footerFont = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY);
//		Paragraph footer = new Paragraph("This is an automatically generated report by Forensic Face Detection System",
//				footerFont);
//		footer.setAlignment(Element.ALIGN_CENTER);
//		document.add(footer);
//
//		document.close();
//		return baos.toByteArray();
//	}

	private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
		PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
		labelCell.setPadding(8);
		labelCell.setBackgroundColor(new BaseColor(240, 240, 240));

		PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
		valueCell.setPadding(8);

		table.addCell(labelCell);
		table.addCell(valueCell);
	}
}
