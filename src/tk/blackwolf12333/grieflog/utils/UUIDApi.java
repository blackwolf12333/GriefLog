package tk.blackwolf12333.grieflog.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
 
/**
 * @author KingFaris10
 * modified by blackwolf12333
 */
public class UUIDApi {
    private static final int DEFAULT_TIMEOUT = 1000;
 
    // private static final String NAME_LINK = "http://mineskin.ca/name/?uuid=%s";
    private static final String API_LINK = "http://mineskin.ca/uuid/?players=%s";
    private static final String UUID_FORMAT = "%s-%s-%s-%s-%s";
    private static final JSONParser jsonParser = new JSONParser();
 
    public static String getName(String playerUUID) {
        Validate.notNull(playerUUID);
        try {
            URL url = new URL("https://uuid.swordpvp.com/session/" + playerUUID);
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");
 
            Scanner scanner = new Scanner(uc.getInputStream(), "UTF-8");
            String json = scanner.useDelimiter("\\A").next();
            Object obj = jsonParser.parse(json);
            scanner.close();
            return (String) ((JSONObject) obj).get("name");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    public static String getName(UUID playerUUID) {
        Validate.notNull(playerUUID);
        return getName(playerUUID.toString()); // Coming soon to MineSkin's API.
    }
 
    public static UUID getUUID(String playerName) {
        Validate.notNull(playerName);
        return getUUID(playerName, false);
    }
 
    public static UUID getUUID(String playerName, boolean isSkin) {
        Validate.notNull(playerName);
        Map<String, UUID> uuidMap = getUUIDs(Arrays.asList(playerName), isSkin);
        return (uuidMap != null && uuidMap.containsKey(playerName)) ? uuidMap.get(playerName) : null;
    }
 
    public static UUID getUUID(String playerName, int timeout) {
        Validate.notNull(playerName);
        return getUUID(playerName, timeout, false);
    }
 
    public static UUID getUUID(String playerName, int timeout, boolean isSkin) {
        Validate.notNull(playerName);
        return getUUIDs(Arrays.asList(playerName), timeout, isSkin).get(playerName);
    }
 
    public static String getUUIDAsString(String playerName) {
        Validate.notNull(playerName);
        return getUUIDAsString(playerName, false);
    }
 
    public static String getUUIDAsString(String playerName, boolean isSkin) {
        Validate.notNull(playerName);
        return getUUIDsAsString(Arrays.asList(playerName), isSkin).get(playerName);
    }
 
    public static String getUUIDAsString(String playerName, int timeout) {
        Validate.notNull(playerName);
        return getUUIDAsString(playerName, timeout, false);
    }
 
    public static String getUUIDAsString(String playerName, int timeout, boolean isSkin) {
        Validate.notNull(playerName);
        return getUUIDsAsString(Arrays.asList(playerName), timeout, isSkin).get(playerName);
    }
 
    public static UUID getUUIDFromString(String playerUUID) {
        Validate.notNull(playerUUID);
        return UUID.fromString(playerUUID);
    }
 
    public static Map<String, UUID> getUUIDs(List<String> users) {
        Validate.notNull(users);
        Validate.noNullElements(users);
        return getUUIDs(users, false);
    }
 
    public static Map<String, UUID> getUUIDs(List<String> users, boolean isSkin) {
        Validate.notNull(users);
        Validate.noNullElements(users);
        return convertMapStringToUUID(getUUIDsAsString(users, isSkin));
    }
 
    public static Map<String, UUID> getUUIDs(List<String> users, int timeout) {
        Validate.notNull(users);
        Validate.noNullElements(users);
        return getUUIDs(users, timeout, false);
    }
 
    public static Map<String, UUID> getUUIDs(List<String> users, int timeout, boolean isSkin) {
        Validate.notNull(users);
        Validate.noNullElements(users);
        return convertMapStringToUUID(getUUIDsAsString(users, timeout, isSkin));
    }
 
    public static Map<String, String> getUUIDsAsString(String... users) {
        Validate.notNull(users);
        return getUUIDsAsString(false, users);
    }
 
    public static Map<String, String> getUUIDsAsString(boolean isSkin, String... users) {
        Validate.notNull(users);
        return getUUIDsAsString(Arrays.asList(users), DEFAULT_TIMEOUT, isSkin);
    }
 
    public static Map<String, String> getUUIDsAsString(List<String> users) {
        Validate.notNull(users);
        return getUUIDsAsString(users, false);
    }
 
    public static Map<String, String> getUUIDsAsString(List<String> users, boolean isSkin) {
        Validate.notNull(users);
        return getUUIDsAsString(users, DEFAULT_TIMEOUT, isSkin);
    }
 
    public static Map<String, String> getUUIDsAsString(List<String> users, int timeout, boolean isSkin) {
        Validate.notNull(users);
        if (timeout < 0) timeout = DEFAULT_TIMEOUT;
 
        Map<String, String> uuids = new HashMap<String, String>();
        int count = users.size();
 
        for (int i = 0; i < count; i += 100) {
            List<String> query = users.subList(i, Math.min(count, i + 100));
            String array = convertListToArray(query);
 
            try {
                URL url = new URL(String.format(API_LINK, array));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 
                connection.setDefaultUseCaches(false);
                connection.setConnectTimeout(timeout);
 
                JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                Iterator<?> iterator = jsonArray.iterator();
 
                while (iterator.hasNext()) {
                    JSONObject result = (JSONObject) iterator.next();
                    String uuid = (String) result.get("uuid");
 
                    if (uuid.length() == 32) {
                        String name = (String) result.get("name");
                        if (!isSkin)
                            uuid = String.format(UUID_FORMAT, uuid.substring(0, 8), uuid.substring(8, 12), uuid.substring(12, 16), uuid.substring(16, 20), uuid.substring(20, 32));
 
                        uuids.put(name, uuid);
                    }
                }
            } catch (Exception ex) {
                continue;
            }
        }
 
        return uuids;
    }
 
    private static Map<String, UUID> convertMapStringToUUID(Map<String, String> stringMap) {
        Map<String, UUID> uuids = new HashMap<String, UUID>();
        for (Map.Entry<String, String> uuidEntry : stringMap.entrySet()) {
            try {
                uuids.put(uuidEntry.getKey(), getUUIDFromString(uuidEntry.getValue()));
            } catch (Exception ex) {
                continue;
            }
        }
        return uuids;
    }
 
    private static String convertListToArray(List<String> list) {
        Validate.notNull(list);
        StringBuilder builder = new StringBuilder();
 
        builder.append("[");
 
        for (String string : list) {
            builder.append(",");
            builder.append('"');
            builder.append(string);
            builder.append('"');
        }
 
        builder.append("]");
 
        return builder.toString().replaceFirst(",", "");
    }
}