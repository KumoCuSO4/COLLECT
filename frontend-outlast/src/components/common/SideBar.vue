<template>
  <div class="sidebar">
    <ul>
      <template v-for="{ id, name, role, path } of sideList">
        <li
          v-if="role === 0 || role === userrole"
          :class="['item', isRouteActive(path) ? 'active' : '']"
          :key="id"
          @click="onTabRouter(path)"
        >
          {{ name }}
        </li>
      </template>
    </ul>
  </div>
</template>

<script>
import { getTaskInformation } from "@/api/task";
export default {
  name: "sidebar",
  props: {
    userrole: {
      type: Number,
      default: localStorage.getItem("$userrole"),
    },
  },
  data() {
    return {
      sideList: [
        {
          id: "1",
          name: "任务广场",
          role: 0,
          path: "/home/square",
        },
        {
          id: "99",
          name: "规则设置",
          role: 0,
          path: "/home/settings",
        },
        {
          id: "5",
          name: "历史任务",
          role: 2,
          path: "/home/history",
        },
        {
          id: "2",
          name: "已接收任务",
          role: 2,
          path: "/home/accept",
        },
        {
          id: "3",
          name: "已发布任务",
          role: 1,
          path: "/home/issue",
        },
        {
          id: "4",
          name: "创建新任务",
          role: 1,
          path: "/home/create",
        },
      ],
    };
  },
  mounted() {
    if (this.userrole !== 0) {
      this.sideList = this.sideList.filter((el) => el.id !== "99");
    }
  },
  methods: {
    isRouteActive(path) {
      return this.$route.path.indexOf(path) !== -1;
    },
    onTabRouter(path) {
      if (path === this.$route.path) return;
      this.$router.push(path);
    },
  },
};
</script>

<style lang="less" scoped>
.sidebar {
  position: absolute;

  width: var(--sidebar-width);
  height: 100%;
  background-color: var(--global-color);
}
ul {
  width: 100%;
}
li.item {
  width: 100%;
  height: 5rem;
  text-align: center;
  line-height: 5rem;
  letter-spacing: 1px;
  color: var(--global-textColor);
  font-size: 1.6rem;
  transition: all 0.3s ease-in-out;
  cursor: pointer;
}

li.active {
  color: #333;
  background-color: #fff;
}
</style>
