package de.thb.paf.scrabblefactory.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class which helps cloning a component's data to another component.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class CloneComponentHelper {

    /**
     * Private Constructor
     */
    private CloneComponentHelper() {
        // this is a static class
    }

    /**
     * Clones all field values from the passed source object to the passed destination object.
     * @param srcObject The source object to clone all field values from
     * @param destObject The destination objectto set all the cloned field values to
     */
    public static void cloneFieldValues(Object srcObject, Object destObject) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(srcObject.getClass().getDeclaredFields()));

        boolean hasSuperClass = true;
        Class<?> classReference = srcObject.getClass();
        do {
            Class<?> superClass = classReference.getSuperclass();
            if(superClass != null && classReference != superClass) {
                classReference = superClass;
                Field[] superFields = superClass.getDeclaredFields();
                fields.addAll((Arrays.asList(superFields)));
            } else {
                hasSuperClass = false;
            }
        } while(hasSuperClass);

        for(Field field : fields) {
            copyFieldValues(field, srcObject, destObject, destObject.getClass());
        }
    }

    /**
     * Copy assigned field value from source to destination object.
     * @param field The field to copy the value from
     * @param srcObject The source object
     * @param destObject The destination object
     * @param srcClassType the source class type
     */
    private static void copyFieldValues(Field field, Object srcObject, Object destObject, Class<?> srcClassType) {
        try {
            Field srcField = srcClassType.getDeclaredField(field.getName());
            srcField.setAccessible(true);
            Object srcFieldValue = srcField.get(srcObject);

            if(srcFieldValue != null) {
                field.setAccessible(true);
                field.set(destObject, srcFieldValue);
            }
        } catch (NoSuchFieldException e) {
            Class<?> srcSuperClass = srcClassType.getSuperclass();
            if(srcSuperClass != null && srcSuperClass != srcClassType) {
                // try to get the field value from the super class
                copyFieldValues(field, srcObject, destObject, srcSuperClass);
            }
        } catch(IllegalAccessException e) {
            // ignore
        }
    }
}
