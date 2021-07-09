const ClassicTemplate = `
<div>
    <section class="pf-c-page__main-section pf-m-light top-border">
        <div class="pf-l-split pf-m-gutter">
            <div class="pf-l-split__item">
                <div class="pf-c-content pf-l-flex">
                    <div class="pf-l-flex__item">
                        <h2 v-model="title" >{{current.pageTitle}}</h2>
                    </div>
                    <div class="pf-l-flex__item">
                        <select @change="onCamelChange" v-model="camelVersion" class="pf-c-form-control" id="version" name="version" aria-label="select version" required>
                            <option v-for="v in current.versions" v-bind:value="v">
                                {{ v.name + ' ' + v.suffix }}
                            </option>
                        </select>
                    </div>
                    <div v-show="type!='quarkus'" class="pf-l-flex__item">
                        <h2>with Java</h2>
                    </div>
                    <div v-show="type!='quarkus'" class="pf-l-flex__item">
                        <select v-model="javaVersion" class="pf-c-form-control" id="java" name="java" aria-label="select version" required>
                            <option v-for="v in camelVersion.javaVersions" v-bind:value="v">
                                {{ v }}
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="pf-l-split__item pf-m-fill"></div>
            <div v-show="showButton === false" class="pf-l-split__item">
                <span class="pf-c-spinner spinner" role="progressbar" aria-valuetext="Loading...">
                  <span class="pf-c-spinner__clipper"></span>
                  <span class="pf-c-spinner__lead-ball"></span>
                  <span class="pf-c-spinner__tail-ball"></span>
                </span>
            </div>
             <div v-show="showButton" class="pf-l-split__item">
                <button  :disabled="isButtonDisabled()" @click="generate()" class="pf-c-button pf-m-primary" type="button">{{getButtonCaption()}}</button>
            </div>
        </div>
    </section>
    <section class="pf-c-page__main-section classic">
        <div class="pf-l-grid pf-m-all-4-col-on-sm pf-m-all-4-col-on-md pf-m-all-4-col-on-lg pf-m-all-4-col-on-xl pf-m-gutter">
            <div class="pf-l-grid__item">
              <project></project>
              <selected></selected>
            </div>
            <div class="pf-l-grid__item pf-m-8-col-on-sm pf-m-8-col-on-lg pf-m-8-col-on-xl">
                <components></components>
            </div>
        </div>
    </section>

</div>
`;

export { ClassicTemplate };
