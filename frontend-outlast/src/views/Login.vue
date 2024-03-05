<template>
  <div class="login">
    <div class="container">
      <div class="tab">
        <h3
          v-for="(item, index) in tab"
          :key="index"
          :class="currentIndex === index ? 'active' : ''"
          @click="currentIndex = index"
        >
          {{ item }}
        </h3>
      </div>

      <!--  -->
      <div class="context">
        <!-- 登录 -->
        <div class="tab-1" :class="currentIndex === 0 ? 'show' : ''">
          <table>
            <tr>
              <td>账号：</td>
              <td>
                <a-input placeholder="请输入用户名" v-model="user.username" />
              </td>
            </tr>
            <tr>
              <td>密码：</td>
              <td>
                <a-input
                  type="password"
                  placeholder="请输入密码"
                  v-model="user.password"
                />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <a-button type="primary" @click="sendLogin">登录</a-button>
              </td>
            </tr>
          </table>
        </div>

        <!-- 注册 -->
        <div class="tab-2" :class="currentIndex === 1 ? 'show' : ''">
          <table>
            <tr>
              <td>账号：</td>
              <td>
                <a-input placeholder="账号" v-model="user.username" />
              </td>
            </tr>
            <tr>
              <td>电话：</td>
              <td>
                <a-input placeholder="电话" v-model="user.phone" />
              </td>
            </tr>
            <tr>
              <td>邮箱</td>
              <td>
                <a-input placeholder="邮箱" v-model="user.email" />
              </td>
            </tr>
            <tr>
              <td>密码：</td>
              <td>
                <a-input-password placeholder="密码" v-model="user.password" />
              </td>
            </tr>
            <tr>
              <td>确认密码</td>
              <td>
                <a-input-password
                  placeholder="确认密码"
                  v-model="user.password_ensure"
                />
              </td>
            </tr>
            <tr>
              <td>角色：</td>
              <td>
                <a-cascader
                  :options="options"
                  placeholder="Please select"
                  v-model="user.userrole"
                />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <a-button type="primary" @click="sendRegister()">注册</a-button>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      user: {
        username: '',
        password: '',
        userrole: ['0'],
        password_ensure: '',
        phone: '',
        email: ''
      },

      tab: ['登录', '注册'],
      currentIndex: 0,

      options: [
        {
          value: '2',
          label: '工人'
        },
        {
          value: '1',
          label: '发包人'
        }
        // {
        //   value: "0",
        //   label: "管理员",
        // },
      ]
    }
  },
  created() {
    if (!!localStorage.getItem('$userrole')) {
      this.$router.replace('/home')
      return
    }
  },
  methods: {
    //注册
    async sendRegister() {
      // 判断用户是否完成信息录入
      if (Object.keys(this.user).length != 6) {
        this.$message.warning('用户信息不能为空')
        return
      }
      // 判断用户连续输入密码是否相同
      const { username, password, password_ensure, userrole, phone, email } =
        this.user
      if (password !== password_ensure) {
        this.$message.warning('两次密码输入不一致，请重新输入')
        return
      }
      try {
        const res = await this.$axios({
          url: '/user/register',
          method: 'post',
          data: {
            uid: 0,
            uname: username,
            password,
            user_role: userrole[0],
            phone,
            email
          },
        })
        if (res.data.code === 0) {
          const { uid, uname, user_role } = res.data.data
          window.localStorage.setItem(
            'TaskUserInfo',
            JSON.stringify(res.data.data)
          )
          localStorage.setItem('userId', uid)
          localStorage.setItem('username', uname)
          localStorage.setItem('$userrole', user_role)
          this.$message.success('注册成功')
          setTimeout(() => {
            this.$router.replace('/home')
          }, 1500)
          return
        }
      } catch (error) {
        console.log(error)
        this.$message.error('注册失败，请检查你的网络连接')
      }
    },

    //登录
    sendLogin() {
      console.log(this.$axios)
      if (!Object.keys(this.user).length) {
        alert('请输入用户名或密码')
        return
      }
      this.$axios({
        method: 'post',
        url: `/user/login`,
        data: {
          // uid: 0,
          uname: this.user.username,
          password: this.user.password
          // user_role: this.user.userrole[0],
        }
      }).then((response) => {
        let ret = response.data
        if (ret.code === 0) {
          //登录成功
          window.localStorage.setItem('TaskUserInfo', JSON.stringify(ret.data))
          window.localStorage.setItem('userId', ret.data.uid)
          window.localStorage.setItem('username', ret.data.uname)
          window.localStorage.setItem('$userrole', ret.data.user_role)
          this.$message.success('登录成功')
          setTimeout(() => {
            this.$router.replace('/home')
          }, 1500)
          return
        } else {
          //登录失败
          alert(ret.msg)
        }
      })

      // res.data.data.forEach((user) => {
      // 	const {
      // 		username,
      // 		password,
      // 		userrole
      // 	} = user
      // 	if (
      // 		JSON.stringify({
      // 			username,
      // 			password
      // 		}) === JSON.stringify(this.user)
      // 	) {
      // 		localStorage.setItem('$userrole', userrole)
      // 		this.$router.replace('/home')
      // 		return
      // 	}
      // });
    }
  }
}
</script>

<style lang="less" scoped>
.login {
  height: 100vh;

  background: url(../assets/images/flowers.jpg) no-repeat;
  background-size: cover;

  position: relative;
}

.container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  width: 35rem;
  height: 50rem;
  padding: 1rem;
  background-color: #fff;
  border-radius: 1rem;

  .tab {
    height: 5rem;
    line-height: 5rem;
    width: 100%;
    display: flex;

    h3 {
      padding: 0 3rem 1rem;
      height: 100%;
      cursor: pointer;
      letter-spacing: 2px;
    }

    .active {
      border-bottom: 3px solid var(--global-color);
    }
  }

  .context {
    width: 100%;
    height: calc(100% - 5rem);
    overflow: hidden;
    position: relative;

    .tab-1 {
      width: 100%;
      height: 100%;
      opacity: 0;
      position: absolute;
      background-color: #fff;

      table {
        margin-top: 5rem;
        width: 100%;

        td {
          height: 5rem;
        }
      }
    }

    .tab-2 {
      width: 100%;
      height: 100%;
      position: absolute;
      opacity: 0;
      background-color: #fff;

      table {
        margin-top: 5rem;
        width: 100%;

        td {
          height: 5rem;
        }
      }
    }

    .show {
      opacity: 1;
      z-index: 5;
    }
  }
}
</style>
