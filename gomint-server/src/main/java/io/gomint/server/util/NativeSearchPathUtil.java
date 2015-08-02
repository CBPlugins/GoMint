/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class NativeSearchPathUtil {

    /**
     * Adds the specified path to the list of search paths java will look in
     * for finding native libraries.
     *
     * @param path The path to be added to the search paths.
     * @throws Exception Thrown in case the operation fails.
     */
    public static void addLibraryPath( String path ) throws Exception {
        final Field usrPathsField = ClassLoader.class.getDeclaredField( "usr_paths" );
        usrPathsField.setAccessible( true );

        String absolutePath = Paths.get( path ).toAbsolutePath().toString();

        final String[] nativePaths = (String[]) usrPathsField.get( null );
        for ( String nativePath : nativePaths ) {
            if ( nativePath.equals( absolutePath ) ) {
                return;
            }
        }

        final String[] replacement = Arrays.copyOf( nativePaths, nativePaths.length + 1 );
        replacement[ nativePaths.length ] = absolutePath;
        usrPathsField.set( null, replacement );
    }

}
