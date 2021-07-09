package dev.kameleon.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.util.List;

public class CamelComponent extends AbstractComponent {
    private String firstVersion;
    private String artifactId;
    private Boolean deprecated;
    private Boolean nativeSupported;

    public CamelComponent() {
    }

    public CamelComponent(String name, String title, String description, String supportLevel, List<String> labels, String firstVersion, String artifactId, Boolean deprecated, Boolean nativeSupported) {
        super(name, title, description, supportLevel, labels);
        this.firstVersion = firstVersion;
        this.artifactId = artifactId;
        this.deprecated = deprecated;
        this.nativeSupported = nativeSupported;
    }

    public String getFirstVersion() {
        return firstVersion;
    }

    public void setFirstVersion(String firstVersion) {
        this.firstVersion = firstVersion;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Boolean getNativeSupported() {
        return nativeSupported;
    }

    public void setNativeSupported(Boolean nativeSupported) {
        this.nativeSupported = nativeSupported;
    }
}
