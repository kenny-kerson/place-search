package com.namkyujin.search.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestResourceReader {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T read(String resourcePath, Class<T> expectedType) {
        try {
            return objectMapper.readValue(read(resourcePath), expectedType);
        } catch (Exception e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }
    }

    public static String read(String path) {
        try {
            return new String(Files.readAllBytes(getPath(path)));
        } catch (Exception e) {
            throw new RuntimeException("파일 읽기 실패", e);
        }
    }

    private static Path getPath(String path) throws FileNotFoundException {
        return Paths.get(ResourceUtils.getFile("classpath:" + path).getPath());
    }
}
