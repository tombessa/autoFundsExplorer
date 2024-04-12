package com.github.tombessa.autofundsexplorer.util;
import java.lang.reflect.Field;

public class MergeUtil {
    public <T> T merge(T local, T remote) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = local.getClass();
        Object merged = clazz.newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object localValue = field.get(local);
            Object remoteValue = field.get(remote);
            if (localValue != null) {
                switch (localValue.getClass().getSimpleName()) {
                    case "Default":
                    case "PersistentBag":
                        break;
                    default:
                        if(field.getName().contains("version")){
                            field.set(merged, localValue);
                        }else{
                            field.set(merged, (remoteValue != null) ? remoteValue : localValue);

                        }
                }
            }
        }
        return (T) merged;
    }


}
