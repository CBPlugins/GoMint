package io.gomint.server;

import io.gomint.plugin.Plugin;

/**
 * @author Fabian
 * @version 1.0
 */
public class TestPlugin extends Plugin {
    @Override
    public void onInstall() {
        disable();
    }
}
