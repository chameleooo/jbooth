package com.chameleooo.jbooth.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

@Service
public class PrintService {

    @Resource
    private PhotoService photoService;

    public void print(String filename) throws IOException {
        BufferedImage image = ImageIO.read(photoService.getPicture(filename));
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
