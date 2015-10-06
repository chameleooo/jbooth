package com.chameleooo.jbooth.service;

import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;

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
import java.util.concurrent.TimeoutException;

@Service
public class PrintService {

    @Resource
    private PhotoService photoService;

    public void print(String filename) throws IOException {
        int exit;
        try {
            exit = new ProcessExecutor().command("lpr", filename)
                    .directory(photoService.getPictureDir())
                    .execute()
                    .getExitValue();
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
