package com.example.carefully.global.utils;

import com.example.carefully.domain.post.exception.FileWrongExtensionException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class FileUtils {

    private static final String TIME_SEPARATOR = "_";
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    public static String createFileName(String originalFileName) {
        Objects.requireNonNull(originalFileName);

        int idx = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = getFileExtension(originalFileName, idx);
        String fileName = originalFileName.substring(0, idx);
        String current = String.valueOf(System.currentTimeMillis());
        return fileName + TIME_SEPARATOR + current + fileExtension;
    }

    private static String getFileExtension(String file, int beginIndex) {
        try {
            return file.substring(beginIndex);
        } catch (StringIndexOutOfBoundsException e) {
            throw new FileWrongExtensionException();
        }
    }
}
