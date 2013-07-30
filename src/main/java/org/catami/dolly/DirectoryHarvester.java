package org.catami.dolly;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mat on 30/07/13.
 *
 * This class monitors a given directory, compares the file contents with the lire index,
 * and then indexes non indexed images.
 *
 */

public class DirectoryHarvester extends TimerTask {

    private String directoryToWatch;
    private long frequencyOfHarvestInSeconds;
    private Timer timer;

    public DirectoryHarvester(String directoryToWatch, long frequencyOfHarvestInSeconds) {

        this.directoryToWatch = directoryToWatch;

        //set up the timer to run the harvest job every so many seconds
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(this, 0, frequencyOfHarvestInSeconds*1000);
    }

    @Override
    public synchronized void run() {
        //run the indexer - we don't need to worry about much, because Lire does not overwrite the index
        //it just appends new entries
        try {
            new LireIndexer().indexDirectory(this.directoryToWatch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

