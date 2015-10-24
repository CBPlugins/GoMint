/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.math;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Tim on 24.10.2015
 */
@Data
@AllArgsConstructor
public class Vector {
    /**
     * The x-Value of the Vector
     */
    private double x;

    /**
     * The y-Value of the Vector
     */
    private double y;

    /**
     * The z-Value of the Vector
     */
    private double z;

    /**
     * Adds the specified values to the Vector and returns it.
     *
     * @param x The x-Value to add to the Vector.
     * @param y The y-Value to add to the Vector.
     * @param z The z-Value to add to the Vector.
     * @return An instance of this Vector with the added values.
     */
    public Vector add( double x, double y, double z ) {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    /**
     * Adds the specified Vector to this Vector and returns it.
     *
     * @param vector The Vector to add to this instance.
     * @return An instance of this Vector with the added values.
     */
    public Vector add( Vector vector ) {
        return add( vector.getX(), vector.getY(), vector.getZ() );
    }

    /**
     * Subtracts the specified values from this Vector and returns it.
     *
     * @param x The x-Value to subtract.
     * @param y The y-Value to subtract.
     * @param z The z-Value to subtract.
     * @return An instance of this Vector with the subtracted values.
     */
    public Vector subtract( double x, double y, double z ) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    /**
     * Subtracts the specified Vector from this Vector and returns it.
     *
     * @param vector The Vector to subtract from this instance.
     * @return An instance of this Vector with the subtracted values.
     */
    public Vector subtract( Vector vector ) {
        return subtract( vector.getX(), vector.getY(), vector.getZ() );
    }

    /**
     * Multiplies the specified values with this Vector and returns it.
     *
     * @param x The x-Value to multiply with.
     * @param y The y-Value to multiply with.
     * @param z The z-Value to multiply with.
     * @return An instance of the product of this multiplication.
     */
    public Vector multiply( double x, double y, double z ) {
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return this;
    }

    /**
     * Multiplies the specified Vector with this instance and returns it.
     *
     * @param vector The Vector to multiply with.
     * @return An instance of the product of this multiplication.
     */
    public Vector multiply( Vector vector ) {
        return multiply( vector.getX(), vector.getY(), vector.getZ() );
    }

    /**
     * Calculates the squared length of this Vector and returns it.
     *
     * @return The squared length of this Vector.
     */
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /**
     * Calculates the length of this Vector and returns it.
     *
     * @return The length of this Vector.
     */
    public double length() {
        return Math.sqrt( this.lengthSquared() );
    }

    /**
     * Normalizes this Vector by dividing through the length of it.
     *
     * @return The normalized Vector instance.
     */
    public Vector normalize() {
        double length = length();

        this.x /= length;
        this.y /= length;
        this.z /= length;

        return this;
    }

    /**
     * Clones this instance to another Vector instance.
     */
    public Vector clone() {
        return new Vector( this.x, this.y, this.z );
    }
}
