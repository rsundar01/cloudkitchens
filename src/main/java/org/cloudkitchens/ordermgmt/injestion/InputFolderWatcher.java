package org.cloudkitchens.ordermgmt.injestion;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import org.cloudkitchens.ordermgmt.configuration.CloudKitchensConfig;
import org.cloudkitchens.ordermgmt.data.InputFile;
import org.cloudkitchens.ordermgmt.data.InputFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputFolderWatcher implements SourceWatcherAdapter, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputFolderWatcher.class);

    private final int THREAD_SLEEP = 1000; // 1 Second

    private boolean isInitialized = false;

    private WatcherCallback ingestCallback;

    private WatchService watchService;

    private List<String> inputFolders;

    @Autowired
    private InputFileService inputFileService;

    @Autowired
    CloudKitchensConfig config;

    @Override
    public void registerCallback(WatcherCallback ingestCallback) {

        this.ingestCallback = ingestCallback;

    }

    public void initialize() {

        try {

            watchService =  FileSystems.getDefault().newWatchService();

            inputFolders = config.getInputfolders();

            for(int i = 0; i < inputFolders.size(); i++) {

                Path inputFolderPath = Paths.get(inputFolders.get(i));

                Files.list(inputFolderPath)
                        .filter(Files::isRegularFile)
                        .forEach(inputfile -> ingestCallback.process(inputfile));

                inputFolderPath.register(watchService, ENTRY_CREATE);

            }

            isInitialized = true;


        } catch(IOException ioe) {

            LOGGER.error("Object can't be initialized");
        }
    }


    @Override
    public void run() {

        if(!isInitialized) {

            LOGGER.error("Object not initialized. Exiting.");
        }

        for(;;) {

            try {

                if(Thread.interrupted()) throw new InterruptedException();

                WatchKey watchKey = watchService.take();

                for(WatchEvent<?> event : watchKey.pollEvents()) {

                    WatchEvent<Path> e = (WatchEvent<Path>) event;

                    Path file = e.context().toAbsolutePath();

                    if(!Files.exists(file) || !Files.isRegularFile(file)) continue;

                    processData(file);
                }

                boolean valid = watchKey.reset();

                if(!valid) break;

                Thread.sleep(THREAD_SLEEP);


            } catch (InterruptedException ie) {

                try { watchService.close(); } catch(Exception e) {}

                LOGGER.info("Thread Interrupted");

                break;
            }

        }
    }


    private void processData(Path file) {

        String sFile = file.toAbsolutePath().toString();

        if(inputFileService.containsFile(sFile)) return;

        try {

            FileInputStream fileInputStream = new FileInputStream(sFile);

            InputFile inputFile = new InputFile(sFile);

        } catch (FileNotFoundException fnf) {


        }
    }


}
