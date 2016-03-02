package me.mprey;

import com.google.common.collect.ImmutableMap;
import me.mprey.commands.GUICommand;
import me.mprey.commands.MapCommand;
import me.mprey.commands.CommandManager;
import me.mprey.commands.map.MapDeleteCommand;
import me.mprey.commands.map.MapSaveCommand;
import me.mprey.database.DatabaseManager;
import me.mprey.game.Ender;
import me.mprey.listener.MapCreatorListener;
import me.mprey.localization.LocalizationConfig;
import me.mprey.map.MapManager;
import me.mprey.regen.RegeneratingBlockManager;
import me.mprey.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/6/16.
 */
public class Annihilation extends JavaPlugin {

    private static Annihilation instance;

    private static String FALLBACK_LOCALE = "en";

    private RegeneratingBlockManager regeneratingBlockManager;
    private MapManager mapManager;
    private DatabaseManager databaseManager;
    private LocalizationConfig localization;

    public void onEnable() {
        long startTime = System.currentTimeMillis();

        instance = this;

        this.saveDefaultConfig();

        this.initLocalization();
        this.initRegeneratingBlocksManager();
        this.initDatabaseManager();
        this.initListeners();

        this.mapManager = new MapManager();
        this.mapManager.loadMaps();

        new Ender(this);

        this.getCommand("annihilation").setExecutor(new CommandManager(new GUICommand(this), new MapCommand(this, new MapDeleteCommand(this), new MapSaveCommand(this))));

        long endTime = System.currentTimeMillis();
        getLogger().info(_l("extra.enable_time", ImmutableMap.of("time", "" + (endTime - startTime))));
    }

    public void onDisable() {
        regeneratingBlockManager.onDisable();
        databaseManager.onDisable();
    }

    public MapManager getMapManager() {
        return this.mapManager;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public LocalizationConfig getLocalization() {
        return this.localization;
    }

    private void initRegeneratingBlocksManager() {
        long interval = this.getConfig().getLong("regen_blocks.interval");
        this.regeneratingBlockManager = new RegeneratingBlockManager(ConfigUtil.getRegeneratingBlockStructures(this.getConfig(), "regen_blocks.blocks"), interval);
    }

    private void initDatabaseManager() {
        if (this.getConfig().getBoolean("database.use_mysql")) {
            this.databaseManager = new DatabaseManager(ConfigUtil.loadMySQL());
        } else {
            this.databaseManager = new DatabaseManager();
        }
    }

    private void initLocalization() {
        this.localization = new LocalizationConfig();
        this.localization.saveLocales(false);
        String locale = this.getConfig().getString("locale");
        if (this.localization.isLocale(locale)) {
            this.localization.loadLocale(locale, false);
            this.getLogger().info(Annihilation._l("success.locale.loaded", ImmutableMap.of("locale", locale)));
        } else {
            this.localization.loadLocale(FALLBACK_LOCALE, false);
            this.getLogger().info(Annihilation._l("errors.locale.not_found", ImmutableMap.of("locale", locale)));
        }
    }

    private void initListeners() {
        new MapCreatorListener();
    }

    public void reloadLocalization() {
        this.localization.saveLocales(false);
        this.localization.loadLocale(this.getConfig().getString("locale"), false);
    }

    public static String _l(String localeKey, Map<String, String> params) {
        return (String) Annihilation.getInstance().getLocalization().get(localeKey, params);
    }

    public static String _l(String localeKey) {
        return (String) Annihilation.getInstance().getLocalization().get(localeKey);
    }

    public static List<String> _ls(String localeKey) {
        return Annihilation.getInstance().getLocalization().getStringList(localeKey);
    }

    public static List<String> _ls(String localeKey, Map<String, String> params) {
        return Annihilation.getInstance().getLocalization().getStringList(localeKey, params);
    }

    public static Annihilation getInstance() {
        return instance;
    }

}
