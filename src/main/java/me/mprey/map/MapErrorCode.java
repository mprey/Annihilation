package me.mprey.map;

/**
 * Created by Mason Prey on 2/13/16.
 */
public enum MapErrorCode {

    OK(200),
    MAP_NAME_NULL(400),
    MAP_REGION_NULL(401),
    MAP_LOBBY_NULL(402),
    RED_LOCATION_ERROR(403),
    BLUE_LOCATION_ERROR(404),
    GREEN_LOCATION_ERROR(405),
    YELLOW_LOCATION_ERROR(406);

    private int code;

    MapErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MapErrorCode valueOf(int code) {
        for (MapErrorCode object : MapErrorCode.values()) {
            if (object.getCode() == code) {
                return object;
            }
        }
        return null;
    }

}
