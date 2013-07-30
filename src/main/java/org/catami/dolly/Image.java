package org.catami.dolly;

import java.util.ArrayList;

/**
 * Created by mat on 30/07/13.
 */
public class Image {

    public String path;
    public float similarityScore;

    public Image(String path, float similarityScore) {
        this.path = path;
        this.similarityScore = similarityScore;
    }

    public String getPath() {
        return path;
    }

    public float getSimilarityScore() {
        return similarityScore;
    }
}

class ImageList extends ArrayList<Image> {}