package tk.roccodev.beezig.forge.pointstag;

import java.util.HashMap;
import java.util.UUID;

public class PointsTagCache {

    private static HashMap<UUID, PointsTag> cache = new HashMap<>();

    public static void putIfAbsent(UUID uuid, PointsTag cached) {
        cache.putIfAbsent(uuid, cached);
    }

    public static PointsTag get(UUID uuid) {
        return cache.get(uuid);
    }

    public static void clear() {
        cache.clear();
    }

}