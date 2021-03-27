import { StandaloneTemplate } from "../templates/standalone-template.js";
import { ProjectTemplate } from "../templates/project-template.js";
import { ComponentsTemplate } from "../templates/components-template.js";
import { SelectedTemplate } from "../templates/selected-template.js";
import Vue from '/js/vue.esm.browser.min.js'
import getEventHub from './event-hub.js'

const Project = Vue.component('project', {
  data: function () {
    return {
        group: 'org.acme',
        artifact: 'camel',
        version: '1.0.0-SNAPSHOT'
    }
  },
  mounted: function () {
  },
  template: ProjectTemplate
});

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

const Selected = Vue.component('selected', {
  data: function () {
    return {
        selected: []
    }
  },
  created: function () {
      getEventHub().$on('select', this.selectComponent);
    },
    beforeDestroy: function () {
      getEventHub().$off('select', this.selectComponent);
    },
  methods: {
      selectComponent: function (event) {
        if (this.selected.filter(e => e.name === event.name).length === 0){
            this.selected.push(event);
        }
      },
      removeComponent: function (comp){
        var index = this.selected.indexOf(comp);
        this.selected.splice(index, 1);
      }
    },
  template: SelectedTemplate
});

const Standalone = Vue.component('standalone', {
  data: function () {
      return {
          camelVersion: '',
          camelVersionN: ''
      }
    },
  components: {
    'project': Project,
    'components': Components,
    'selected': Selected
  },
  mounted: function () {
      this.selectCamelVersion();
  },
  methods: {
    onChange : async function(event){
        console.log(this.camelVersion);
        this.selectCamelVersion();
    },
    selectCamelVersion: async function (event) {
        var result = [];
        const vRequest = await axios.get('https://api.github.com/repos/apache/camel/tags');
        var camelVersions = vRequest.data;
        this.camelVersion = (this.camelVersion === '' ? camelVersions[0].name : this.camelVersion);
        this.camelVersionN = (this.camelVersion.startsWith('camel-') ? this.camelVersion.replace('camel-', '') : this.camelVersionN);
        const cRequest = await axios.get('https://api.github.com/repos/apache/camel/contents/components?ref=' + this.camelVersion);
        // get Component names
        result = result.concat(await this.getNames(this.camelVersion, 'ROOT', cRequest.data, 'component'));
        // get Dataformat names
        result = result.concat(await this.getNames(this.camelVersion, 'dataformats', cRequest.data, 'dataformat'));
        // get Dataformat names
        result = result.concat(await this.getNames(this.camelVersion, 'languages', cRequest.data, 'language'));
        // get Other names
        result = result.concat(await this.getNames(this.camelVersion, 'others', cRequest.data, 'other'));
        getEventHub().$emit('components', result);
    },
    getNames: async function(version, folder, compList, type){
        var url = 'https://raw.githubusercontent.com/apache/camel/'+version+'/docs/components/modules/'+folder+'/nav.adoc';
        var result = [];
        var regexp= /(?<=\[).*?(?=\])/g;
        var nRequest = await axios.get(url);
        var names = nRequest.data.split("\n")
        compList
            .filter(element => element.name.startsWith('camel-'))
            .forEach(e => {
                var name = names.filter(n => n.includes(e.name.replace('camel-', ':')))[0];
                  if (name != null){
                      result.push({"artifact":e.name, "name":name.match(regexp)[0], "type":type});
                  }
            });
        return result;
    },
  },
  template: StandaloneTemplate
});

export { Standalone }