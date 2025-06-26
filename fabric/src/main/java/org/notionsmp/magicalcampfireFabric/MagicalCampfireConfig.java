package org.notionsmp.magicalcampfireFabric;

import com.google.gson.*;
import java.io.*;

public class MagicalCampfireConfig {
    public boolean enabled;
    public MagicalCampfireSettings campfire;
    public MagicalCampfireSettings soulCampfire;

    public static MagicalCampfireConfig load(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!file.exists()) {
            JsonObject base = new JsonObject();
            base.addProperty("enabled", true);

            base.add("campfire", defaultSection());
            base.add("soul_campfire", defaultSection());

            try {
                file.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(base, writer);
                }
            } catch (IOException ignored) {}

            MagicalCampfireConfig config = new MagicalCampfireConfig();
            config.enabled = true;
            config.campfire = new MagicalCampfireSettings(defaultSection());
            config.soulCampfire = new MagicalCampfireSettings(defaultSection());
            return config;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            MagicalCampfireConfig config = new MagicalCampfireConfig();
            config.enabled = json.get("enabled").getAsBoolean();
            config.campfire = new MagicalCampfireSettings(json.getAsJsonObject("campfire"));
            config.soulCampfire = new MagicalCampfireSettings(json.getAsJsonObject("soul_campfire"));
            return config;
        } catch (IOException e) {
            e.printStackTrace();
            return new MagicalCampfireConfig();
        }
    }

    private static JsonObject defaultSection() {
        JsonObject obj = new JsonObject();
        obj.addProperty("enabled", true);
        obj.addProperty("interval", 2000);
        obj.addProperty("amount", 1.0);
        obj.addProperty("range", 3);

        JsonObject ab = new JsonObject();
        ab.addProperty("enabled", true);
        ab.addProperty("message", "§2You're getting healed.");
        obj.add("actionbar", ab);

        return obj;
    }
}
