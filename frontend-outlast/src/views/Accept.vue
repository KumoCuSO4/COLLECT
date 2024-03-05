<template>
  <div class="accept">
    <a-table
      row-key="id"
      :columns="columns"
      :data-source="tableData"
      :pagination="{
        pageSize: 6,
        total: this.total,
        onChange: (pageNumber) => this.getTaskList(pageNumber)
      }"
    >
      <span slot="create_time" slot-scope="time">{{ formatDate(time) }}</span>
      <a slot="downloadExe" slot-scope="text" @click="onDownloadExe(text)"
        >下载</a
      >
      <a slot="downloadDoc" slot-scope="text" @click="onDownloadDoc(text)"
        >下载</a
      >
      <a slot="report" slot-scope="text, record" @click="onWriteReport(record)"
        >填写</a
      >
      <a slot="check" slot-scope="text, record" @click="onCheckReport(record)"
        >查看</a
      >
    </a-table>

    <!-- 填写报告 -->
    <div class="report-card" v-show="isWrite" @click.self="isWrite = false">
      <div class="form">
        <table>
          <tr>
            <td>缺陷情况说明：</td>
            <td>
              <input placeholder="请说明缺陷情况" v-model="form.description" />
            </td>
          </tr>
          <tr>
            <td>缺陷复现步骤：</td>
            <td>
              <input placeholder="请录入缺陷复现步骤" v-model="form.step" />
            </td>
          </tr>
          <tr>
            <td>设备测试信息：</td>
            <td>
              <input
                placeholder="请录入设备测试信息"
                v-model="form.equipment"
              />
            </td>
          </tr>
          <tr>
            <td>缺陷应用截图：</td>
            <td>
              <input id="fileExe" type="file" name="fileExe" />
              <span>只能上传jpg/png文件，且不超过500kb</span>
            </td>
          </tr>
        </table>
        <a-button type="primary" class="upload" @click="upload">上传</a-button>
        <a-button type="primary" class="upload" @click="isWrite = false"
          >取消</a-button
        >
      </div>
    </div>
    <DialogAccept
      :formEdit="form"
      :show.sync="showDialog"
      v-if="showDialog"
    ></DialogAccept>
  </div>
</template>

<script>
import axios from "axios";
import DialogAccept from "./components/accept-dialog.vue";

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
    slots: { title: "customTitle" },
    scopedSlots: { customRender: "create_time" },
  },
  {
    title: "下载可执行文件",
    dataIndex: "task_id",
    key: "downloadExe",
    scopedSlots: { customRender: "downloadExe" },
  },
  {
    title: "下载文档",
    dataIndex: "task_id",
    key: "downloadDoc",
    scopedSlots: { customRender: "downloadDoc" },
  },
  {
    title: "填写报告",
    // dataIndex: "task_id",
    key: "report",
    scopedSlots: { customRender: "report" },
  },
  {
    title: "查看所有报告",
    // dataIndex: "task_id",
    key: "check",
    scopedSlots: { customRender: "check" },
  },
];

// const data = []

// for (let i = 1; i < 5; i++) {
//   data.push({
//     id: i,
//     task_name: `任务${i}`,
//     user_name: '李晓明',
//     create_time: '2022/03/29',
//   })
// }

export default {
  components: {
    DialogAccept,
  },
  data() {
    return {
      tableData: [],
      columns,
      isWrite: false,
      taskName: {},
      fileTmp: null,
      //测试报告表单
      form: {
        id: 0,
        user_id: null,
        user_name: null,
        task_id: null,
        task_name: null,
        description: "无",
        step: "无",
        equipment: "无",
        pics: [],
      },
      showDialog: false,
      total: 0,
      curPage: 1
    };
  },
  mounted() {
    //加载任务列表
    this.getTaskList();
  },
  methods: {
    //读取任务
    getTaskList(page = 0) {
      let uid1 = window.localStorage.getItem("userId");
      let uid = Number.parseInt(uid1);
      this.$axios({
        method: "get",
        url: `taskOrder/getDoing?uid=${uid}&page=${page}`,
      }).then((response) => {
        let ret = response.data;
        this.total = ret.total
        this.tableData = ret.list;
      });
    },
    // 报告：传入当前任务的id填写报告
    onWriteReport(row) {
      this.isWrite = true;
      // console.log(id);
      // console.log(this.taskName[id]);
      const uid = window.localStorage.getItem("userId");
      // const uid=Number.parseInt(uid1);
      // const uname = window.localStorage.getItem('username')
      this.form = {
        user_id: uid,
        task_id: row.task_id,
        task_name: row.task_name,
        description: "",
        step: "",
        equipment: "",
      };
      console.log(this.form);
    },
    //下载可执行文件
    async onDownloadExe(task_id) {
      window.open(
        axios.defaults.baseURL + `/task/${task_id}/downloadExecutable`
      );
    },
    //下载文档
   async onDownloadDoc(task_id) {
      window.open(axios.defaults.baseURL + `/task/${task_id}/downloadDoc`);
    },

    onShowSizeChange(cur, pageSize) {
      console.log(cur, pageSize);
    },
    // 填写上传
    upload() {
      this.form.post_time = this.getDay();
      let formData = new FormData();
      for (const key in this.form) {
        formData.append(key, this.form[key]);
      }
      if(document.getElementById("fileExe").files[0]) {
        formData.append("pics", document.getElementById("fileExe").files[0]);
      }
      this.$axios({
        method: "post",
        url: `/report/submit`,
        data: formData,
      }).then((response) => {
        let ret = response.data;
        if (ret.code === 0) {
          // http://121.5.6.104:8081/report/getReport/8/pic?name=b38898f7-f395-4d60-8e32-54b9f24ef24f.png
          let url = `http://121.5.6.104:8081/report/getReport/${ret.data.id}/pic?name=${ret.data.picsList[0]}`;
          this.form = ret.data;
          this.$message.success(ret.msg);
          this.form.pics = [url];
          this.showDialog = true;
          this.isWrite = false;
          this.getTaskList();
          // this.getReport(ret.data.task_id, ret.data.picsList[0]);
        } else {
          this.$message.error(ret.msg);
        }
      });
    },
    //获取今日日期
    getDay() {
      var $today = new Date();
      return $today;
    },
    formatDate(date) {
      return date.substring(0, 10);
    },

    // 查看所有报告
    onCheckReport(row) {
      this.$router.push(`/home/square/task-detail/${row.task_id}`);
    },
  },
};
</script>

<style lang="less" scoped>
#fileExe {
  width: 200px;
}
.accept {
  height: 100%;
  padding: 0 1rem;
  position: relative;
}

.report-card {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  &::before {
    content: "";
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
    width: 60rem;
    height: 40rem;
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
