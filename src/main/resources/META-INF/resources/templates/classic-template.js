const ClassicTemplate = `
<div>
    <section class="pf-c-page__main-section pf-m-light">
        <div class="pf-l-split pf-m-gutter">
            <div class="pf-l-split__item">
                <div class="pf-c-content pf-l-flex">
                    <div class="pf-l-flex__item">
                        <h1 v-model="title" >{{title}}</h1>
                    </div>
                    <div class="pf-l-flex__item">
                        <select @change="onChange" v-model="camelVersion" class="pf-c-form-control" id="version" name="version" aria-label="select version" required>
                            <option v-for="v in camelVersions" v-bind:value="v">
                                {{ v }}
                            </option>
                        </select>
                    </div>
                </div>
                <p v-model="subtitle" >{{subtitle}}</p>
            </div>
            <div class="pf-l-split__item pf-m-fill"></div>
            <div class="pf-l-split__item">
                    <button v-show="showButton" @click="generate()" class="pf-c-button pf-m-primary" type="button">Generate</button>
                    <span v-show="showButton === false" class="pf-c-spinner" role="progressbar" aria-valuetext="Loading...">
                      <span class="pf-c-spinner__clipper"></span>
                      <span class="pf-c-spinner__lead-ball"></span>
                      <span class="pf-c-spinner__tail-ball"></span>
                    </span>
            </div>
        </div>
    </section>
    <section class="pf-c-page__main-section">
        <project></project>
    </section>
    <section class="pf-c-page__main-section no-padding-top">
        <div class="pf-l-grid pf-m-all-6-col-on-sm pf-m-all-4-col-on-md pf-m-all-2-col-on-lg pf-m-all-1-col-on-xl">
          <div class="pf-l-grid__item pf-m-8-col-on-sm pf-m-4-col-on-lg pf-m-6-col-on-xl">
                <components></components>
          </div>
          <div class="pf-l-grid__item pf-m-8-col-on-sm pf-m-4-col-on-lg pf-m-6-col-on-xl">
                <selected></selected>
          </div>
        </div>
    </section>

</div>
`

export { ClassicTemplate }