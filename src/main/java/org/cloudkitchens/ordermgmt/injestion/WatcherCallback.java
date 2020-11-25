package org.cloudkitchens.ordermgmt.injestion;

import java.nio.file.Path;

public interface WatcherCallback {

    public void process(Path inputPath);

}
