package io.xchris6041x.devin.injection;

import io.xchris6041x.devin.AnsiColor;
import io.xchris6041x.devin.Devin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Injects objects into fields with the Inject annotation.
 *
 * @author Christopher Bishop
 */
@SuppressWarnings("ALL")
public class Injector {

    private List<InjectedObject> injections;

    public Injector() {
        this.injections = new ArrayList<>();
    }

    /**
     * Add an object that will be available for injection when a specific name is used.
     *
     * @param obj
     * @param name
     * @throws IllegalArgumentException - When there is already an object with the same name and type.
     */
    public void add(Object obj, String name) throws IllegalArgumentException {
        for (InjectedObject io : injections) {
            if (io.isSimilarTo(name, obj))
                throw new IllegalArgumentException("Cannot have two objects with the same name and type.");
        }
        injections.add(new InjectedObject(name, obj));
    }

    /**
     * Add an object that will be available for injection with no specific name.
     *
     * @param obj
     * @throws IllegalArgumentException - When there is already an object with the same type and no name.
     */
    public void add(Object obj) throws IllegalArgumentException {
        add(obj, "");
    }

    /**
     * Inject an object with available injections.
     *
     * @param obj             - The object to inject.
     * @param localInjections - Injections that will only be applied to this object. These take prescience over the global injections.
     */
    public void inject(Object obj, InjectedObject... localInjections) {
        Devin.debug("Injecting " + obj.getClass().getCanonicalName() + ": ");
        Devin.debug("---------------------------------------------------------------");

        for (Field field : obj.getClass().getDeclaredFields()) {
            // Get Annotation.
            Inject inject = field.getAnnotation(Inject.class);
            if (inject == null) continue;

            Devin.debug("\tAttempting to inject " + AnsiColor.DARK_CYAN + field.getName() + AnsiColor.RESET + ":");

            // Starting injecting field.
            if (localInjections != null && injectField(obj, field, localInjections)) {
                Devin.debug(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
            } else if (injectField(obj, field, injections.toArray(new InjectedObject[0]))) {
                Devin.debug(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
            } else {
                Devin.debug(AnsiColor.RED + "\t\tFAILED: Cannot inject type " + field.getType().getCanonicalName() + " with name " + field.getName() + AnsiColor.RESET);
            }
        }

        Devin.debug();
    }

    /**
     * Inject an object with available injections.
     *
     * @param obj - The object to inject.
     */
    public void inject(Object obj) {
        inject(obj, new InjectedObject[0]);
    }

    /*
     * Inject one field.
     */
    private boolean injectField(Object obj, Field field, InjectedObject[] injections) {
        boolean injected = false;
        for (InjectedObject io : injections) {
            if ((io.getName().length() == 0 || field.getName().equalsIgnoreCase(io.getName())) && field.getType().isAssignableFrom(io.getObject().getClass())) {
                try {
                    boolean accessible = field.isAccessible();

                    field.setAccessible(true);
                    field.set(obj, io.getObject());
                    field.setAccessible(accessible);

                    injected = true;
                    break;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return injected;
    }

}
