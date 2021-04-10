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
        var index = this.selected.indexOf(comp);
        this.selected.splice(index, 1);
      }
    },
  template: SelectedTemplate
});

export { Selected }