import Vue from '/js/vue.esm.browser.min.js'
import { MainTemplate } from './templates/main-template.js'
import { Classic } from './components/classic.js'

// Router
Vue.use(VueRouter);
const router = new VueRouter({
  routes: [
    { path: '/', redirect: "/standalone" },
    { path: '/standalone', component: Classic, props: { type: 'standalone', title: 'Standalone' } },
    { path: '/spring', component: Classic, props: { type: 'spring', title: 'Spring' } },
    { path: '/cdi', component: Classic, props: { type: 'cdi', title: 'CDI' } },
    { path: '/quarkus', component: Classic, props: { type: 'quarkus', title: 'Quarkus'} }
  ]
})

// Application
var client = new Vue({
  el: '#app',
  router,
  template: MainTemplate,
})
