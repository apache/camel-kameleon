package org.apache.camel.kameleon.model;

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

public class CamelVersion {
    private String name;
    private String suffix;
    private List<String> javaVersions;
    private String defaultJava;
    private String runtimeVersion; // ex. Quarkus version
    private String archetypeGroupId;
    private String archetypeArtifactId;
    private List<CamelComponent> components;

    public CamelVersion() {
    }

    public CamelVersion(String name, String suffix, List<String> javaVersions, String defaultJava, String runtimeVersion,
                        String archetypeGroupId, String archetypeArtifactId, List<CamelComponent> components) {
        this.name = name;
        this.suffix = suffix;
        this.javaVersions = javaVersions;
        this.defaultJava = defaultJava;
        this.runtimeVersion = runtimeVersion;
        this.archetypeGroupId = archetypeGroupId;
        this.archetypeArtifactId = archetypeArtifactId;
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<String> getJavaVersions() {
        return javaVersions;
    }

    public void setJavaVersions(List<String> javaVersions) {
        this.javaVersions = javaVersions;
    }

    public String getDefaultJava() {
        return defaultJava;
    }

    public void setDefaultJava(String defaultJava) {
        this.defaultJava = defaultJava;
    }

    public String getRuntimeVersion() {
        return runtimeVersion;
    }

    public void setRuntimeVersion(String runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
    }

    public String getArchetypeGroupId() {
        return archetypeGroupId;
    }

    public void setArchetypeGroupId(String archetypeGroupId) {
        this.archetypeGroupId = archetypeGroupId;
    }

    public String getArchetypeArtifactId() {
        return archetypeArtifactId;
    }

    public void setArchetypeArtifactId(String archetypeArtifactId) {
        this.archetypeArtifactId = archetypeArtifactId;
    }

    public List<CamelComponent> getComponents() {
        return components;
    }

    public void setComponents(List<CamelComponent> components) {
        this.components = components;
    }
}
