package me.ninethousand.fate.api.config;

import com.google.gson.*;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.misc.EnumUtil;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void createDirectory() throws IOException {
        if (!Files.exists(Paths.get("etikahack/"))) {
            Files.createDirectories(Paths.get("etikahack/"));
        }

        if (!Files.exists(Paths.get("etikahack/modules"))) {
            Files.createDirectories(Paths.get("etikahack/modules"));
        }

        if (!Files.exists(Paths.get("etikahack/social"))) {
            Files.createDirectories(Paths.get("etikahack/social"));
        }
    }

    public static void registerFiles(String name, String path) throws IOException {
        if (Files.exists(Paths.get("etikahack/" + path + "/" + name + ".json"))) {
            File file = new File("etikahack/" + path + "/" + name + ".json");
            file.delete();
        }

        Files.createFile(Paths.get("etikahack/" + path + "/" + name + ".json"));
    }

    public static void saveConfig() {
        try {
            saveModules();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void loadConfig() {
        try {
            createDirectory();
            loadModules();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void saveModules() throws IOException {
        for (Module module : ModuleManager.getModules()) {
            registerFiles(module.getName(), "modules");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("etikahack/modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);

            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            JsonObject subSettingObject = new JsonObject();

            moduleObject.add("Name", new JsonPrimitive(module.getName()));
            moduleObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
            moduleObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
            moduleObject.add("Bind", new JsonPrimitive(module.getKey()));

            for (Setting<?> setting : module.getSettings()) {
                settingObject = saveSetting(setting, settingObject);
            }

            moduleObject.add("Settings", settingObject);

            String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));

            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    private static JsonObject saveSetting(Setting<?> setting, JsonObject json) {
        if (setting.getValue() instanceof Boolean) {
            json.add(setting.getName(), new JsonPrimitive((Boolean) setting.getValue()));
        }

        if (setting.getValue() instanceof Enum) {
            json.add(setting.getName(), new JsonPrimitive(String.valueOf(setting.getValue())));
        }

        if (setting.getValue() instanceof String) {
            json.add(setting.getName(), new JsonPrimitive((String) setting.getValue()));
        }

        if (setting.getValue() instanceof Integer) {
            json.add(setting.getName(), new JsonPrimitive((Integer) setting.getValue()));
        }

        if (setting.getValue() instanceof Float) {
            json.add(setting.getName(), new JsonPrimitive((Float) setting.getValue()));
        }

        if (setting.getValue() instanceof Double) {
            json.add(setting.getName(), new JsonPrimitive((Double) setting.getValue()));
        }

        if (setting.getValue() instanceof Color) {
            ArrayList<Integer> rgbaArr = new ArrayList<>();
            rgbaArr.addAll(Arrays.asList(
                    ((Setting<Color>) setting).getValue().getRed(),
                    ((Setting<Color>) setting).getValue().getGreen(),
                    ((Setting<Color>) setting).getValue().getBlue(),
                    ((Setting<Color>) setting).getValue().getAlpha()
            ));

            json.add(setting.getName(), new JsonPrimitive(String.valueOf(rgbaArr)));
        }

        if (setting.hasSubSettings()) {
            JsonObject subJson = new JsonObject();

            for (Setting<?> subSetting : setting.getSubSettings()) {
                subJson = saveSetting(subSetting, subJson);
            }

            json.add(setting.getName() + "SubSettings", subJson);
        }

        return json;
    }

    public static void loadModules() throws IOException {
        for (Module module : ModuleManager.getModules()) {
            if (!Files.exists(Paths.get("etikahack/modules/" + module.getName() + ".json"))) continue;

            InputStream inputStream = Files.newInputStream(Paths.get("etikahack/modules/" + module.getName() + ".json"));
            JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

            if (moduleObject.get("Name") == null || moduleObject.get("Enabled") == null || moduleObject.get("Drawn") == null || moduleObject.get("Bind") == null) continue;

            JsonObject settings = moduleObject.get("Settings").getAsJsonObject();

            for (Setting<?> setting : module.getSettings()) {
                loadSetting(setting, settings);
            }

            module.setEnabled(moduleObject.get("Enabled").getAsBoolean());
            module.setDrawn(moduleObject.get("Drawn").getAsBoolean());
            module.setKey((moduleObject.get("Bind").getAsInt()));
        }
    }

    private static void loadSetting(Setting<?> setting, JsonObject json) {
        JsonElement jsonValue = json.get(setting.getName());

        if (setting.getValue() instanceof Boolean) {
            ((Setting<Boolean>) setting).setValue(jsonValue.getAsBoolean());
        }

        if (setting.getValue() instanceof Enum) {
            EnumUtil.setEnumValue(((Setting<Enum<?>>) setting), jsonValue.getAsString());
        }

        if (setting.getValue() instanceof String) {
            ((Setting<String>) setting).setValue(jsonValue.getAsString());
        }

        if (setting.getValue() instanceof Integer) {
            ((Setting<Integer>) setting).setValue(jsonValue.getAsInt());
        }

        if (setting.getValue() instanceof Float) {
            ((Setting<Float>) setting).setValue(jsonValue.getAsFloat());
        }

        if (setting.getValue() instanceof Double) {
            ((Setting<Double>) setting).setValue(jsonValue.getAsDouble());
        }

        if (setting.getValue() instanceof Color) {
            Setting<Color> colorSetting = (Setting<Color>) setting;
            String value = jsonValue.getAsString().replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ", "");
            String[] values = value.split(",");
            colorSetting.setValue(new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3])));
        }

        if (setting.hasSubSettings()) {
            for (Setting<?> subSetting : setting.getSubSettings()) {
                loadSetting(subSetting, json.get(setting.getName() + "SubSettings").getAsJsonObject());
            }
        }
    }
}
