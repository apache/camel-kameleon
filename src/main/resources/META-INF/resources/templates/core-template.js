const CoreTemplate = `
<div>
    <section class="pf-c-page__main-section pf-m-light">
        <div class="pf-l-split pf-m-gutter">
            <div class="pf-l-split__item">
                <div class="pf-c-content">
                    <h1>Camel Core</h1>
                    <p>Maven project generator for Camel Core</p>
                </div>
            </div>
            <div class="pf-l-split__item pf-m-fill"></div>
            <div class="pf-l-split__item">
                <div class="pf-c-form__group padding">
                    <label class="pf-c-form__label" for="topic">
                        <span class="pf-c-form__label-text">Camel version</span>
                        <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
                    </label>
                    <select @change="onChange" v-model="camelVersion" class="pf-c-form-control" id="version" name="version" aria-label="select version" required>
                        <option v-for="v in camelVersions" v-bind:value="v.name">
                            {{ v.name }}
                        </option>
                    </select>
                </div>
            </div>
        </div>
    </section>
    <section class="pf-c-page__main-section">
        <project></project>
    </section>
    <section class="pf-c-page__main-section">
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

export { CoreTemplate }