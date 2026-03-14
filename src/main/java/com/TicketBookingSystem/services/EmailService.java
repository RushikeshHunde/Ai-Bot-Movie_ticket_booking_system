package com.TicketBookingSystem.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.TicketBookingSystem.model.Booking;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public byte[] generatePdfBytes(Booking booking) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // A6 is a great size for mobile-friendly digital tickets
            Document document = new Document(PageSize.A6, 20, 20, 20, 20);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 1. STYLING & FONTS
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.WHITE);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.GRAY);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

            // 2. TICKET HEADER (Dark Background)
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            PdfPCell headerCell = new PdfPCell(new Phrase("CINEPASS TICKET", titleFont));
            headerCell.setBackgroundColor(new Color(26, 26, 26)); // Dark slate
            headerCell.setPadding(10);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(headerCell);
            document.add(headerTable);

            // 3. MOVIE POSTER IMAGE
            try {
                String imageUrl = booking.getShow().getMovieImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image poster = Image.getInstance(imageUrl);
                    poster.scaleToFit(PageSize.A6.getWidth() - 40, 180);
                    poster.setAlignment(Element.ALIGN_CENTER);
                    poster.setSpacingBefore(10);
                    document.add(poster);
                }
            } catch (Exception e) {
                document.add(new Paragraph("\n[Poster Not Available]\n", valueFont));
            }

            // 4. TICKET DETAILS TABLE
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            detailsTable.setSpacingBefore(15);

            // Movie Title (Span 2 columns)
            PdfPCell movieCell = new PdfPCell(new Phrase(booking.getShow().getMovieTitle().toUpperCase(),
                                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            movieCell.setColspan(2);
            movieCell.setBorder(Rectangle.NO_BORDER);
            movieCell.setPaddingBottom(10);
            detailsTable.addCell(movieCell);

            // Rows: Labels and Values
            addDetailRow(detailsTable, "BOOKING ID", "#" + booking.getBookingId(), labelFont, valueFont);
            addDetailRow(detailsTable, "NAME", (booking.getUser() != null ? booking.getUser().getName() : "Guest"), labelFont, valueFont);
            addDetailRow(detailsTable, "SEATS", booking.getSeatsBooked(), labelFont, valueFont);
            addDetailRow(detailsTable, "AMOUNT", "₹" + booking.getTotalAmount(), labelFont, valueFont);

            document.add(detailsTable);

            // 5. FOOTER (Dashed Line & Note)
            document.add(new Paragraph("\n-------------------------------------------", labelFont));
            Paragraph footer = new Paragraph("Enjoy your movie! Please present this PDF at the entrance.",
                                            FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("CRITICAL PDF ERROR: " + e.getMessage());
            return null;
        }
    }

    // Helper method to keep code clean
    private void addDetailRow(PdfPTable table, String label, String value, Font lFont, Font vFont) {
        PdfPCell lCell = new PdfPCell(new Phrase(label, lFont));
        lCell.setBorder(Rectangle.NO_BORDER);
        lCell.setPaddingBottom(5);
        table.addCell(lCell);

        PdfPCell vCell = new PdfPCell(new Phrase(value, vFont));
        vCell.setBorder(Rectangle.NO_BORDER);
        vCell.setPaddingBottom(5);
        table.addCell(vCell);
    }

    public void sendTicketWithPdf(Booking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Ensure we use the email entered during booking
            String recipientEmail = (booking.getUser() != null) ? booking.getUser().getEmail() : null;

            if (recipientEmail == null) {
                System.err.println("Error: No email found for booking ID " + booking.getBookingId());
                return;
            }

            helper.setTo(recipientEmail);
            helper.setSubject("🎟️ Your CinePass Ticket: " + booking.getShow().getMovieTitle());

            String htmlContent = "<h3>Hi " + (booking.getUser() != null ? booking.getUser().getName() : "Customer") + ",</h3>"
                               + "<p>Thank you for choosing CinePass. Your booking is confirmed!</p>"
                               + "<p><b>Booking ID:</b> " + booking.getBookingId() + "</p>"
                               + "<p>Please find your ticket attached below.</p>";

            helper.setText(htmlContent, true);

            byte[] bytes = generatePdfBytes(booking);
            if(bytes != null) {
                helper.addAttachment("CinePass_Ticket_" + booking.getBookingId() + ".pdf", new ByteArrayResource(bytes));
                mailSender.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}