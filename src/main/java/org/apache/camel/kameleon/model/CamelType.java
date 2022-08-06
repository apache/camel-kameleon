/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.kameleon.model;

import java.util.List;

public class CamelType {
    private String name;
    private String pageTitle;
    private String componentListTitle;
    private List<CamelVersion> versions;

    public CamelType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CamelVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<CamelVersion> versions) {
        this.versions = versions;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getComponentListTitle() {
        return componentListTitle;
    }

    public void setComponentListTitle(String componentListTitle) {
        this.componentListTitle = componentListTitle;
    }
}
