import Vue from '/js/vue.esm.browser.min.js'
import { MainTemplate } from './templates/main-template.js'
import { Standalone } from './components/standalone.js'
import { Quarkus } from './components/quarkus.js'

// Router
Vue.use(VueRouter);
const router = new VueRouter({
  routes: [
    { path: '/', redirect: "/standalone" },
    { path: '/standalone', component: Standalone, props: { type: 'main' } },
    { path: '/spring', component: Standalone, props: { type: 'spring' } },
    { path: '/quarkus', component: Quarkus, name: "Quarkus" }
  ]
})

// Application
var client = new Vue({
  el: '#app',
  router,
  template: MainTemplate,
})
