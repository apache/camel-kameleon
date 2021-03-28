import Vue from '/js/vue.esm.browser.min.js'
import { ProjectTemplate } from "../templates/project-template.js"

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

export { Project }