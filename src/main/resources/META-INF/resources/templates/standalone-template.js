const StandaloneTemplate = `
<div>
    <section class="pf-c-page__main-section pf-m-light">
        <div class="pf-l-split pf-m-gutter">
            <div class="pf-l-split__item">
                <div class="pf-c-content">
                    <h1 v-model="camelVersionN" >Camel Standalone ({{camelVersionN}})</h1>
                    <p>Maven project for Camel routes running Camel standalone (camel-main)</p>
                </div>
            </div>
            <div class="pf-l-split__item pf-m-fill"></div>
            <div class="pf-l-split__item">
                    <button @click="generate()" class="pf-c-button pf-m-primary" type="button">Generate</button>
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

export { StandaloneTemplate }