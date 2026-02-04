package com.example.onboardingAgent.onboardingAgent.utility;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class FreeOcrService {

    public String extractText(MultipartFile file) {
        try {
            // Load image via standard ImageIO
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new RuntimeException("Could not read image");
            }

            // Optional: force to PNG or something reliable (but JPEG usually works after jai)
            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // ImageIO.write(image, "png", baos);  // or "jpg"
            // Then use baos.toByteArray() if needed

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("F:\\onboarding-agent-ai-backend\\src\\main\\resources\\tessdata\\tessdata-main"); // your path
            tesseract.setLanguage("eng");

            // Tess4J can take BufferedImage directly (better!)
            return  tesseract.doOCR(image);

        } catch (Exception e) {
            throw new RuntimeException("OCR extraction failed", e);
        }
    }
}