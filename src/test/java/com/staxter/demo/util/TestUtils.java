package com.staxter.demo.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class TestUtils {

    public static String loadResourceAsString(String resourcePath) throws IOException {
        URL resource = Resources.getResource(resourcePath);
        return Resources.toString(resource, Charsets.UTF_8);
    }
}
