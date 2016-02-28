package me.mprey.localization;

import me.mprey.Annihilation;
import me.mprey.util.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/12/16.
 */
public class LocalizationConfig extends YamlConfiguration {

    private static String[] availableLocales = {"en"};

    private LocalizationConfig fallback;

    public boolean isLocale(String locale) {
        for (String s : availableLocales) {
            if (s.equalsIgnoreCase(locale)) {
                return true;
            }
        }
        return false;
    }

    public void saveLocales(boolean overwrite) {
        try {
            for (String locale : availableLocales) {
                File file = getLocFile(locale);
                if (!file.exists() || overwrite) {
                    Annihilation.getInstance().saveResource("locale" + File.separator + locale + ".yml", overwrite);
                }
            }
        } catch (Exception e) {
            //TODO print out unable to load locale
            e.printStackTrace();
        }
    }

    public void loadLocale(String key, boolean fallback) {
        File locFile = new File(Annihilation.getInstance().getDataFolder().getPath() + File.separator + "locale" + File.separator + key + ".yml");
        if (!locFile.exists()) {
            locFile = getLocFile(key);
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(locFile), "UTF-8"));
            this.load(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!fallback) {
            this.fallback = new LocalizationConfig();
            InputStream stream = null;
            try {
                stream = Annihilation.getInstance().getResource("locale" + File.separator + key + ".yml");
                if (stream == null) {
                    this.fallback = null;
                    return;
                }
                reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object get(String path) {
        return this.getString(path);
    }

    public Object get(String path, Map<String, String> params) {
        return this.getFormatString(path, params);
    }

    public String getString(String path) {
        path = path.toLowerCase();
        if (super.get(path) == null) {
            if (this.fallback == null) {
                return "LOCALE_NOT_FOUND";
            }
            return this.fallback.getString(path);
        }
        return ChatColor.translateAlternateColorCodes('&', super.getString(path));
    }

    public List<String> getStringList(String path) {
        path = path.toLowerCase();
        if (super.getStringList(path) == null) {
            if (this.fallback == null) {
                return Arrays.asList("LOCALE_NOT_FOUND");
            }
            return this.fallback.getStringList(path);
        }
        List<String> translated = super.getStringList(path);
        for (int i = 0; i < translated.size(); i++) {
            String s = translated.remove(i);
            s = ChatColor.translateAlternateColorCodes('&', s);
            translated.add(i, s);
        }
        return translated;
    }

    public List<String> getStringList(String path, Map<String, String> params) {
        List<String> stringList = this.getStringList(path);
        for (int i = 0; i < stringList.size(); i++) {
            for (String key : params.keySet()) {
                stringList.add(i, stringList.remove(i).replace("$" + key.toLowerCase() + "$", params.get(key)));
            }
        }
        return stringList;
    }

    public String getFormatString(String path, Map<String, String> params) {
        String str = this.getString(path);
        for (String key : params.keySet()) {
            str = str.replace("$" + key.toLowerCase() + "$", params.get(key));
        }
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private File getLocFile(String key) {
        return new File(Annihilation.getInstance().getDataFolder() + File.separator + "locale" + File.separator + key + ".yml");
    }

}
