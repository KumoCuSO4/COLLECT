<!--
 * @Description: 
 * @Author: lz
 * @Date: 2022-04-01 09:01:08
 * @LastEditTime: 2022-04-01 13:45:15
 * @LastEditors: lz
-->
<template>
  <div class="history">
    <a-table
      rowKey="id"
      :columns="columns"
      :data-source="data"
      :pagination="{
        pageSize: 6,
        total: this.total,
        onChange: (pageNumber) => this.getTaskList(pageNumber)
      }"
    >
      <span slot="create_time" slot-scope="time">{{ formatDate(time) }}</span>
      <a
        slot="detail"
        slot-scope="record"
        @click="onCheckTaskDetail(record.task_id)"
        >查看详情</a
      >
    </a-table>
  </div>
</template>

<script>
const columns = [
  {
    title: "任务",
    dataIndex: "task_name",
    ellipsis: true,
  },
  {
    title: "发布人",
    dataIndex: "user_name",
  },
  {
    title: "发布时间",
    dataIndex: "create_time",
    key: "create_time",
    slots: {
      title: "customTitle",
    },
    scopedSlots: {
      customRender: "create_time",
    },
  },
  {
    title: "查看详情",
    scopedSlots: {
      customRender: "detail",
    },
  },
];

export default {
  name: "history",
  data() {
    return {
      columns,
      data: null,
      total: 0,
      curPage: 0
    };
  },
  mounted() {
    //加载任务列表
    this.getTaskList();
  },
  methods: {
    //读取任务
    getTaskList(page = 0) {
      console.log(page)
      let uid1 = window.localStorage.getItem("userId");
      let uid = Number.parseInt(uid1);
      this.$axios({
        method: "get",
        url: `taskOrder/getFinished?uid=${uid}&page=${page}`,
      }).then((response) => {
        let ret = response.data;
        this.total = ret.total
        this.data = ret.list;
      });
    },
    formatDate(date) {
      return date.substring(0, 10);
    },
    onCheckTaskDetail(taskId) {
      this.$router.push(`/home/square/task-detail/${taskId}`);
    },
  },
};
</script>

<style lang="less" scoped>
.history {
  width: 100%;
  height: 100%;
  padding: 1rem;
}
</style>
