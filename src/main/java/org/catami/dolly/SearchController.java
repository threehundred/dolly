package org.catami.dolly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by mat on 30/07/13.
 */

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private LireSearcher lireSearcher;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ImageList search(@RequestParam("imagePath") String imagePath,
                                          @RequestParam("imageComparisonList") String[] imageComparisonList,
                                          @RequestParam("limit") int limit,
                                          @RequestParam("similarityGreater") double similarityGreater,
                                          @RequestParam("featureType") String featureType) throws IOException {

        //search for the similar images
        return lireSearcher.search(imagePath, imageComparisonList, limit, similarityGreater, featureType);
    }

}
