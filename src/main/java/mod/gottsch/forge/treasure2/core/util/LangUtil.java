/*
 * This file is part of  Treasure2.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * All rights reserved.
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2.core.util;

import mod.gottsch.forge.treasure2.core.Treasure;
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
