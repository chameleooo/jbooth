package com.chameleooo.jbooth.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.IOException;

@Service
public class PrintService {

    @Resource
    private PhotoService photoService;

    public void print(String filename) throws IOException {
        BufferedImage image = ImageIO.read(photoService.getPicture(filename));
        PrinterJob printJob = PrinterJob.getPrinterJob();

        int scale = 72;
        printJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;

                g2d.scale(scale, scale);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                Paper paper = pageFormat.getPaper();
                paper.setSize(Math.floor(paper.getWidth() + 1), Math.floor(paper.getHeight() + 1));
                paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
                pageFormat.setPaper(paper);
                g2d.drawImage(image, 0, 0, 6, 4, null);
                return Printable.PAGE_EXISTS;
            }
        });

        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(new PrinterResolution(scale, scale, ResolutionSyntax.DPI));
        attributes.add(OrientationRequested.LANDSCAPE);
        attributes.add(MediaSizeName.JAPANESE_POSTCARD);

        try {
            printJob.print(attributes);
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
