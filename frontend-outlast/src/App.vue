<!--
 * @Description: 
 * @Author: lz
 * @Date: 2022-03-31 09:29:24
 * @LastEditTime: 2022-03-31 14:12:51
 * @LastEditors: lz
-->
<template>
  <a-config-provider :locale="zh_CN">
    <div id="app">
      <router-view />
    </div>
  </a-config-provider>
</template>
<script>
import zh_CN from "ant-design-vue/lib/locale-provider/zh_CN";
import moment from "moment";
import "moment/locale/zh-cn";
moment.locale("zh-cn");
export default {
  name: "App",
  data() {
    return { zh_CN };
  },
};
</script>
<style>
@import url(./assets/css/base.css);
</style>
