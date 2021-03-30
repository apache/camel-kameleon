const ComponentsTemplate = `
<div class="pf-c-card components">
   <div class="pf-c-card__title">Camel components:</div>
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
    <table class="pf-c-table pf-m-compact pf-m-grid-lg" role="grid" aria-label="This is a compact table example" id="page-layout-table-compact-table">
      <tbody role="rowgroup">
        <tr role="row" v-for="comp in filtered" :key="comp">
          <td role="cell" data-label="Type">{{comp.type}}</td>
          <td role="cell" data-label="Component">{{comp.name}}</td>
          <td class="pf-c-table__check" role="cell">
            <button class="pf-c-button pf-m-link pf-m-small" type="button" v-on:click="selectComponent(comp)">
              <span class="pf-c-button__icon pf-m-end">
                <i class="fas fa-plus" aria-hidden="true"></i>
              </span>
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
`

export { ComponentsTemplate }