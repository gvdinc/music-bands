package main;

import java.util.Objects;

/**
 * Object that helps {@link CollectionHolder}
 * to convert String from xml file into {@link collections.MusicBand} object
 */
public class StringXMLItem {
    private String name = null;
    private String content = null;

    /**
     * getter
     */
    public String getName() {
        return name;
    }

    /**
     * setter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter
     */
    public String getContent() {
        return content;
    }

    /**
     * setter
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        return this.name == null && this.content == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }

    @Override
    public String toString() {
        return this.content;
    }
}
