const MainTemplate = `
<div class="pf-c-page" id="page-default-nav">
    <a class="pf-c-skip-to-content pf-c-button pf-m-primary" href="#main-content-page-default-nav">Skip to content</a>
    <header class="pf-c-page__header pf-c-light">
        <div class="pf-c-page__header-brand">
            <a class="logo" href="../.."></a>
            <a class="pf-c-page__header-brand-link">
                <h1 class="header">KAMELEON 0.0.9</h1>
            </a>
        </div>
        <div class="pf-c-page__header-tools">
            <a href="https://github.com/mgubaidullin/kameleon">
               <img class="github" src="/img/fa-github.svg" alt="Github"/>
            </>
        </div>
    </header>
    <div class="pf-c-page__sidebar pf-m-light" id="sidebar">
        <div class="pf-c-page__sidebar-body">
            <nav class="pf-c-nav pf-m-light" id="page-default-nav-example-primary-nav" aria-label="Global">
                <ul class="pf-c-nav__list">
                    <li class="pf-c-nav__item">
                        <router-link to="/standalone" v-bind:class="[$route.path === '/standalone' ? 'pf-m-current' : '', 'pf-c-nav__link']">Standalone</router-link>
                    </li>
                    <li class="pf-c-nav__item">
                        <router-link to="/spring" v-bind:class="[$route.path === '/spring' ? 'pf-m-current' : '', 'pf-c-nav__link']">Spring Boot</router-link>
                    </li>
                    <li class="pf-c-nav__item">
                        <router-link to="/cdi" v-bind:class="[$route.path === '/cdi' ? 'pf-m-current' : '', 'pf-c-nav__link']">CDI</router-link>
                    </li>
                    <li class="pf-c-nav__item">
                        <router-link to="/quarkus" v-bind:class="[$route.path === '/quarkus' ? 'pf-m-current' : '', 'pf-c-nav__link']">Quarkus</router-link>
                    </li>
                </ul>
            </nav>
        </div>
    </div>

    <main role="main" class="pf-c-page__main" tabindex="-1">
        <router-view></router-view>
    </main>
</div>
`

export { MainTemplate }