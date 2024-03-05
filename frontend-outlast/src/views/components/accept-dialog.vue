<!--
 * @Description: 查看填写报告
 * @Author: lz
 * @Date: 2022-03-31 21:37:16
 * @LastEditTime: 2022-04-02 20:30:35
 * @LastEditors: lz
-->
<template>
  <div class="dialog-wrapper">
    <a-modal
      v-model="show"
      ref="dialog"
      width="880px"
      title="提交成功"
      on-ok="handleOk"
      @cancel="onCancel"
      centered
      :maskClosable="false"
      :getPopupContainer="(triggerNode) => triggerNode.parentNode"
    >
      <div class="form">
        <div class="left">
          <h3>原报告</h3>
          <a-form-model :model="formEdit" :labelCol="{ style: 'width: 100px' }">
            <a-form-model-item label="缺陷情况说明:">
              <textarea readonly class="text_box">{{formEdit.description}}</textarea>
            </a-form-model-item>
            <a-form-model-item label="缺陷复现步骤:">
              <textarea readonly class="text_box">{{formEdit.step}}</textarea>
            </a-form-model-item>
            <a-form-model-item label="缺陷测试消息:">
              <textarea readonly class="text_box">{{formEdit.equipment}}</textarea>
            </a-form-model-item>
            <a-form-model-item label="缺陷应用截图:">
              <div
                v-if="formEdit.pics && formEdit.pics.length"
                class="img-wrapper"
              >
                <el-image
                  v-if="formEdit.pics[0]!=null"
                  style="width: 100px; height: 100px"
                  :src="formEdit.pics[0]"
                  :preview-src-list="formEdit.pics"
                >
                </el-image>
                <div v-else>无截图</div>
                <!-- <i class="el-icon-delete" @click="delImage()"></i> -->
              </div>
            </a-form-model-item>
          </a-form-model>
        </div>
        <div class="right">
          <h3>相似报告</h3>
          <a-form-model v-if="hasSimilar" :model="form" :labelCol="{ style: 'width: 100px' }">
            <a-form-model-item label="缺陷情况说明:">
              <textarea readonly class="text_box">{{ form.description }}</textarea>
            </a-form-model-item>
            <a-form-model-item label="缺陷复现步骤:">
              <!-- <a-input v-model="form.step" /> -->
              <textarea readonly class="text_box">{{ form.step }}</textarea>
            </a-form-model-item>
            <a-form-model-item label="缺陷测试消息:">
              <!-- <a-input v-model="form.equipment" /> -->
              <textarea readonly class="text_box">{{ form.equipment }}</textarea>
            </a-form-model-item>
            <a-form-model-item label="缺陷应用截图:">
              <!-- <a-input v-model="form.equipment" /> -->
              <!-- <span>{{ form.equipment }}</span> -->
              <el-image
                v-if="form.pics && form.pics.length>0"
                style="width: 100px; height: 100px"
                :src="form.pics[0]"
                :preview-src-list="form.pics"
              >
              </el-image>
              <div v-else>无截图</div>
            </a-form-model-item>
          </a-form-model>
          <div v-else>
            没有相似报告
          </div>
        </div>
      </div>
      <template slot="footer">
        <a-button
          v-if="this.hasSimilar"
          key="submit"
          type="primary"
          @click="showSupplementaryReport = true"
        >
          填写补充报告
        </a-button>
        <a-button key="back" @click="onCancel"> 返回 </a-button>
      </template>
      <!-- 补充报告 -->
      <a-modal
        v-model="showSupplementaryReport"
        ref="dialog"
        width="480px"
        title="补充报告"
        @cancel="showSupplementaryReport = false"
        centered
        on-ok="handleOk"
        :maskClosable="false"
        :getPopupContainer="(triggerNode) => triggerNode.parentNode"
      >
        <div class="form">
          <a-form-model
            :model="supplementaryReport"
            :labelCol="{ style: 'width: 100px' }"
          >
            <a-form-model-item label="缺陷情况说明:">
              <a-input v-model="supplementaryReport.description" />
            </a-form-model-item>
            <a-form-model-item label="缺陷复现步骤:">
              <a-input v-model="supplementaryReport.step" />
            </a-form-model-item>
            <a-form-model-item label="测试设备信息:">
              <a-input v-model="supplementaryReport.equipment" />
            </a-form-model-item>
            <a-form-model-item label="报告发布时间:">
              <!-- <el-date-picker
                v-model="supplementaryReport.post_time"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker> -->
              <input id="fileExe" type="file" name="fileExe" />
          <span>只能上传jpg/png文件，且不超过500kb</span>
            </a-form-model-item>
          </a-form-model>
        </div>
        <template slot="footer">
          <a-button key="submit" type="primary" @click="handleOk">
            提交补充报告
          </a-button>
          <a-button key="back" @click="showSupplementaryReport = false">
            返回
          </a-button>
        </template>
      </a-modal>
    </a-modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      hasSimilar: false,
      form: {
        id: 0,
        user_id: null,
        user_name: null,
        task_id: null,
        task_name: null,
        description: "",
        step: "",
        equipment: "",
        pics: [],
      },
      supplementaryReport: {
        description: "",
        step: "",
        equipment: "",
        post_time: "",
      },
      isDisabled: true,
      showSupplementaryReport: false,
    };
  },
  props: {
    show: {
      type: Boolean,
      default: () => false,
    },
    formEdit: {
      type: Object,
      default: () => {},
    },
  },
  created() {
    this.getSimilar();
  },
  methods: {
    handleOk() {
       let formData = new FormData();
      formData.append('user_id',this.formEdit.user_id)
      formData.append('report_id',this.formEdit.id)
      formData.append('description',this.supplementaryReport.description)
      formData.append('step',this.supplementaryReport.step)
      formData.append('equipment',this.supplementaryReport.equipment)
      if(document.getElementById("fileExe").files[0]) {
        formData.append("pics", document.getElementById("fileExe").files[0]);
      }

      this.$axios({
        method: "post",
        url: `/supplement/createSupplement`,
        data: formData
      }).then((response) => {
        let ret = response.data;
        if (ret.code === 0) {
          this.showSupplementaryReport = false;
          this.$message.success(ret.msg);
          this.$emit("refresh");
          this.onCancel();
        } else {
          this.$message.error(ret.msg);
        }
      });
    },
    onCancel() {
      this.$emit("update:show", false);
    },
    getSimilar() {
      this.$axios({
        method: "get",
        url: `/report/getSimilar?reportId=${this.formEdit.id}`,
        data: {},
      }).then((response) => {
        let ret = response.data;
        if (ret) {
          this.hasSimilar=true;
          let url = `http://121.5.6.104:8081/report/getReport/${ret.id}/pic?name=${ret.picsList[0]}`;
          this.form = ret;
          this.form.pics = [url];
        }
      });
    },
  },
};
</script>
<style lang="less" scoped>
.img-wrapper {
  position: relative;
  .el-icon-delete {
    position: absolute;
    font-size: 20px;
    cursor: pointer;
    right: -30px;
  }
}

.form {
  width: 100%;
  display: flex;
  justify-content: space-between;
  h3 {
    font-size: 18px;
    font-weight: bold;
  }
  .left {
    flex: 1;
  }
  .right {
    width: 400px;
  }
  .ant-form-item {
    display: flex;
    align-items: center;
  }
  .ant-modal-footer {
    display: flex;
    justify-content: space-between;
  }
  .ant-input {
    min-width: 220px;
  }
}
#fileExe {
  width: 200px;
}
.text_box {
  width: 300px;
  white-space: pre-wrap;
  line-height: normal;
}
</style>
