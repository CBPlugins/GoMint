/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.config;

import com.blackypaw.simpleconfig.SimpleConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
@Getter
public class ServerConfig extends SimpleConfig {
    private List<ListenerConfig> listener = new ArrayList<>();
}
