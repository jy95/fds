package io.github.jy95.fds;

import java.util.List;
import org.junit.jupiter.api.Test;

import io.github.jy95.fds.common.config.DefaultImplementations;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultImplementationsTest {
    
    @Test
    void fromExtensionsToStringEmptyList() {
        assertEquals("[]", DefaultImplementations.fromExtensionsToString(List.of()).join());
    }
}
