package dev.kameleon.version.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="metadata")
@XmlAccessorType(XmlAccessType.FIELD)
public class MavenMetadata {
    public String groupId;
    public String artifactId;
    public Versioning versioning;

    public MavenMetadata() {
    }

    @Override
    public String toString() {
        return "MavenMetadata{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", versioning=" + versioning +
                '}';
    }
}
