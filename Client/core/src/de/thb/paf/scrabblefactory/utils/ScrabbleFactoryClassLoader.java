package de.thb.paf.scrabblefactory.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class which helps creating class instances defined just by it's class name.
 *
 * @author Dominic Schiller
 * @version 1.0
 * @since 1.0
 */

public class ScrabbleFactoryClassLoader {


    /**
     * Instantiate a class given by it's class name and required params.
     * @param packageName The java package the class is located in
     * @param className The full class name to create an instance from
     * @param params List of parameters which will be handed over a matching class constructor (Note: optional)
     * @return The created instance (Note: might be null)
     */
    public static Object createInstance(String packageName, String className, Object... params) {
        Class<?> classType = getClassForName(packageName, className);
        return createInstance(classType, params);
    }

    /**
     * Instantiate a class given by it's class name and required params.
     * @param className The full class name to create an instance from (including package name)
     * @param params List of parameters which will be handed over a matching class constructor (Note: optional)
     * @return The created instance (Note: might be null)
     */
    public static Object createInstance(String className, Object... params) {
        Class<?> classType = getClassForName(className);
        return createInstance(classType, params);
    }

    /**
     * Instantiate a class given by it's class name and required params.
     * @param classType The class type to create an instance from
     * @param params List of parameters which will be handed over a matching class constructor (Note: optional)
     * @return The created instance (Note: might be null)
     */
    public static Object createInstance(Class<?> classType, Object... params) {
        Constructor[] constructors = classType.getDeclaredConstructors();

        Constructor targetConstructor = null;
        List<Object> orderedParams = new ArrayList<>();

        for(Constructor constructor : constructors) {
            Class<?>[] constructorParamTypes = constructor.getParameterTypes();
            if(constructorParamTypes.length == params.length) {
                targetConstructor = constructor;
                for(Class<?> constructorParamType : constructorParamTypes) {
                    for(Object param : params) {
                        Class<?> paramClassType = param.getClass();
                        if(constructorParamType.equals(paramClassType)) {
                            orderedParams.add(param);
                        }
                    }
                }

                // if we did not found the right constructor we need to clear the stored ordered params list
                if(orderedParams.size() == params.length) {
                    break;
                } else {
                    orderedParams.clear();
                }
            }
        }

        Object instance = null;

        if(targetConstructor != null && orderedParams.size() == params.length) {
            try {
                instance = targetConstructor.newInstance(orderedParams.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    /**
     * Get Java class type for provided class name.
     * @param className The full class name (including package name)
     * @return The Java class type (Note: might be null)
     */
    public static Class<?> getClassForName(String className) {

        try  {
            return Class.forName(className);
        }  catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Get Java class type for provided package and class name.
     * @param packageName The java package the class is located in
     * @param className The full class name
     * @return The Java class type (Note: might be null)
     */
    public static Class<?> getClassForName(String packageName, String className) {
        return getClassForName(packageName + "." + className);
    }
}
