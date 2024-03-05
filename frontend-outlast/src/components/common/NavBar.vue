<template>
  <div class="header">
    <!-- @click="$router.replace('/home/square')" -->
    <h1>COLLECT</h1>
    <div class="user">
      <h3>{{ userInfo.uname }}</h3>
      <h3>{{ userlist[userrole] }}: </h3>
      <ul>
        <li @click="userInformation">个人信息</li>
        <li @click="onLogout">退出登录</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "navbar",
  props: {
    userrole: {
      type: Number,
      default: localStorage.getItem("$userrole"),
    },
  },
  data() {
    return {
      userlist: {
        0: "管理员",
        1: "发包人",
        2: "工人",
      },
      userInfo:JSON.parse(localStorage.getItem('TaskUserInfo'))
    };
  },
  methods: {
    onLogout() {
      let self = this;
      this.$confirm({
        title: `确认退出登录吗?`,
        content: "",
        onOk: async (e) => {
          const res = await this.$axios({
            url: "/user/logout",
            method: "post",
            data: {},
          });
          if (res.data.code === 0) {
            self.$message.success("退出成功");

            // localStorage.removeItem("$userrole");
            // let guidanceShown = localStorage.guidanceShown;
            localStorage.clear()
            // localStorage.guidanceShown = guidanceShown;

            self.$router.replace("/login");
          }
        },
        onCancel() {},
      });
    },
    userInformation() {
      this.$router.replace("/home/person");
    },
  },
};
</script>

<style scoped lang="less">
.header {
  width: 100%;
  height: var(--navbar-height);
  line-height: var(--navbar-height);

  background-color: var(--global-color);
  border-bottom: 1px solid #ddd;
}

h1 {
  float: left;
  margin-left: 2rem;
  margin-bottom: 0;
  color: var(--global-textColor);
  cursor: pointer;
}
h3 {
  color: var(--global-textColor);
  cursor: pointer;
  float: right;
  &+h3{
    margin-right: 10px;
  }
}
.user {
  float: right;
  margin-right: 5rem;
  position: relative;
  &:hover {
    ul {
      display: block;
    }
  }
  ul {
    display: none;
    position: absolute;
    z-index: 10;
    left: 50%;
    transform: translateX(-50%);
    top: 5rem;
    transform: translateX(-50%);
    background-color: #fff;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    overflow: hidden;
    li {
      height: 3.5rem;
      text-align: center;
      line-height: 3rem;
      padding: 0 1.5rem;
      transition: all 0.3s ease-in-out;
      background-color: #f6f7fb;
      cursor: pointer;
      &:hover {
        background-color: #fff;
      }
    }
  }
}
</style>
