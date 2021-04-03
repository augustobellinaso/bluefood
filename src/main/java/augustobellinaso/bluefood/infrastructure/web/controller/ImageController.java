package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.application.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ResponseBody
    @GetMapping(path = "/images/{type}/{imgName}")
    public byte[] getBytes(
            @PathVariable("type") String type,
            @PathVariable("imgName") String imgName){

        return imageService.getBytes(type, imgName);

    }
}
