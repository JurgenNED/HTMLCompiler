package htmlcompiler.compilers.tags;

import htmlcompiler.pojos.compile.StyleType;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.Path;

import static htmlcompiler.compilers.CssCompiler.compressCssCode;
import static htmlcompiler.pojos.compile.StyleType.css;
import static htmlcompiler.pojos.compile.StyleType.detectStyleType;
import static htmlcompiler.tools.IO.toLocation;

public enum Style {;

    public static TagVisitor newStyleVisitor() {
        return (TagVisitor.TailVisitor) (config, file, element, depth) -> {
            if (element.hasAttr("inline")) {
                final Path location = toLocation(file, element.attr("src"), "style tag in %s has an invalid src location '%s'");

                final StyleType type = detectStyleType(element, css);
                TagParsing.setData(element, compressIfRequested(element, type.compile(location)));
                TagParsing.removeAttributes(element, "inline", "compress", "src", "type");

                final Element previousSibling = TagParsing.previousElementSibling(element);
                if (TagParsing.isInlineStyle(previousSibling) && !TagParsing.isScriptEmpty(previousSibling)) {
                    TagParsing.setData(element, previousSibling.data() + element.data());
                    previousSibling.attr("htmlcompiler", "delete-me");
                }
                return;
            }

            if (!TagParsing.isStyleEmpty(element)) {
                final StyleType type = detectStyleType(element, css);
                TagParsing.setData(element, compressIfRequested(element, type.compile(element.data(), file)));
                TagParsing.removeAttributes(element,"compress", "type");

                final Element previousSibling = TagParsing.previousElementSibling(element);
                if (TagParsing.isInlineStyle(previousSibling) && !TagParsing.isStyleEmpty(previousSibling)) {
                    TagParsing.setData(element, previousSibling.data() + element.data());
                    previousSibling.attr("htmlcompiler", "delete-me");
                }

                return;
            }
            if (element.hasAttr("to-absolute")) {
                TagParsing.makeAbsolutePath(element, "src");
            }
        };
    }

    private static String compressIfRequested(final Element element, final String code) throws IOException {
        if (code == null || code.isEmpty()) return code;
        return element.hasAttr("compress") ? compressCssCode(code) : code;
    }

}
