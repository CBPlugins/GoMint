/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.server.math.Vector;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class Entity {
    private double x;
    private double y;
    private double z;

    private Vector vector;
}
