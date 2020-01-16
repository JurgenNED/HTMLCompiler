package htmlcompiler.tags.neko;

import htmlcompiler.pojos.compile.ScriptBag;
import org.w3c.dom.Node;

public enum Head {;

    public static TagProcessor newHeadProcessor(final ScriptBag scripts) {
        return (file, document, element) -> {
            final String codeAtStart = scripts.getScriptAtHeadStart();
            if (!codeAtStart.isEmpty()) {
                final Node scriptStart = document.createElement("script");
                scriptStart.setTextContent(codeAtStart);
                element.insertBefore(scriptStart, element.getFirstChild());
            }

            final String codeAtEnd = scripts.getScriptAtHeadEnd();
            if (!codeAtEnd.isEmpty()) {
                final Node scriptEnd = document.createElement("script");
                scriptEnd.setTextContent(codeAtEnd);
                element.appendChild(scriptEnd);
            }

            return false;
        };
    }

}
