package htmlcompiler.compile.html;

import htmlcompiler.error.InvalidInput;
import org.w3c.dom.Element;

import java.io.File;

import static htmlcompiler.tools.HTML.*;
import static htmlcompiler.tools.IO.toLocation;

public enum Import {;

    public static TagProcessor newImportProcessor(final HtmlCompiler html) {
        return (inputDir, file, document, element) -> {
            final Element root = loadHtml(html, toSourceLocation(element, "src", file));
            if (root == null) deleteTag(element);
            else replaceTag(element, toElementOf(document, root));
            return true;
        };
    }

    private static File toSourceLocation(final Element element, final String attribute, final File file) throws InvalidInput {
        if (!element.hasAttribute(attribute)) throw new InvalidInput(String.format("<import> is missing '%s' attribute", attribute));
        return toLocation(file.getParentFile(), element.getAttribute(attribute), "<import> in %s has an invalid src location '%s'");
    }

}
