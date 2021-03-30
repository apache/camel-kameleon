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
    getEventHub().$on('versionChanged', this.getComponents);
  },
  beforeDestroy: function () {
    getEventHub().$off('versionChanged', this.getComponents);
  },
  methods: {
    selectComponent: function (comp){
        getEventHub().$emit('select', comp);
    },
    getComponents: function (camelVersion){
        this.components =[];
        axios('/component/' + this.type + '/' + camelVersion)
        .then(response => {
            this.components = response.data;
            this.setFilter();
        })
    },
    setFilter: function(){
        this.filtered = this.components.filter(e => e.name.toLowerCase().includes(this.filter.toLowerCase()))
    }
  },
  template: ComponentsTemplate
});

export { Components }