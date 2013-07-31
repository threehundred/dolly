package org.catami.dolly;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.impl.ChainedDocumentBuilder;
import net.semanticmetadata.lire.utils.FileUtils;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mat on 30/07/13.
 */

@Service
public class LireIndexer {

    public LireIndexer() {}

    public void indexDirectory(String directoryPath) throws IOException {

        String indexPath = "lire-index";

        // Getting all images from a directory and its sub directories.
        ArrayList<String> images = FileUtils.getAllImages(new File(directoryPath), true);

        //create a list from the index, so we can search on it
        ArrayList<String> indexedFiles = new ArrayList<String>();
        try {
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            for(int i=0; i < indexReader.numDocs(); i++) {
                indexedFiles.add(indexReader.document(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0]);
            }
            indexReader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Use multiple DocumentBuilder instances:
        ChainedDocumentBuilder builder = new ChainedDocumentBuilder();
        builder.addBuilder(DocumentBuilderFactory.getGaborDocumentBuilder());
        builder.addBuilder(DocumentBuilderFactory.getCEDDDocumentBuilder());

        // Creating an Lucene IndexWriter
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_40, new WhitespaceAnalyzer(Version.LUCENE_40));
        IndexWriter iw = new IndexWriter(FSDirectory.open(new File(indexPath)), conf);

        // Iterating through images building the low level features
        for (Iterator<String> it = images.iterator(); it.hasNext(); ) {
            //if the index contains the image, don't index it
            String imageFilePath = it.next();
            if(!indexedFiles.contains(imageFilePath)) {
                System.out.println("Indexing " + imageFilePath);
                try {
                    BufferedImage img = ImageIO.read(new FileInputStream(imageFilePath));
                    Document document = builder.createDocument(img, imageFilePath);
                    iw.addDocument(document);
                } catch (Exception e) {
                    System.err.println("Error reading image or indexing it.");
                    e.printStackTrace();
                }
            }
        }

        // closing the IndexWriter
        iw.close();
        System.out.println("Finished indexing.");

    }
}
