package agh.iet.devs.utils;

import agh.iet.devs.data.Epoch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class StatisticsSaver {

    private final static String directory = GeneralUtils.resources + "statistics" + File.separator;
    private static final Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    public static void saveAll(List<Epoch> history) {
        final var content = gson.toJson(history);
        final var name = "ALL";

        writeJson(name, content);
    }

    public static void save(Epoch epoch) {
        final var content = gson.toJson(epoch);
        final var name = "EPOCH-" + epoch.epoch;

        writeJson(name, content);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeJson(String name, String content) {
        var file = new File(directory + name + ".json");

        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists())
                file.createNewFile();
            var bytes = content.getBytes();

            fop.write(bytes);
            fop.flush();

            String title = "Congratulations sir";
            String message = "You've successfully saved your statistics";

            showNotification(title, message);
        } catch (IOException e) {
            String title = "Sorry sir";
            String message = "Something went wrong during saving";

            showNotification(title, message);
        }
    }

    private static void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

}
