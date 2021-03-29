import Vue from '/js/vue.esm.browser.min.js'
import { ClassicTemplate } from "../templates/classic-template.js"
import { Project } from "./project.js"
import { Components } from "./components.js"
import { Selected } from "./selected.js"
import getEventHub from './event-hub.js'

const Classic = Vue.component('classic', {
  props: ['type'],
  data: function () {
      return {
          title: '',
          subtitle: '',
          camelVersions: [],
          camelVersion: '',
          showButton: true
      }
    },
  components: {
    'project': Project,
    'components': Components,
    'selected': Selected
  },
  watch: {
    '$route.path': async function(val, oldVal){
        this.setTitle();
        getEventHub().$emit('clearSelection', '');
        await this.selectCamelVersion();
        this.setTitle();
        await this.selectComponents();
    }
  },
  mounted: async function () {
      this.setTitle();
      await this.selectCamelVersion();
      this.setTitle();
      await this.selectComponents();
  },
  methods: {
    setTitle: function(){
        if (this.type === 'main'){
            this.title = 'Camel Standalone'
            this.subtitle = 'Maven project for Camel routes running Camel standalone (camel-main)'
        } else if (this.type === 'spring') {
            this.title = 'Camel Spring Boot'
            this.subtitle = 'Maven project for Camel routes running Camel Spring Boot (camel-spring-boot)'
        } else if (this.type === 'quarkus') {
            this.title = 'Camel Quarkus'
            this.subtitle = 'Maven project for Camel routes running Camel Quarkus (camel-quarkus)'
        }
    },
    generate : async function(event){
        this.showButton = false;
        const project = this.$children.find(child => { return child.$options.name === "project"; });
        const sel = this.$children.find(child => { return child.$options.name === "selected"; });
        const selected = sel.selected.map((item) => item['component']).join(",");
        axios({
            method: 'get',
            url: '/generator/'+this.type+'/'+this.camelVersion+'/'+project.group+'/'+project.artifact+'/'+project.version+'/' + selected,
            responseType: 'arraybuffer'
        }).then(response => {
            this.forceFileDownload(response);
            this.showButton = true;
        }).catch(() => {
            this.showButton = true;
            console.log('error occured');
        });
    },
    forceFileDownload(response){
          const url = window.URL.createObjectURL(new Blob([response.data]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', response.headers['filename'])
          document.body.appendChild(link)
          link.click()
    },
    onChange : async function(event){
        getEventHub().$emit('components', []);
        this.selectComponents();
    },
    selectCamelVersion: async function (event) {
        var result = [];
        const vRequest = await axios.get('/version/' + this.type);
        this.camelVersions = vRequest.data;
        this.camelVersion = this.camelVersions[0];
    },
    selectComponents: async function (event) {
        var result = await axios.get('/component/' + this.type + '/' + this.camelVersion);
        getEventHub().$emit('components', result.data);
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
  template: ClassicTemplate
});

export { Classic }