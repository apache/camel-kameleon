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
import { SelectedTemplate } from "../templates/selected-template.js"
import getEventHub from './event-hub.js'

const Selected = Vue.component('selected', {
  data: function () {
    return {
        selected: []
    }
  },
    created: function () {
      getEventHub().$on('select', this.selectComponent);
      getEventHub().$on('clearSelection', this.clearSelection);
    },
    beforeDestroy: function () {
      getEventHub().$off('select', this.selectComponent);
      getEventHub().off('clearSelection', this.clearSelection);
    },
  methods: {
      selectComponent: function (event) {
        if (this.selected.filter(e => e.name === event.name).length === 0){
            this.selected.push(event);
        }
      },
      clearSelection: function (event) {
          this.selected =[]
        },
      removeComponent: function (comp){
        getEventHub().$emit('unselect', comp);
        var index = this.selected.indexOf(comp);
        this.selected.splice(index, 1);
      }
    },
  template: SelectedTemplate
});

export { Selected }
