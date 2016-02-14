package me.mprey.bosses;

/**
 * Created by Mason Prey on 2/13/16.
 */
public enum BossType {

    GOLEM,
    WITHER;

    public static BossType getType(String input) {
        switch (input.toUpperCase()) {
            case "GOLEM":
                return GOLEM;
            case "WITHER":
                return WITHER;
        }
        return null;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
