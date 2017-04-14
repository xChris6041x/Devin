package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.DevinException;

import java.util.HashMap;
import java.util.Map;

/**
 * A class with all static methods for converting strings into objects.
 *
 * @author Christopher Bishop
 */
public final class ObjectParsing {

    private static Map<Class<?>, ObjectParser> parsers = new HashMap<>();

    private ObjectParsing() {
    }

    /**
     * Add the {@code objParser} parser for the type {@code type}.
     *
     * @param type
     * @param objParser
     */
    public static void registerParser(Class<?> type, ObjectParser objParser) {
        parsers.put(type, objParser);
    }

    /**
     * Parse the string {@code str} to a object with the type {@code type}
     *
     * @param type
     * @return
     */
    public static Object parseObject(Class<?> type, ArgumentStream args) throws DevinException {
        ObjectParser objParser = parsers.get(type);
        if (objParser == null) {
            if (type.isEnum()) {
                String arg = args.next();

                // Check if the next string matches one of the enum values.
                for (Object value : type.getEnumConstants()) {
                    if (value.toString().equalsIgnoreCase(arg)) {
                        return value;
                    }
                }
                throw new DevinException(arg + " is not a valid value.");
            } else {
                throw new DevinException("There is no registered object parser for " + type.getCanonicalName() + ".");
            }
        } else {
            return objParser.parseObject(args);
        }
    }

    /**
     * @param type
     * @return whether a parser exists for {@code type} or is an enum.
     */
    public static boolean parserExistsFor(Class<?> type) {
        return type.isEnum() || parsers.get(type) != null;
    }

}
