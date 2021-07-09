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

const SelectedTemplate = `
<div v-show="selected.length != 0" class="selected">
   <div class="title">Selected artifacts ({{selected.length}})</div>
    <table class="pf-c-table pf-m-compact pf-m-grid-lg" role="grid" aria-label="This is a compact table example" id="page-layout-table-compact-table">
      <tbody role="rowgroup">
        <tr role="row" v-for="comp in selected" :key="comp">
            <td role="cell" data-label="Component">
                <div class="component">
                    <span class="component">{{comp.title}}</span>
                    <span class="artifact-id">{{comp.artifactId}}</span>
                </div>
            </td>
            <td class="pf-c-table__check" role="cell">
              <button class="pf-c-button pf-m-link pf-m-small" type="button" v-on:click="removeComponent(comp)">
                <span class="pf-c-button__icon pf-m-end">
                  <i class="fas fa-minus" aria-hidden="true"></i>
                </span>
              </button>
            </td>
        </tr>
      </tbody>
    </table>
  </div>
`

export { SelectedTemplate }
