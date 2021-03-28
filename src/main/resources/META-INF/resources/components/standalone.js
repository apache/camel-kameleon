import Vue from '/js/vue.esm.browser.min.js'
import { StandaloneTemplate } from "../templates/standalone-template.js"
import { Project } from "./project.js"
import { Components } from "./components.js"
import { Selected } from "./selected.js"
import getEventHub from './event-hub.js'

const Standalone = Vue.component('standalone', {
  props: ['type'],
  data: function () {
      return {
          title: '',
          subtitle: '',
          camelVersion: '',
          camelVersionN: ''
      }
    },
  components: {
    'project': Project,
    'components': Components,
    'selected': Selected
  },
  watch: {
    '$route.path': function(val, oldVal){
      this.setTitle();
    }
  },
  mounted: async function () {
      this.setTitle();
      await this.selectCamelVersion();
      this.setTitle();
  },
  methods: {
    setTitle: function(){
        if (this.type === 'main'){
            this.title = 'Camel Standalone (' + this.camelVersionN + ')'
            this.subtitle = 'Maven project for Camel routes running Camel standalone (camel-main)'
        } else if (this.type === 'spring') {
            this.title = 'Camel Spring Boot (' + this.camelVersionN + ')'
            this.subtitle = 'Maven project for Camel routes running Camel Spring Boot (camel-spring-boot)'
        }
    },
    generate : async function(event){
        const project = this.$children.find(child => { return child.$options.name === "project"; });
        const sel = this.$children.find(child => { return child.$options.name === "selected"; });
        const selected = sel.selected.map((item) => item['artifact']).join(",");
        axios({
            method: 'get',
            url: '/generator/main/'+'3.8.0'+'/'+project.group+'/'+project.artifact+'/'+project.version+'/' + selected,
            responseType: 'arraybuffer'
        }).then(response => {
            this.forceFileDownload(response)
        }).catch(() => console.log('error occured'));
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
        console.log(this.camelVersion);
        this.selectCamelVersion();
    },
    selectCamelVersion: async function (event) {
        var result = [];
        const vRequest = await axios.get('/version/main');
        this.camelVersionN = vRequest.data;
        this.camelVersion = 'camel-' + this.camelVersionN;
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