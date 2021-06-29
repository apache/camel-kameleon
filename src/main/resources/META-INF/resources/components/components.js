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
    getComponents: function (event){
        this.components =[];
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
        )
    }
  },
  template: ComponentsTemplate
});

export { Components }