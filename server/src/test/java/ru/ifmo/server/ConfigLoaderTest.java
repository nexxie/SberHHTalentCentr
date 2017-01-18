package ru.ifmo.server;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** Tests for ConfigLoader */

public class ConfigLoaderTest {

    private static String PATH = "/success";

    @Test
    public void testProperties() throws IOException {

        URL resourse = getClass().getClassLoader().getResource("web-server.properties");

        assertNotNull(resourse);

        File prop = new File(resourse.getFile());

        ServerConfig config = new ConfigLoader().load(prop);

        checkConfig(config);
    }

    @Test
    public void testXml() throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("web-server.xml");

        assertNotNull(in);

        File tmpFile = File.createTempFile("ifmo", ".xml");

        tmpFile.deleteOnExit();

        Files.copy(in, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        ServerConfig config = new ConfigLoader().load(tmpFile);

        checkConfig(config);
    }

    @Test
    public void testClasspath() throws Exception {
        ServerConfig config = new ConfigLoader().load();

        checkConfig(config);
    }

    private void checkConfig(ServerConfig config) {

        assertEquals(8081, config.getPort());
        assertEquals(5000, config.getSocketTimeout());

        Set<String> paths = new HashSet<>(Collections.singleton(PATH));

        assertEquals(paths, config.getHandlers().keySet());
        assertNotNull(config.getHandlers().get(PATH));
        assertEquals(SuccessHandler.class, config.getHandlers().get(PATH).getClass());
    }
}