const ComponentsTemplate = `
<div class="components">
   <div v-model="components" class="title">Camel components ({{filtered.length}}):</div>
    <div class="pf-c-toolbar toolbar" >
      <div class="pf-c-toolbar__content">
        <div class="pf-c-toolbar__content-section pf-m-nowrap">
            <div class="pf-c-toolbar__item pf-m-search-filter">
                <input v-model="filter" @input="setFilter" @change="setFilter" class="pf-c-form-control" type="search"
                placeholder="Search Camel components, dataformats, languages, etc"/>
            </div>
        </div>
      </div>
    </div>
    <section class="pf-c-page__main-section components-grid pf-m-fill">
        <div class="pf-l-gallery pf-m-gutter">
            <div v-for="comp in filtered" :key="comp" class="pf-c-card pf-m-hoverable pf-m-compact component-card">
              <div class="pf-c-card__header">
                    <div class="pf-c-content pf-l-flex badge">
                        <div v-for="label in comp.labels" :key="label" class="pf-l-flex__item">
                            <span class="pf-c-badge pf-m-read">{{label}}</span>
                        </div>
                    </div>
              </div>
              <div class="pf-c-card__title component-title">
                <div class="pf-c-content pf-l-flex">
                    <p id="card-1-check-label">{{comp.name}}</p>
                    <span v-if="comp.supportLevel != 'Stable'" class="pf-c-badge pf-m-unread">{{comp.supportLevel}}</span>
                </div>
              </div>
              <div class="pf-c-card__body">
                <span class="description">{{comp.description}}</span>
              </div>
              <div class="pf-c-card__footer pf-l-flex">
                    <div class="pf-l-split__item pf-m-fill"></div>
                    <button class="pf-c-button pf-m-link pf-m-small no-padding" type="button" v-on:click="addComponent(comp)">
                        <span class="pf-c-button__icon pf-m-end">
                            <i v-show="!comp.selected" class="fas fa-plus" aria-hidden="true"></i>
                            <i v-show="comp.selected" class="fas fa-check" aria-hidden="true"></i>
                        </span>
                    </button>
              </div>
            </div>
        </div>
    </section>
  </div>
`

export { ComponentsTemplate }