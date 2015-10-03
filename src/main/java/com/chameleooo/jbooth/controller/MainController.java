package com.chameleooo.jbooth.controller;

import com.chameleooo.jbooth.service.PhotoService;
import com.chameleooo.jbooth.service.PrintService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
public class MainController {

    @Resource
    private PhotoService photoService;

    @Resource
    private PrintService printService;

    @RequestMapping(value = "")
    public String index(Map<String, Object> model) {
        //Return a template name
        return "welcome";
    }

    @RequestMapping(value = "get-ready")
    public String getReady(Map<String, Object> model) {
        //Return a template name
        return "get-ready";
    }

    @RequestMapping(value = "take-picture")
    public @ResponseBody PicutreDto takePicture(Map<String, String> result) {
        String filename = photoService.takePicture();
        return new PicutreDto(filename);
    }

    @RequestMapping(value = "print/{filename:.+}")
    public String print(@PathVariable("filename") String filename, Map<String, Object> model) throws IOException {
        printService.print(filename);
        return "";
    }


    @RequestMapping(value = "get-picture/{filename:.+}")
    public ResponseEntity<byte[]> getPicture(@PathVariable("filename") String filename, HttpServletRequest request) throws IOException {
        InputStream in = new FileInputStream(photoService.getPicture(filename));
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "show-picture/{filename:.+}")
    public String showPicture(@PathVariable("filename") String filename, Map<String, Object> model) throws IOException {
        model.put("filename", filename);
        return "show-picture";
    }


    public class PicutreDto {
        private String filename;

        public PicutreDto(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }

}
