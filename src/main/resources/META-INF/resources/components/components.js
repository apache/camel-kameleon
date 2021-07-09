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
import { ComponentsTemplate } from "../templates/components-template.js"
import getEventHub from './event-hub.js'

const Components = Vue.component('components', {
  data: function () {
    return {
        title: '',
        components: [],
        filtered: [],
        filter: ''
    }
  },
  created: function () {
    getEventHub().$on('versionChanged', this.getComponents);
    getEventHub().$on('unselect', this.unselectComponent);
    getEventHub().$on('select', this.selectComponent);
  },
  beforeDestroy: function () {
    getEventHub().$off('versionChanged', this.getComponents);
    getEventHub().$off('select', this.selectComponent);
    getEventHub().$off('unselect', this.unselectComponent);
  },
  methods: {
    addComponent: function (comp){
        getEventHub().$emit('select', comp);
    },
    selectComponent: function (comp){
        var index = this.filtered.indexOf(comp);
        if (index >= 0){
            comp.selected = true;
            this.filtered.splice(index, 1, comp);
        }
    },
    unselectComponent: function (comp){
        var index = this.filtered.findIndex(e => e.name == comp.name);
        console.log(index);
        comp.selected = false;
        this.filtered.splice(index, 1, comp);
    },
    getComponents: function (event){
        this.components =[];
        this.title = event.type === 'quarkus' ? 'Camel Quarkus extensions' : 'Camel components';
        axios('/component/' + event.type + '/' + event.camelVersion)
        .then(response => {
            this.components = response.data;
            this.setFilter();
        })
    },
    setFilter: function(){
         this.filtered = this.components.filter(e =>
            e.name.toLowerCase().includes(this.filter.toLowerCase())
            || e.labels.filter( x => x.toLowerCase().includes(this.filter.toLowerCase())).length > 0
        );
    }
  },
  template: ComponentsTemplate
});

export { Components }
