const ComponentsTemplate = `
<div class="pf-c-card components">
   <div v-model="components" class="pf-c-card__title">Camel components ({{components.length}}):</div>
    <div class="pf-c-toolbar no-padding" id="page-layout-table-compact-toolbar">
      <div class="pf-c-toolbar__content">
        <div class="pf-c-toolbar__content-section pf-m-nowrap">
          <div class="pf-c-toolbar__group pf-m-toggle-group pf-m-show-on-lg">
            <div class="pf-c-toolbar__item pf-m-search-filter ">
              <div class="pf-c-input-group" aria-label="search filter" role="group">
                <input v-model="filter" @input="setFilter" @change="setFilter" class="pf-c-form-control" type="search" id="page-layout-table-compact-toolbar--search-filter-input" name="page-layout-table-compact-toolbar-search-filter-input" aria-label="input with dropdown and button" aria-describedby="page-layout-table-compact-toolbar--button" />
              </div>
            </div>
          </div>
        </div>
        <div class="pf-c-toolbar__expandable-content pf-m-hidden" id="page-layout-table-compact-toolbar-expandable-content" hidden></div>
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
                    <button class="pf-c-button pf-m-link pf-m-small no-padding" type="button" v-on:click="selectComponent(comp)">
                        <span class="pf-c-button__icon pf-m-end">
                            <i class="fas fa-plus" aria-hidden="true"></i>
                        </span>
                    </button>
              </div>
            </div>
        </div>
    </section>
  </div>
`

export { ComponentsTemplate }