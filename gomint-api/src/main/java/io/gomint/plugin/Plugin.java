/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.plugin;

import io.gomint.scheduler.Scheduler;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;

/**
 * <p>
 *     Base class for any plugin to be created for use with the GoMint system. Below you will find an in-depth
 *     explanation of the GoMint plugin system.
 * </p>
 *
 * <p>
 *     When creating a plugin you should take care that the plugin implementation class directly inherits from
 *     the base Plugin class. It is important that the implementation will not be considered a valid plugin
 *     by GoNets plugin loader in case it only indirectly inherits from plugin. There are also several
 *     annotation which are required on any plugin and which you need to specify under all circumstances so that
 *     your plugin will actually be loaded. Namely, these are:
 *     <ul>
 *         <li>@Name( "NameOfPlugin" ) - The name of your plugin; for internal use only.</li>
 *         <li>@Version( major = 1, minor = 0 ) - The current version of your plugin; for incremental updates.</li>
 *     </ul>
 *     Furthermore you may specify additional annotation as needed, for example a @Startup annotation to specify
 *     upmost priority in loading the specified plugin.
 * </p>
 *
 * <p>
 *     Any plugin will live through up to 4 different stages which will be explained in detail below. Please note
 *     that only the very first stage is obligatory (assumed you have been able to put your - correct - implementation,
 *     as shown above, into the right folder of the application), that is the DETECTION stage.
 *     <ol>
 *         <li>DETECTION - In this stage only the bare meta-information provided by annotations is actually loaded.</li>
 *         <li>INSTALLATION - In this stage the classes of the plugin are loaded and the startup hook will be invoked.</li>
 *         <li>RUNTIME - The main stage your plugin will (probaby) be in most of the time (expect your plugin is full of shit nobody wants to install).</li>
 *         <li>UNINSTALLATION - In this stage your plugin will be removed and - as the name obviously indicates - uninstalled.</li>
 *     </ol>
 * </p>
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class Plugin {
    /**
     * The plugin manager which controls the plugin's lifecycle. This is used to allow
     * plugins to determine their end on their own by disabling them. As this operation
     * needs to be done by the plugin manager each plugin gets a reference to it.
     */
    @Getter @Setter PluginManager pluginManager;

    /**
     * The name of the plugin as provided by annotation data.
     */
    @Getter String name;

    /**
     * The version of the plugin as provided by annotation data.
     */
    @Getter PluginVersion version;

    /**
     * Logger for this Plugin
     */
    @Getter Logger logger;

    /**
     * Scheduler for this Plugin
     */
    @Getter Scheduler scheduler;

    /**
     * Implementation hook. This hook is invoked once the plugin is being installed.
     */
    public void onStartup() {}

    /**
     * Implementation hook. This hook is invoked once the plugin enters the runtime stage.
     */
    public void onInstall() {}

    /**
     * Implementation hook. This hook is invoked once the plugin is being uninstalled.
     */
    public void onUninstall() {}

    /**
     * Disables the plugin if - and only if - the plugin is currently in the runtime stage.
     * Under all other circumstances invocation of this method will show no effect.
     */
    protected void disable() {
        this.pluginManager.disablePlugin( this );
    }

    /**
     * Get a resource from within this plugins jar or container. Care must be
     * taken to close the returned stream.
     *
     * @param name the full path name of this resource
     * @return the stream for getting this resource, or null if it does not
     * exist
     */
    public final InputStream getResourceAsStream( String name ) {
        return getClass().getClassLoader().getResourceAsStream( name );
    }

    /**
     * Gets the data folder where this plugin may store arbitrary data.
     *
     * @return the data folder of this plugin
     */
    public final File getDataFolder() {
        return new File( getPluginManager().getBaseDirectory(), getName() );
    }
}
