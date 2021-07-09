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

const MainTemplate = `
<div class="pf-c-page main" id="page-default-nav">
    <a class="pf-c-skip-to-content pf-c-button pf-m-primary" href="#main-content-page-default-nav">Skip to content</a>
    <header class="pf-c-page__header pf-c-light">
        <div class="pf-c-page__header-brand">
            <a class="logo" href="../.."></a>
        </div>
        <div class="pf-c-page__header-nav navigation" id="navigation">
          <nav class="pf-c-nav pf-m-horizontal pf-m-light" id="page-default-nav-example-primary-nav" aria-label="Global">
              <ul class="pf-c-nav__list">
                  <li class="pf-c-nav__item">
                      <router-link to="/standalone" v-bind:class="[$route.path === '/standalone' ? 'pf-m-current' : '', 'pf-c-nav__link']">Standalone</router-link>
                  </li>
                  <li class="pf-c-nav__item">
                      <router-link to="/spring" v-bind:class="[$route.path === '/spring' ? 'pf-m-current' : '', 'pf-c-nav__link']">Spring Boot</router-link>
                  </li>
                  <li class="pf-c-nav__item">
                      <router-link to="/cdi" v-bind:class="[$route.path === '/cdi' ? 'pf-m-current' : '', 'pf-c-nav__link']">CDI</router-link>
                  </li>
                  <li class="pf-c-nav__item">
                      <router-link to="/quarkus" v-bind:class="[$route.path === '/quarkus' ? 'pf-m-current' : '', 'pf-c-nav__link']">Quarkus</router-link>
                  </li>
                  <li class="pf-c-nav__item">
                      <router-link to="/kamelet" v-bind:class="[$route.path === '/kamelet' ? 'pf-m-current' : '', 'pf-c-nav__link']">Kamelet</router-link>
                  </li>
              </ul>
          </nav>
        </div>
        <div class="pf-c-page__header-tools">
            <a class="pf-c-page__header-brand-link">
                <h1 class="header">KAMELEON {{version}}</h1>
            </a>
            <a class="github-link" href="https://github.com/mgubaidullin/kameleon">
               <img class="github" src="/img/fa-github.svg" alt="Github"/>
            </>
        </div>
    </header>

    <main role="main" class="pf-c-page__main" tabindex="-1">
        <router-view></router-view>
    </main>
</div>
`

export { MainTemplate }
