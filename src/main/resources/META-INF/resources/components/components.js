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
        console.log(comp);
        var index = this.filtered.findIndex(e => e.component == comp.component);
        console.log(index);
        comp.selected = false;
        this.filtered.splice(index, 1, comp);
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
        );
    }
  },
  template: ComponentsTemplate
});

export { Components }