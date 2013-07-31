package org.catami.dolly;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mat on 30/07/13.
 */

@Service
public class LireSearcher {

    public LireSearcher() {}

    public ImageList search(String imagePath, String[] imageComparisonList, int limit, double similarityGreater, String featureType) throws IOException {
        List<String> comparisonListAsList = Arrays.asList(imageComparisonList);

        //intialise some things
        String indexPath = "lire-index";
        IndexReader ir = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
        ImageSearcher searcher;

        if(featureType.equals("gabor")) {
            searcher = ImageSearcherFactory.createGaborImageSearcher(100000);
        } else { //default to CEDD
            searcher = ImageSearcherFactory.createCEDDImageSearcher(100000);
        }

        //do the search
        ImageSearchHits hits = searcher.search(ImageIO.read(new File(imagePath)), ir);
        ImageList imageList = new ImageList();

        //pack the image list - go in reverse order, as the highest weighted images are at the top
        for (int j = 0; j < hits.length(); j++) {

            //pull out images with closeness score above the given
            if(hits.score(j) > similarityGreater) {
                String fileName = hits.doc(j).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];

                //only take the image from the subset we are interested in
                if(comparisonListAsList.contains(fileName)) {
                    imageList.add(new Image(fileName, hits.score(j)));
                }

                if(imageList.size() >= limit) break;
            }
        }

        return imageList;
    }
}
