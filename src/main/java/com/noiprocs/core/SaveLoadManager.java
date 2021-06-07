package com.noiprocs.core;

import com.noiprocs.core.config.Config;
import com.noiprocs.core.model.ServerModelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class SaveLoadManager {
    private static final Logger logger = LogManager.getLogger(SaveLoadManager.class);

    public static void saveGameData(ServerModelManager serverModelManager) {
        logger.info("Saving data to " + Config.SAVE_FILE_NAME + " ...");

        try {
            FileOutputStream f = new FileOutputStream(Config.SAVE_FILE_NAME);
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(serverModelManager);

            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerModelManager loadGameData() throws IOException, ClassNotFoundException {
        logger.info("Loading data from " + Config.SAVE_FILE_NAME);
        FileInputStream fi = new FileInputStream(Config.SAVE_FILE_NAME);
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
        ServerModelManager serverModelManager = (ServerModelManager) oi.readObject();

        oi.close();
        fi.close();

        return serverModelManager;
    }
}
