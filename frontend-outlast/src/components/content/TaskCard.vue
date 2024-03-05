<template>
  <div class="task-card">
    <div class="task-wrapper" @click="$emit('showDatail', task)">
      <h3>{{ task.name }}</h3>
      <div class="task-content">
        <p>截止日期：{{ new Date(task.end_time).toLocaleDateString() }}</p>
        <p>任务描述：{{ task.description }}</p>
      </div>
      <div class="task-link">
        <span>任务详情</span>
        <!-- <a href="javascript:;" @click.stop="onCheckTaskDetail(task)">任务详情</a> -->
        <!-- <a>
        {{ "删除任务" }}
        </a> -->
        <a @click="onAcceptTask()" v-if="userrole === '2'">接受任务</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'taskcard',
  props: {
    task: Object
  },
  data() {
    return {
      userrole: localStorage.getItem('$userrole')
    }
  },
  methods: {
    // 查看任务详情
    onCheckTaskDetail(task) {
      return false
      this.$emit('onCheck', task)
    },

    //接受任务
    onAcceptTask() {
      let self = this
      let uid = window.localStorage.getItem('userId')
      let uname = window.localStorage.getItem('username')
      this.$confirm({
        title: `确认接受该任务吗？`,
        content: '',
        onOk: async () => {
          const res = await this.$axios({
            method: 'post',
            url: `/taskOrder/acceptTask`,
            data: {
              id: 0,
              user_id: uid,
              user_name: uname,
              task_id: this.task.id,
              task_name: this.task.name,
              status: 1,
              create_time: this.getDay()
            }
          })
          let ret = res.data
          if (res.data.code === 0) {
            self.$message.success(ret.msg)
          } else {
            self.$message.warning('接受任务失败,请勿重复操作')
          }
          self.$emit('refresh')
        },
        onCancel() {}
      })
      //   if (confirm("确认接受该任务吗？")) {
      //     this.$axios({
      //       method: "post",
      //       url: `/taskOrder/acceptTask`,
      //       data: {
      //         id: 0,
      //         user_id: uid,
      //         user_name: uname,
      //         task_id: this.task.id,
      //         task_name: this.task.name,
      //         status: 1,
      //         create_time: this.getDay(),
      //       },
      //     }).then((response) => {
      //       console.log(response); //请求成功返回的数据
      //       let ret = response.data;
      //       alert(ret.msg);
      //       this.$emit("refresh");
      //     });
      //     // alert('你已成功接受该任务')
      //     return;
      //   }
    },
    formatDate(date) {
      return date.substring(0, 10)
    },
    //获取今日日期
    getDay() {
      var $today = new Date()
      var y = $today.getFullYear() //获取年份
      var m =
        $today.getMonth() + 1 < 10
          ? '0' + ($today.getMonth() + 1)
          : $today.getMonth() + 1 //获取月份
      var d = $today.getDate() < 10 ? '0' + $today.getDate() : $today.getDate() //获取天
      $today = y + '-' + m + '-' + d //获取当前时间
      return $today
    }
  }
}
</script>

<style lang="less" scoped>
.task-card {
  width: 30rem;
}

.task-wrapper {
  width: 70%;
  height: 15rem;
  padding: 1rem;
  margin: 2rem 0 3rem 5rem;
  border-radius: 0.5rem;
  background-color: var(--card-color);
  box-shadow: 2px 2px 0.5rem rgba(0, 0, 0, 0.3);

  cursor: pointer;
}

.task-link {
  width: 100%;
  display: flex;
  justify-content: space-around;
}

h3,
p {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
