package com.chameleooo.jbooth.controller;

import org.gphoto2.Camera;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@Controller
public class WelcomeController {

    @RequestMapping(value = "")
    public String index(Map<String, Object> model) {

        try (Camera camera = new Camera()) {
            camera.initialize();
            camera.captureImage().save("hey.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Fill the model with data
        model.put("key", "value");
        //Return a template name
        return "welcome";
    }

}
