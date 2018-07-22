package htmlcompiler.compiler.processors;

import htmlcompiler.util.Loader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

public interface TagProcessor {
    boolean process(Loader loader, File file, Document document, Element element) throws Exception;

    static boolean notEmpty(final String code) {
        return code != null && !code.isEmpty();
    }

    static boolean isJavaScript(final Element script) {
        return !script.hasAttribute("type") || script.getAttribute("type").equalsIgnoreCase("text/javascript");
    }
    static boolean isTypeScript(final Element script) {
        return script.hasAttribute("type") && script.getAttribute("type").equalsIgnoreCase("text/typescript");
    }
    static boolean isHtml(final Element script) {
        return script.hasAttribute("type") && script.getAttribute("type").equalsIgnoreCase("text/x-jquery-tmpl");
    }
    static boolean isCss(final Element link) {
        return !link.hasAttribute("type") || link.getAttribute("type").equalsIgnoreCase("text/css");
    }
}
