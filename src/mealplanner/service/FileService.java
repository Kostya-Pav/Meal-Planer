package mealplanner.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileService {

    public void saveToFile(Map<String, Integer> map, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() > 1) {
                    writer.write(entry.getKey() + " x" + entry.getValue());
                } else {
                    writer.write(entry.getKey());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}