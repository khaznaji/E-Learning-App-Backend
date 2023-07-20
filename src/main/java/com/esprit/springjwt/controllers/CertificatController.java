package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Etudiant;
import com.esprit.springjwt.repository.EtudiantRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
@RestController
@RequestMapping("/api/certif")

public class CertificatController {
    @Autowired
    protected EtudiantRepository Etudiantrepository;
    @PostMapping("/Generer")
    public void genererCertificat(@RequestBody String c) throws RuntimeException, IOException, DocumentException {
        try {
            Object obj = new JSONParser().parse(c);
            JSONObject jo = (JSONObject) obj;

            String liste = (String) jo.get("liste");
            String periode = (String) jo.get("periode");
            String nom_formation = (String) jo.get("nom_formation");
            String month = (String) jo.get("month");
            String date = (String) jo.get("date");

            File f = new File("C:\\Users\\DELL\\Desktop\\Certifications\\" + nom_formation + " " + month);
            if (f.mkdir()) {
                System.out.println("Directory has been created successfully");
            } else {
                System.out.println("Directory cannot be created");
            }

            String[] names = liste.split(",");  // Split the names using comma as the separator

            for (String name : names) {
                name = name.trim();  // Trim any leading/trailing spaces
                String pdfname = f.getAbsolutePath() + "\\" + name + ".pdf";

                Document document = new Document();
                document.setPageSize(PageSize.A4.rotate());

                try {
                    FileOutputStream fo = new FileOutputStream(new File(pdfname));
                    PdfWriter writer = PdfWriter.getInstance(document, fo);

                    document.open();

                    PdfContentByte canvas = writer.getDirectContentUnder();
                    Image image = Image.getInstance("src/main/resources/certif2.jpg");
                    image.scaleAbsolute(PageSize.A4.rotate());
                    image.setAbsolutePosition(0, 0);
                    canvas.addImage(image);

                    float pos = (document.getPageSize().getWidth() / 2) - (name.length() * 18 / 2);
                    FixText(name, "savoyeplain.ttf", "Savoye", pos, 240, writer, 60);

                    certificate_footer(writer, name, periode, nom_formation, month);

                    FixText(date, "poppins.regular.ttf", "Poppins", 280, 100, writer, 13);

                    Etudiant e = Etudiantrepository.findByName(name);

                    String str = "";
                    if (e != null) {
                        str = "http://localhost:4200/profil/" + e.getIdEtudiant();
                    } else {
                        str = "https://www.facebook.com/9antra.tn/";
                    }

                    BarcodeQRCode my_code = new BarcodeQRCode(str, 100, 100, null);
                    Image qr_image = my_code.getImage();
                    qr_image.setAbsolutePosition(70, 60);
                    document.add(qr_image);

                    document.close();
                    writer.close();
                    fo.close();
                    System.out.println("Done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.print(nom_formation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void FixText(String text, String fontfile, String fontname, float x, int y,PdfWriter writer,int size) {
        PdfContentByte cb = writer.getDirectContent();


        FontFactory.register("C:\\Users\\DELL\\Desktop\\Certif\\backend\\src\\main\\resources\\fonts\\"+fontfile);
        Font textFont = FontFactory.getFont(fontname, BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED, 10);
        BaseFont bf = textFont.getBaseFont();

        cb.saveState();
        cb.beginText();
        cb.moveText(x, y);
        cb.setFontAndSize(bf, size);
        cb.showText(text);
        cb.endText();
        cb.restoreState();
    }
    private static void certificate_footer(PdfWriter writer, String name, String periode, String formation, String month ) {

        PdfContentByte cb = writer.getDirectContent();


        FontFactory.register("C:\\Users\\DELL\\Desktop\\Certif\\backend\\src\\main\\resources\\fonts\\Poppins-Thin.ttf");
        Font textFont = FontFactory.getFont("Poppins Thin", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED, 10);
        BaseFont bf = textFont.getBaseFont();
        FontFactory.register("C:\\Users\\DELL\\Desktop\\Certif\\backend\\src\\main\\resources\\fonts\\poppins.regular.ttf");
        Font textFont2 = FontFactory.getFont("Poppins", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED, 10);
        BaseFont bf2 = textFont2.getBaseFont();

        cb.saveState();
        cb.beginText();
        cb.moveText(180, 185);
        cb.setFontAndSize(bf, 14);
        String begin = "This is to certify that";
        cb.showText(begin);
        cb.endText();

        cb.beginText();
        cb.setFontAndSize(bf2, 14);
        float pos_name = 180+cb.getEffectiveStringWidth(begin, false);
        cb.moveText(pos_name, 185);
        cb.showText(name);
        cb.endText();

        cb.beginText();
        cb.setFontAndSize(bf, 14);
        float pos_text2 = pos_name+cb.getEffectiveStringWidth(name, false);
        cb.moveText(pos_text2 + 10, 185);
        String next ="successfully completed ";
        cb.showText(next);
        cb.endText();


        cb.beginText();
        cb.setFontAndSize(bf2, 14);
        float pos_mot2 = pos_text2 +cb.getEffectiveStringWidth(next, false);
        cb.moveText(pos_mot2 + 7, 185);
        cb.showText(periode);
        cb.endText();

        cb.beginText();
        cb.moveText(180,165);
        cb.setFontAndSize(bf, 14);
        String of = "of";
        cb.showText(of);
        cb.endText();

        cb.beginText();
        cb.setFontAndSize(bf2, 14);
        float pos_formation = 180+cb.getEffectiveStringWidth(of, false);
        cb.moveText(pos_formation + 7, 165);
        cb.showText(formation);
        cb.endText();

        cb.beginText();
        cb.setFontAndSize(bf, 14);
        float pos_text3 = pos_formation+cb.getEffectiveStringWidth(formation, false);
        cb.moveText(pos_text3 + 20, 165);
        String next2 = "training and coaching on";
        cb.showText(next2);
        cb.endText();

        cb.beginText();
        cb.setFontAndSize(bf2, 14);
        float pos_month = pos_text3+cb.getEffectiveStringWidth(next2, false);
        cb.moveText(pos_month + 20, 165);
        cb.showText(month);
        cb.endText();

        cb.restoreState();
    }

}
