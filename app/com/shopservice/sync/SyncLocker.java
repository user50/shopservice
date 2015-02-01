package com.shopservice.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Yevhen on 1/10/15.
 */
public class SyncLocker implements SyncProduct {

    private boolean runningFlag;
    private SyncProduct syncProduct;
    private String clientId;

    public SyncLocker(SyncProduct syncProduct, String clientId) {
        this.syncProduct = syncProduct;
        this.clientId = clientId;
    }

    public void started(){
        runningFlag = true;
        Logger.getGlobal().info("Sync for " + clientId + " is started!");
    }

    public void stopped(){
        runningFlag = false;
        Logger.getGlobal().info("Sync for " + clientId + " is stopped!");
    }

    public boolean isStarted(){
        return runningFlag;
    }

    @Override
    public void execute() {
        if (isStarted()){
            Logger.getGlobal().info("Sync for " + clientId + " is locked!");
            return;
        }

        started();
        syncProduct.execute();
        stopped();

    }
}
