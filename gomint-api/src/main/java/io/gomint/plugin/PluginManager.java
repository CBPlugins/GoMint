package io.gomint.plugin;

/**
 * @author Fabian
 * @version 1.0
 */
public interface PluginManager {
    /**
     * Disable the given plugin. This is only valid to be called from {@link Plugin#disable()}
     * @param plugin which should be disabled
     * @throws SecurityException when somebody else as the Main Class tries to disable a plugin
     */
    void disablePlugin( Plugin plugin );

    /**
     * Absolute path of the plugin Directory. This is used to determinate where the data folders of the Plugins
     * should reside
     *
     * @return absolute Path of the plugin folder
     */
    String getBaseDirectory();
}
