package com.chameleooo.jbooth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

@Service
public class PhotoService {

    @Value("#{systemProperties['user.dir']}")
    private String workingDir;

    public String takePicture() {
        String outputFilename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".jpg";
        int exit;
        try {
            exit = new ProcessExecutor().command("gphoto2", "--capture-image-and-download", "--filename", outputFilename)
                    .directory(getPictureDir())
                    .execute()
                    .getExitValue();
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }

        return outputFilename;
    }

    public File getPicture(String filename) {
        File picture = new File(getPictureDir(), filename);
        if (picture.exists()) {
            return picture;
        } else {
            return null;
        }
    }

    public File getPictureDir() {
        String pictureDirectoryPath = workingDir + File.separator + "picture";
        File pictureDirectory = new File(pictureDirectoryPath);

        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdir();
        }

        return pictureDirectory;
    }
}
