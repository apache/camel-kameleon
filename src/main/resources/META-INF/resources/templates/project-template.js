const ProjectTemplate = `
<div class="project">
   <div class="title">Maven Project</div>
   <div class="form">
        <form novalidate class="pf-c-form pf-m-horizontal">
            <div class="pf-c-form__group">
            <div class="pf-c-form__group-label">
              <label class="pf-c-form__label" for="form-group">
                <span class="pf-c-form__label-text">Group</span>
                <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
              </label>
            </div>
            <div class="pf-c-form__group-control">
              <input v-model="group" class="pf-c-form-control" required type="text" id="form-group" name="form-group" aria-describedby="form-group" />
            </div>
            </div>
            <div class="pf-c-form__group">
              <div class="pf-c-form__group-label">
                <label class="pf-c-form__label" for="form-artifact">
                  <span class="pf-c-form__label-text">Artifact</span>
                  <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
                </label>
              </div>
              <div class="pf-c-form__group-control">
                <input v-model="artifact" class="pf-c-form-control" required type="text" id="form-artifact" name="form-artifact" aria-describedby="form-artifact" />
              </div>
            </div>
            <div class="pf-c-form__group">
                <div class="pf-c-form__group-label">
                  <label class="pf-c-form__label" for="form-version">
                    <span class="pf-c-form__label-text">Version</span>
                    <span class="pf-c-form__label-required" aria-hidden="true">&#42;</span>
                  </label>
                </div>
                <div class="pf-c-form__group-control">
                  <input v-model="version" class="pf-c-form-control" required type="text" id="form-version" name="form-version" aria-describedby="form-version" />
                </div>
            </div>
        </form>
   </div>
</div>
`

export { ProjectTemplate }