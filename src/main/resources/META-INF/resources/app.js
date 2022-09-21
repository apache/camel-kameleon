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

import Vue from '/js/vue.esm.browser.min.js'
import { MainTemplate } from './templates/main-template.js'
import { Classic } from './components/classic.js'

// Router
Vue.use(VueRouter);
const router = new VueRouter({
  routes: [
    { path: '/', redirect: "/standalone" },
    { path: '/standalone', component: Classic, props: { type: 'standalone', title: 'Standalone' } },
    { path: '/spring', component: Classic, props: { type: 'spring', title: 'Spring' } },
    { path: '/quarkus', component: Classic, props: { type: 'quarkus', title: 'Quarkus'} },
    { path: '/kamelet', component: Classic, props: { type: 'kamelet', title: 'Kamelet'} }
  ]
})

// Application
var client = new Vue({
  el: '#app',
  data: function () {
      return {
          version: ""
      }
  },
  mounted: async function () {
          this.getVersion();
      },
  methods: {
      getVersion: function (event) {
          axios.get('/configuration/version')
              .then(response => {
                  this.version = response.data;
              })
      }
  },
  router,
  template: MainTemplate,
})
