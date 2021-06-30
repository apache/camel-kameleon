const SelectedTemplate = `
<div v-show="selected.length != 0" class="pf-c-card selected">
   <div class="pf-c-card__title">Selected components: ({{selected.length}})</div>
    <div class="pf-c-toolbar" id="page-layout-table-compact-toolbar">
      <div class="pf-c-toolbar__content">
        <div class="pf-c-toolbar__content-section pf-m-nowrap">
        </div>
        <div class="pf-c-toolbar__expandable-content pf-m-hidden" id="page-layout-table-compact-toolbar-expandable-content" hidden></div>
      </div>
    </div>
    <table class="pf-c-table pf-m-compact pf-m-grid-lg" role="grid" aria-label="This is a compact table example" id="page-layout-table-compact-table">
      <tbody role="rowgroup">
        <tr role="row" v-for="comp in selected" :key="comp">
            <td role="cell" data-label="Component">
                <div class="component">
                    <span class="component">{{comp.name}}</span>
                    <span class="artifact-id">{{comp.artifactId}}</span>
                </div>
            </td>
            <td class="pf-c-table__check" role="cell">
              <button class="pf-c-button pf-m-link pf-m-small" type="button" v-on:click="removeComponent(comp)">
                <span class="pf-c-button__icon pf-m-end">
                  <i class="fas fa-minus" aria-hidden="true"></i>
                </span>
              </button>
            </td>
        </tr>
      </tbody>
    </table>
  </div>
`

export { SelectedTemplate }