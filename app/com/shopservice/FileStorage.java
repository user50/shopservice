package com.shopservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.shopservice.domain.Product;
import play.libs.Json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class FileStorage<T> {

    private String fileName;

    protected FileStorage(String fileName) {
        this.fileName = fileName;
    }

    public boolean isExist()
    {
        return new File(fileName).exists();
    }

    public T get() throws IOException {
        String persistedText = new String(Files.readAllBytes(Paths.get(fileName)), "utf8");
        return construct(Json.parse(persistedText));
    }


    public void save(T t) throws IOException {
        createDirectory();

        String textToPersist = Json.toJson(t).toString();
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(textToPersist);
        fileWriter.close();
    }

    private void createDirectory() throws IOException {
        File file = new File(fileName);

        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
    }

    protected abstract T construct(JsonNode node);
}
