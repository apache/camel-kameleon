import { QuarkusTemplate } from "../templates/quarkus-template.js";
import Vue from '/js/vue.esm.browser.min.js'

const Quarkus = Vue.component('quarkus', {
  template: QuarkusTemplate
});

export { Quarkus }