/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Tim
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class Location {

    /**
     * The block x coordinate.
     */
    private int blockX;

    /**
     * The block y coordinate.
     */
    private int blockY;

    /**
     * The block z coordinate.
     */
    private int blockZ;

    /**
     * The exact x coordinate of this location.
     */
    private double x;

    /**
     * The exact y coordinate of this location.
     */
    private double y;

    /**
     * The exact z coordinate of this location.
     */
    private double z;

    /**
     * The rotation in degrees.
     */
    private Rotation rotation;
    /**
     * A block coordinates accepting constructor.
     * May be used to save block locations.
     *
     * @param blockX The x coordinate.
     * @param blockY The y coordinate.
     * @param blockZ The z coordinate.
     */
    public Location( int blockX, int blockY, int blockZ ) {
        // Setting block locations
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;

        // Setting exact locations to prevent errors
        this.x = blockX;
        this.y = blockY;
        this.z = blockZ;

        // Also setting rotation degrees
        this.rotation = new Rotation( 0F, 0F );
    }

    /**
     * An exact coordinates and rotation accepting constructor.
     * May be used to save entity locations.
     *
     * @param x The exact x coordinate.
     * @param y The exact y coordinate.
     * @param z The exact z coordinate.
     * @param yaw The east - west rotation in degrees.
     * @param pitch The north - south rotation in degrees.
     */
    public Location( double x, double y, double z, float yaw, float pitch ) {
        // Setting exact coordinates
        this.x = x;
        this.y = y;
        this.z = z;

        // Setting block coordinates to prevent errors
        this.blockX = (int) Math.floor( x ); // round off
        this.blockY = (int) Math.round( y ); // round off
        this.blockZ = (int) Math.round( z ); // round off

        // Setting rotation degrees
        this.rotation = new Rotation( yaw, pitch );
    }

    /**
     * Returns the west - east rotation in degrees.
     */
    public float getYaw() {
        return rotation != null ? rotation.getYaw() : 0;
    }

    /**
     * Returns the north - south rotation in degrees.
     */
    public float getPitch() {
        return rotation != null ? rotation.getPitch() : 0;
    }

    /**
     * Adds the given coordinates to the cloned location object
     * of this instance.
     *
     * @param x The amount of blocks to add to the x coordinate.
     * @param y The amount of blocks to add to the y coordinate.
     * @param z The amount of blocks to add to the z coordinate.
     * @return The cloned and modified location object.
     */
    public Location add( double x, double y, double z ) {
        return new Location( this.x + x, this.y + y, this.z + z, getYaw(), getPitch() );
    }

    @Data
    @AllArgsConstructor
    public class Rotation {
        /**
         * The west - east rotation in degrees.
         */
        private float yaw;

        /**
         * The north - south rotation in degrees.
         */
        private float pitch;
    }

}
