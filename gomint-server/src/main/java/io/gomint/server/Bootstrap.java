/*
 *  Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Fabian
 * @version 1.0
 */
public class Bootstrap {
    /**
     * Main entry point. May be used for custom dependency injection, dynamic
     * library class loaders and other experiments which need to be done before
     * the actual main entry point (inside {@link io.gomint.server.GoMintServer} is executed.
     *
     * @param args The command-line arguments to be passed to the GoMintServer.
     */
    public static void main( String[] args ) {
        // Check if classloader has been changed (it should be a URLClassLoader)
        if ( !( ClassLoader.getSystemClassLoader() instanceof URLClassLoader ) ) {
            System.out.println( "System Classloader is no URLClassloader" );
            System.exit( -1 );
        }

        // Scan the libs/ Directory for .jar Files
        File libsFolder = new File( "libs/" );

        // Checking if the libs folder exists or is a directory
        if ( !libsFolder.exists() || !libsFolder.isDirectory() ) {
            if ( !libsFolder.mkdir() ) {
                System.out.println( "Could not create libs folder. Please double check you file system permissions." );
            }
        }

        try {
            for ( File file : libsFolder.listFiles() ) {
                if ( file.getAbsolutePath().endsWith( ".jar" ) ) {
                    try {
                        System.out.println( "Loading lib: " + file.getAbsolutePath() );
                        addJARToClasspath( file );
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                }
            }
        } catch ( NullPointerException exception ) {
            // Ignore
        }

        // Load the GoMint
        try {
            Class<?> coreClass = ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.GoMintServer" );
            Constructor constructor = coreClass.getDeclaredConstructor();
            constructor.newInstance();
        } catch ( ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a JAR into the System Classloader
     *
     * @param moduleFile which should be added to the classpath
     * @throws IOException
     */
    private static void addJARToClasspath( File moduleFile ) throws IOException {
        URL moduleURL = moduleFile.toURI().toURL();
        Class[] parameters = new Class[]{ URL.class };

        ClassLoader sysloader = ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod( "addURL", parameters );
            method.setAccessible( true );
            method.invoke( sysloader, new Object[]{ moduleURL } );
        } catch ( NoSuchMethodException | InvocationTargetException | IllegalAccessException e ) {
            e.printStackTrace();
            throw new IOException( "Error, could not add URL to system classloader" );
        }
    }
}
