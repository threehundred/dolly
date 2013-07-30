package org.catami.dolly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mat on 30/07/13.
 */

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private LireSearcher lireSearcher;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ImageList search(@RequestParam("imagePath") String imagePath,
                                          @RequestParam("imageComparisonList") String[] imageComparisonList,
                                          @RequestParam("limit") int limit) throws IOException {

        //search for the similar images
        return lireSearcher.search(imagePath, imageComparisonList, limit);
    }

}
