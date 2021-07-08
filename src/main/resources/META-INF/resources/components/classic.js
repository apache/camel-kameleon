import Vue from '/js/vue.esm.browser.min.js'
import { ClassicTemplate } from "../templates/classic-template.js"
import { Project } from "./project.js"
import { Components } from "./components.js"
import { Selected } from "./selected.js"
import getEventHub from './event-hub.js'

const Classic = Vue.component('classic', {
    props: ['type', 'title'],
    data: function () {
        return {
            kameleon: {},
            current: {},
            camelVersion: {},
            javaVersion: '',
            showButton: true
        }
    },
    components: {
        'project': Project,
        'components': Components,
        'selected': Selected
    },
    watch: {
        '$route.path': async function (val, oldVal) {
            getEventHub().$emit('clearSelection', '');
            this.changeType();
        }
    },
    mounted: async function () {
        this.getConfiguration();
    },
    methods: {
        getConfiguration: function (event) {
            axios.get('/configuration')
                .then(response => {
                    this.kameleon = response.data;
                    this.changeType();
                })
        },
        changeType: function (event) {
            this.current = this.kameleon.types.find(e => e.name === this.type);
            if (!this.current.versions.some(e => e.name === this.camelVersion.name)) {
                this.camelVersion = this.current.versions[0];
            } else {
                this.camelVersion = this.current.versions.find(e => e.name === this.camelVersion.name);
            }
            this.onCamelChange();
        },
        onCamelChange: function (event) {
            this.javaVersion = this.camelVersion.javaVersions.includes(this.javaVersion) ? this.javaVersion : this.camelVersion.defaultJava;
            getEventHub().$emit('versionChanged', { type: this.type, camelVersion: this.camelVersion.name });
        },
        isButtonDisabled: function (event) {
            return this.current.name === "kamelet";
        },
        getButtonCaption: function (event) {
            return this.current.name === "kamelet" ? "Coming soon.." : "Generate";
        },
        generate: function (event) {
            this.showButton = false;
            const project = this.$children.find(child => { return child.$options.name === "project"; });
            const sel = this.$children.find(child => { return child.$options.name === "selected"; });
            const selected = sel.selected.map((item) => item['component']).join(",");
            const request = {
                camelType: this.type,
                camelVersion: this.camelVersion.name,
                javaVersion: this.javaVersion,
                groupId: project.group,
                artifactId: project.artifact,
                version: project.version,
                components: selected
            };
            axios({
                method: 'get',
                url: '/generator',
                params: request,
                responseType: 'blob'
             })
              .then(response => {
                this.forceFileDownload(response);
                this.showButton = true;
              })
              .catch(error => {
                console.log('error occured');
                console.log(error);
                this.showButton = true;
              });  
        },
        forceFileDownload(response) {
            const url = window.URL.createObjectURL(new Blob([response.data]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', response.headers['filename'])
            document.body.appendChild(link)
            link.click()
        },
    },
    template: ClassicTemplate
});

export { Classic }