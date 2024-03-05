<template>
  <div class="issue">
    <div class="accept">
      <a-table
        row-key="id"
        :columns="columns"
        :data-source="data"
        :pagination="{
          pageSize: 8,
          onShowSizeChange: (current, pageSize) => {
            onShowSizeChange(current, pageSize)
          }
        }"
      >
        <span slot="end_time" slot-scope="text">{{
          new Date(text).toLocaleDateString()
        }}</span>
        <span slot="status" slot-scope="text, record">{{
          getStatus(record)
        }}</span>
        <template slot="operate" slot-scope="record">
          <a @click="onCheckReport(record.id)">查看详情</a>
        </template>
      </a-table>
      <div class="report-card" v-show="isLook" @click.self="isLook = false">
        <div class="form">
          <a-table
            :columns="columns2"
            :data-source="data2"
            :pagination="{
              pageSize: 8,
              onShowSizeChange: (current, pageSize) => {
                onShowSizeChange(current, pageSize)
              }
            }"
          >
            <!-- <span slot="end_time" slot-scope="text">{{
              new Date(text).toLocaleDateString()
            }}</span> -->
            <a slot="report" slot-scope="id" @click="onCheckReportDetail(id)"
              >查看详情</a
            >
          </a-table>
        </div>
      </div>
      <div
        class="report-detail"
        v-show="isLookDetail"
        @click.self="isLookDetail = false"
      >
        <div class="form2">
          <a-table :columns="columns3" :data-source="data3">
            <a
              slot="download"
              slot-scope="text, report"
              @click="onDownload(record)"
              >下载</a
            >
          </a-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const columns = [
  {
    title: '任务名称',
    id: '任务名称',
    dataIndex: 'name'
  },

  {
    title: '截止时间',
    dataIndex: 'end_time',
    scopedSlots: { customRender: 'end_time' }
  },
  {
    title: '任务类型',
    dataIndex: 'type',
    ellipsis: true
  },
  // {
  //   title: "查看填写报告",
  //   dataIndex: "id",
  //   scopedSlots: { customRender: "report" },
  // },
  {
    title: '状态',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '操作',
    scopedSlots: { customRender: 'operate' }
  }
]
const columns2 = [
  {
    id: '',
    dataIndex: 'report'
  },
  {
    title: '报告人',
    dataIndex: 'user_name',
    ellipsis: true
  },
  {
    title: '报告名称',
    dataIndex: 'task_name'
  },

  {
    title: '查看填写报告',
    dataIndex: 'id',
    scopedSlots: { customRender: 'report' }
  }
]
const columns3 = [
  {
    title: '缺陷情况说明',
    dataIndex: 'description'
  },
  {
    title: '缺陷复现步骤',
    dataIndex: 'step'
  },

  {
    title: '设备测试信息',
    dataIndex: 'equipment'
  },
  {
    title: '缺陷应用截图',
    dataIndex: 'report',
    scopedSlots: { customRender: 'pics' }
  }
]
// const data3 = [
//   {
//     key: "1",
//     report_id: "1",
//     decription: "1",
//     step: "1",
//     equipment: 3,
//   },
// ];

// for (let i = 2; i <= 30; i++) {
//   data.push({
//     key: `${i}`,
//     id: i,
//     task: '解决某……',
//     end_time: '2022-02-26',
//     number: 3,
//     status:'进行中',
//     type:'功能测试',
//     name:'修复损耗',
//   })
// }

export default {
  data() {
    return {
      columns,
      data: [],
      columns2,
      data2: [],
      columns3,
      data3: [],
      isLook: false,
      isLookDetail: false,
      report_id: {}
    }
  },
  mounted() {
    this.getCreated()
    this.getReporList()
  },
  methods: {
    getCreated(page = 0) {
      let uid1 = window.localStorage.getItem('userId')
      let uid = Number.parseInt(uid1)
      this.$axios({
        method: 'get',
        url: `task/getCreated?uid=${uid}&page=${page}`
      }).then((response) => {
        console.log(response) //请求成功返回的数据
        let ret = response.data
        this.data = ret.list
      })
    },
    getStatus(task) {
      const currentDate = new Date().getTime()
      const taskDate = new Date(task.end_time).getTime()
      return taskDate > currentDate ? '进行中' : '已截止'
    },
    getReporList(page = 0) {
      // let taskid1 = window.localStorage.getItem("task_id");
      // let task_id=Number.parseInt(taskid1)
      // this.$axios({
      // 	method: 'get',
      // 	url: `/report/getReport/${task_id}?page=${page}`,
      // }).then((response) => {
      // 	console.log(response) //请求成功返回的数据
      // 	let ret = response.data;
      // 	this.data2 = ret.list;
      // 	//映射ID任务名关系
      // 	// ret.list.forEach(item => {
      // 	// 	this.report_id[item.id] = item.id;
      // 	// });
      // });
    },
    onCheckReport(taskId) {
      this.$router.push(`/home/square/task-detail/${taskId}`)
      // this.isLook = true;
      // this.$axios({
      //   method: "get",
      //   url: `/report/getList?taskId=${id}`,
      // }).then((response) => {
      //   console.log(response); //请求成功返回的数据
      //   let ret = response.data;
      //   this.data2 = ret.list;
      //   //映射ID任务名关系
      //   // ret.list.forEach(item => {
      //   // 	this.report_id[item.id] = item.id;
      //   // });
      // });
    },
    onCheckReportDetail(id) {
      this.isLookDetail = true
      console.log(id)
      this.$axios({
        method: 'get',
        url: `report/getReport?reportId=${id}`
      }).then((response) => {
        console.log(response) //请求成功返回的数据
        let ret = response.data
        this.data3 = [ret]
        console.log(response.data.user_id)
        //映射ID任务名关系
        // ret.list.forEach(item => {
        // 	this.report_id[item.id] = item.id;
        // });
      })
    },
    onDownload() {}
  }
}
</script>

<style lang="less" scoped>
.report-card {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  &::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
  }

  .form {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 10;
    width: 100rem;
    height: 60rem;
    padding: 2rem;
    background-color: #fff;
    border-radius: 1rem;
    table {
      width: 100%;
      input {
        width: 100%;
        padding: 0.5rem 1rem;
        border: none;
        border-bottom: 1px solid #ddd;
        &::placeholder {
          color: #999;
        }
      }
      tr {
        td {
          padding: 2rem 0;
          color: #333;
          font-weight: 600;
          &:first-child {
            width: 10rem;
          }
        }
      }
    }
    .ant-btn.upload {
      bottom: 2rem;
      padding: 0 3.5rem;
      margin: 5rem 3rem 0 0;
    }
  }
}
.report-detail {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  &::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
  }

  .form2 {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 10;
    width: 100rem;
    height: 60rem;
    padding: 2rem;
    background-color: #fff;
    border-radius: 1rem;
    table {
      width: 100%;
      input {
        width: 100%;
        padding: 0.5rem 1rem;
        border: none;
        border-bottom: 1px solid #ddd;
        &::placeholder {
          color: #999;
        }
      }
      tr {
        td {
          padding: 2rem 0;
          color: #333;
          font-weight: 600;
          &:first-child {
            width: 10rem;
          }
        }
      }
    }
    .ant-btn.upload {
      bottom: 2rem;
      padding: 0 3.5rem;
      margin: 5rem 3rem 0 0;
    }
  }
}
</style>
