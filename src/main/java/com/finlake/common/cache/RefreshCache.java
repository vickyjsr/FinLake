package com.finlake.common.cache;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RefreshCache implements Runnable {

    protected boolean cachingEnabled = true;

    public abstract void refreshCache();

    @Override
    public void run() {
        if (cachingEnabled) {
            log.info("Cache refresh start");
            try {
                refreshCache();
                log.info("Cache refresh completed");
            } catch (Exception e) {
                log.error("Exception while refreshing cache : {}", e);
            }
        }
    }
}
