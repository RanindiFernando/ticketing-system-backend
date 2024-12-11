package Ticketing.System.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONFileWriter {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Writes a list of JsonObject logs to a JSON file.
     *
     * @param logs     The list of JsonObject logs to write.
     * @param filename The filename where the logs will be saved.
     */
    public static void writeLogsToJSON(List<JsonObject> logs, String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) { // Overwrite the file
            gson.toJson(logs, writer);
            System.out.println("Logs successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write logs to JSON file: " + e.getMessage());
        }
    }

    /**
     * Reads a list of JsonObject logs from a JSON file.
     *
     * @param filename The filename to read from.
     * @return The list of JsonObject logs, or null if there was an error.
     */
    public static List<JsonObject> readLogsFromJSON(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, List.class); // Parse the JSON array into a list of JsonObjects
        } catch (IOException e) {
            System.err.println("Failed to read logs from JSON file: " + e.getMessage());
            return null;
        }
    }
}
