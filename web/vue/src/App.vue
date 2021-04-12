<template>
  <div id="app">
    <header>
      <span>To Do List</span>
    </header>
    <main>
      <Name/>
      <router-view></router-view>
    </main>
  </div>
</template>
<script>
  import Name from './components/Name';
  export default {
    name: 'app',
    components: {
      Name
    }
  }
</script>