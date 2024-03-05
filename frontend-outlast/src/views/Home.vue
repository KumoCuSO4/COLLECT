<!--
 * @Description: 
 * @Author: lz
 * @Date: 2022-04-02 22:16:41
 * @LastEditTime: 2022-04-02 23:24:55
 * @LastEditors: lz
-->
<template>
  <div class="home">
    <nav-bar :userrole="userrole" />
    <div class="body">
      <side-bar :userrole="userrole" />
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '../components/common/NavBar.vue'
import SideBar from '../components/common/SideBar.vue'
export default {
  name: 'home',
  components: {
    NavBar,
    SideBar,
  },
  data() {
    return {
      userrole: Number.parseInt(localStorage.getItem('$userrole')),
    }
  },
  created() {
    // if (!localStorage.getItem('$userrole')) {
    //   this.$router.replace('/login')
    // }
  },
}
</script>

<style lang="less" scoped>
.home {
  height: 100vh;
}
.body {
  position: relative;
  height: calc(100% - var(--navbar-height));
}
.content {
  position: absolute;
  left: var(--sidebar-width);
  width: calc(100% - var(--sidebar-width));
  height: 100%;
}
</style>
