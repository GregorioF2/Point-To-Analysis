package tp_final.utils;
import soot.Unit;
import soot.tagkit.LineNumberTag;
import soot.tagkit.Tag;

import java.util.List;

/**
 * Utils file for working with {@link Unit}s
 */
public class UnitUtils {

    /**
     * Extracts the line number of a given unit. Not that if no line number is present, this method invokation will not fail.
     *
     * @param unit the target unit
     * @return the line number, or -1 if not found
     */
    public static int getLineNumberFromUnit(Unit unit) {
        return unit.getTags().stream()
                .filter(tag -> tag instanceof LineNumberTag)
                .findFirst()
                .map(tag -> (LineNumberTag) tag)
                // If no line numbers configured, return dummy one
                .orElseGet(() -> new LineNumberTag(-1))
                .getLineNumber();
    }
}
