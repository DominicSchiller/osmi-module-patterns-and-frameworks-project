package de.thb.paf.scrabblefactory.utils;

import java.lang.reflect.Field;

import de.thb.paf.scrabblefactory.models.components.IComponent;

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
     * Clones all field values from the passed source component to the passed destination component.
     * @param srcComponent The source component to clone all field values form
     * @param destComponent The destination component to set all the cloned field values to
     */
    public static void cloneFieldValues(IComponent srcComponent, IComponent destComponent) {
        Field[] thisFields = srcComponent.getClass().getDeclaredFields();
        Field[] superFields = srcComponent.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = new Field[thisFields.length + superFields.length];
        System.arraycopy(thisFields, 0, fields, 0, thisFields.length);
        System.arraycopy(superFields, 0, fields, thisFields.length, superFields.length);

        for(Field field : fields) {
            try {
                Field otherField = destComponent.getClass().getDeclaredField(field.getName());
                otherField.setAccessible(true);
                Object fieldValue = otherField.get(destComponent);

                if(fieldValue != null) {
                    field.setAccessible(true);
                    field.set(srcComponent, fieldValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // we can safely ignore this case:
                // because we're just copying. But if there's
                // nothing to copy from we continue with the next possibly known field
            }
        }
    }
}
