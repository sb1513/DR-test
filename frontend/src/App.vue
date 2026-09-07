<script setup>
import Home from "./pages/home/Home.vue";
import Sidebar from "./components/sidebar/Sidebar.vue";
</script>

<template>
  <Sidebar>
    <router-view/>
  </Sidebar>
</template>
