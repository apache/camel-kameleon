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

const ProjectTemplate = `
<div class="project">
   <div class="title">Maven Project</div>
   <div class="form">
        <form novalidate class="pf-c-form pf-m-horizontal">
            <div class="pf-c-form__group">
            <div class="pf-c-form__group-label">
              <label class="pf-c-form__label" for="form-group">
                <span class="pf-c-form__label-text">Group</span>
                <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
              </label>
            </div>
            <div class="pf-c-form__group-control">
              <input v-model="group" class="pf-c-form-control" required type="text" id="form-group" name="form-group" aria-describedby="form-group" />
            </div>
            </div>
            <div class="pf-c-form__group">
              <div class="pf-c-form__group-label">
                <label class="pf-c-form__label" for="form-artifact">
                  <span class="pf-c-form__label-text">Artifact</span>
                  <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
                </label>
              </div>
              <div class="pf-c-form__group-control">
                <input v-model="artifact" class="pf-c-form-control" required type="text" id="form-artifact" name="form-artifact" aria-describedby="form-artifact" />
              </div>
            </div>
            <div class="pf-c-form__group">
                <div class="pf-c-form__group-label">
                  <label class="pf-c-form__label" for="form-version">
                    <span class="pf-c-form__label-text">Version</span>
                    <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
                  </label>
                </div>
                <div class="pf-c-form__group-control">
                  <input v-model="version" class="pf-c-form-control" required type="text" id="form-version" name="form-version" aria-describedby="form-version" />
                </div>
            </div>
        </form>
   </div>
</div>
`

export { ProjectTemplate }
