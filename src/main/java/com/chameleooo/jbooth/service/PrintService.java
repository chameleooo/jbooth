package com.chameleooo.jbooth.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

@Service
public class PrintService {

    public static void main(String... args) throws IOException {
        BufferedImage image = ImageIO.read(new File("/Users/victor/Downloads/cham.png"));
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex != 0) {
                return Printable.NO_SUCH_PAGE;
            }
            graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            return Printable.PAGE_EXISTS;
        });
        try {
            printJob.print();
        } catch (PrinterException e1) {
            e1.printStackTrace();
        }
    }
}
