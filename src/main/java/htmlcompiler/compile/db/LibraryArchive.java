package htmlcompiler.compile.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static htmlcompiler.compile.db.LibraryDescription.toLibraryDescription;
import static htmlcompiler.compile.db.LibraryKey.toLibraryKey;

public final class LibraryArchive {

    private final Map<LibraryKey, LibraryDescription> archive;
    public LibraryArchive(final Gson gson) throws IOException {
        this.archive = new HashMap<>();
        try (final Reader reader = new InputStreamReader(LibraryArchive.class.getResourceAsStream("/library-archive.json"))) {
            final List<LibraryRecord> list = gson.fromJson(reader, new TypeToken<ArrayList<LibraryRecord>>(){}.getType());
            for (final LibraryRecord record : list) {
                archive.put(toLibraryKey(record), toLibraryDescription(record));
            }
        }
    }

    public Element createTag(final Document document, final String name, final String version, final String type) {
        final LibraryDescription description = archive.get(new LibraryKey(name, version, type));
        final Element element = document.createElement(description.tagName);
        for (final Attribute attribute : description.attributes) {
            element.setAttribute(attribute.name, attribute.value);
        }
        return element;
    }

}
