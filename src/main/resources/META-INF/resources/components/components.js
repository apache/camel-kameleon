import Vue from '/js/vue.esm.browser.min.js'
import { ComponentsTemplate } from "../templates/components-template.js"
import getEventHub from './event-hub.js'

const Components = Vue.component('components', {
  data: function () {
    return {
        components: [],
        filtered: [],
        filter: ''
    }
  },
  created: function () {
    getEventHub().$on('components', this.setComponents);
  },
  beforeDestroy: function () {
    getEventHub().$off('components', this.setComponents);
  },
  methods: {
    addComponent: function (comp){
        getEventHub().$emit('select', comp);
    },
    setComponents: function (comps){
        this.components = comps;
        this.setFilter();
    },
    setFilter: function(){
        this.filtered = this.components.filter(e => e.name.toLowerCase().includes(this.filter.toLowerCase()))
    }
  },
  template: ComponentsTemplate
});

export { Components }