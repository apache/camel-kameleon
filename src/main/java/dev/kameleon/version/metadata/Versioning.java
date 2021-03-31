package dev.kameleon.version.metadata;

public class Versioning {
    public String latest;
    public String release;
    public String lastUpdated;
    public Versions versions;

    public Versioning() {
    }

    @Override
    public String toString() {
        return "Versioning{" +
                "latest='" + latest + '\'' +
                ", release='" + release + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", versions=" + versions +
                '}';
    }
}
