package me.mprey.database;

import java.lang.reflect.Method;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class DatabaseField {

    private Method getter;
    private Method setter;

    public DatabaseField() {
        this(null, null);
    }

    public DatabaseField(Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }
}
