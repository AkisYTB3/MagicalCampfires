package org.notionsmp.magicalcampfireFabric;

import com.google.gson.JsonObject;

public class MagicalCampfireSettings {
    public final boolean enabled;
    public final int interval;
    public final double amount;
    public final int range;
    public final boolean actionbarEnabled;
    public final String actionbarMessage;

    public MagicalCampfireSettings(JsonObject json) {
        enabled = json.get("enabled").getAsBoolean();
        interval = json.get("interval").getAsInt();
        amount = json.get("amount").getAsDouble();
        range = json.get("range").getAsInt();
        JsonObject ab = json.getAsJsonObject("actionbar");
        actionbarEnabled = ab.get("enabled").getAsBoolean();
        actionbarMessage = ab.get("message").getAsString();
    }
}
