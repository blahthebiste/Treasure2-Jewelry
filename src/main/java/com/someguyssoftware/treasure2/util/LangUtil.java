package com.someguyssoftware.treasure2.util;

import com.someguyssoftware.treasure2.Treasure;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Mark Gottschling on 8/11/2024
 */
public class LangUtil {
    public static final String NEWLINE = "";
    public static final String INDENT2 = "  ";
    public static final String INDENT4 = "    ";

    public static String name(String modid, String prefix, String suffix) {
        return StringUtils.stripEnd(prefix.trim(), ".")
                + "."
                + modid
                + "."
                + StringUtils.stripStart(suffix.trim(), ".");
    }

    // general tooltip helper
    public static String tooltip(String modid, String suffix) {
        return name(modid, "tooltip", suffix);
    }

    // treasure2 specific helper
    public static String tooltip(String suffix) {
        return name(Treasure.MODID, "tooltip", suffix);
    }
}
