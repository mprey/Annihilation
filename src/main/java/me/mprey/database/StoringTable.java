package me.mprey.database;

import me.mprey.database.interf.DatabaseGetField;
import me.mprey.database.interf.DatabaseSetField;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public abstract class StoringTable {

    private Map<String, DatabaseField> fieldMap;

    public StoringTable() {
        this.fieldMap = new HashMap<>();
        this.initFields();
    }

    private void initFields() {
        fieldMap.clear();

        for (Method method : this.getClass().getMethods()) {
            DatabaseGetField getAnn = method.getAnnotation(DatabaseGetField.class);
            DatabaseSetField setAnn = method.getAnnotation(DatabaseSetField.class);
            if (getAnn == null && setAnn == null ) continue;

            String fieldName = getAnn != null ? getAnn.name() : setAnn.name();

            if (this.fieldMap.containsKey(fieldName)) {
                DatabaseField field = this.fieldMap.get(fieldName);
                if (getAnn == null)
                    field.setSetter(method);
                else
                    field.setGetter(method);
            } else {
                DatabaseField field = new DatabaseField();
                if (getAnn == null)
                    field.setSetter(method);
                else
                    field.setGetter(method);
                this.fieldMap.put(fieldName, field);
            }
        }
    }

    public Map<String, DatabaseField> getFieldMap() {
        return fieldMap;
    }

    public abstract String getTableName();

    public abstract String getKeyField();

    public abstract void load();

    public abstract void store();

    public abstract void setDefault();

}
