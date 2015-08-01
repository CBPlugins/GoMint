package io.gomint.plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public class PluginMeta {
    private final String name;
    private final PluginVersion version;
    private final StartupPriority priority;
    private final String mainClass;
    private final File pluginFile;
}
