<template>
  <div class="container">
    <table>
      <tr>
        <td>缺陷情况说明</td>
        <td>
          <el-input
            type="text"
            placeholder="请输入缺陷情况说明……"
            v-model="report.description"
          />
        </td>
      </tr>
      <tr>
        <td>缺陷复现步骤</td>
        <td>
          <el-input
            type="text"
            placeholder="请输入缺陷复现步骤……"
            v-model="report.step"
          />
        </td>
      </tr>
      <tr>
        <td>测试设备信息</td>
        <td>
          <el-input
            type="text"
            placeholder="请输入测试设备信息……"
            v-model="report.equipment"
          />
        </td>
      </tr>
      <tr>
        <td>缺陷应用截图：</td>
        <td>
          <input id="fileExe" type="file" name="fileExe" />
          <span>只能上传jpg/png文件，且不超过500kb</span>
          <!-- <el-date-picker
            v-model="report.post_time"
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker> -->
        </td>
      </tr>
      <tr>
        <td></td>
        <td>
          <el-button type="primary" @click="onSupReport">提交</el-button>
          <el-button type="primary" @click="onClose">取消</el-button>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    report: {
      type: Object,
      require: true,
    },
  },
  data() {
    return {
      // report: {
      //   report_id: null,
      //   user_id: null,
      //   description: "",
      //   step: "",
      //   equipment: "",
      //   post_time: new Date(),
      // },
    };
  },
  // mounted() {
  //   this.report.user_id = this.reportObj.uid;
  //   this.report.report_id = this.reportObj.report_id;
  // },
  methods: {
    // 提交补充
    onSupReport() {
      let formData = new FormData();
      formData.append('user_id',this.report.user_id)
      formData.append('report_id',this.report.report_id)
      formData.append('description',this.report.description)
      formData.append('step',this.report.step)
      formData.append('equipment',this.report.equipment)
      if(document.getElementById("fileExe").files[0]) {
        formData.append("pics", document.getElementById("fileExe").files[0]);
      }

      this.$axios({
        method: "post",
        url: `/supplement/createSupplement`,
        data: formData,
      }).then((response) => {
        let ret = response.data;
        if (ret.code === 0) {
          this.showSupplementaryReport = false;
          this.$message.success(ret.msg);
          
          this.$emit("refresh");
          this.onClose();

        } else {
          this.$message.error(ret.msg);
        }
      });
    },

    // 关闭卡片
    onClose() {
      this.$emit("onClose");
    },
  },
};
</script>

<style lang="less" scoped>
#fileExe {
  width: 200px;
}
.container {
  width: 60rem;
  height: auto;
  padding: 1rem;
  border-radius: 1rem;
  background-color: #fff;
}

table {
  width: 100%;

  td {
    text-align: center;
    padding: 1rem 0;
    .el-button {
      margin-right: 2rem;
    }
  }
}
</style>
