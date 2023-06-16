package net.kankantari.saeb;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    private static final String CONFIG_DIRECTORY_PATH = "./saeb";
    private static final String CONFIG_FILE_PATH = CONFIG_DIRECTORY_PATH + "/config.json";

    private static Config config;

    @SerializedName("ae3Url")
    @Expose
    private String ae3Url;
    @SerializedName("autoLogin")
    @Expose
    private boolean autoLogin;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;

    public String getAe3Url() {
        return ae3Url;
    }

    public void setAe3Url(String ae3Url) {
        this.ae3Url = ae3Url;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveConfig() throws IOException {
        var gson = new Gson();
        var jsonStr = gson.toJson(this);

        var dir = new File(CONFIG_DIRECTORY_PATH);
        var file = new File(CONFIG_FILE_PATH);

        if (!dir.exists()) {
            dir.mkdir();
        }

        if (file.exists()) {
            file.delete();
        }

        Files.createFile(file.toPath());
        Files.writeString(file.toPath(), jsonStr);
    }

    public static boolean isConfigExists() {
        var file = new File(CONFIG_FILE_PATH);
        return file.exists();
    }

    public static Config loadConfigFile() throws IOException {
        var file = new File(CONFIG_FILE_PATH);
        var content = Files.readString(file.toPath());
        var gson = new Gson();
        var conf = gson.fromJson(content, Config.class);

        return conf;
    }

    public static boolean isConfigSet() {
        return false; // TODO:
    }


    public static Config getDefaultConfig() {
        var conf = new Config();
        conf.setAe3Url("https://ae3.example.com");
        conf.setId("your_id");
        conf.setPassword("your_password");
        conf.setAutoLogin(false);

        return conf;
    }


    public static void setConfig(Config config) {
        Config.config = config;
    }

    public static Config getConfig() {
        return config;
    }
}
