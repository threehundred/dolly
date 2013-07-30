package org.catami.dolly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mat on 30/07/13.
 */

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private LireIndexer lireIndexer;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String index(@RequestParam("imagePath") String imagePath) {

        //index the image
        //lireIndexer.index(imagePath);

        //if all goes well return a good response to the user
        return "";
    }

}