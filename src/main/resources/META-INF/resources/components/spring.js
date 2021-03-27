import { SpringTemplate } from "../templates/spring-template.js";
import Vue from '/js/vue.esm.browser.min.js'

const Spring = Vue.component('spring', {
  template: SpringTemplate
});

export { Spring }