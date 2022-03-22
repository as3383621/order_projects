<template>
  <div id="app">
    <router-view :key="$route.fullPath" />
  </div>
</template>

<style>
  #app{
    height: 100%;
  }
</style>