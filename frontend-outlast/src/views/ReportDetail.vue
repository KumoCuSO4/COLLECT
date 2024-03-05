<template>
  <div class="report-detail">
    <!-- 信息 -->
    <div class="wrapper">
      <div class="hd">
        <h2>报告详情</h2>
        <h2>
          总评分: <span> {{ report.scoreTotal }}</span>
        </h2>
      </div>
      <div class="bd">
        <p>
          提交工人: <span>{{ report.userName }}</span>
        </p>
        <p>
          提交时间: <span>{{ report.submit_time }}</span>
        </p>
      </div>
      <a href="javascript:;" @click="onCheckOrigin()">查看原报告</a>
      <span/>
      <a href="javascript:;" @click="onCheckSimilar()">查看相似报告</a>
    </div>

    <!-- 报告 -->
    <div class="report">
      <div class="td">
        <div class="sup">
          <h2>补充报告</h2>
          <el-button type="primary" plain @click="supplement()">补充</el-button>
        </div>
        <div class="com">
          <h2>评论区</h2>
        </div>
      </div>
      <div class="tb">
        <!-- 报告模板 -->
        <div class="sup-tb">
          <table v-if="report_sup && report_sup.length">
            <tr v-for="(item, index) in report_sup" :key="item.id">
              <td>{{ index + 1 }}</td>
              <td>{{ item.user_name }}</td>
              <td>{{ new Date(item.post_time).toLocaleDateString() }}</td>
              <td>
                <a href="javascript:;" @click="getSupplement(item)">查看报告</a>
              </td>
            </tr>
          </table>
          <a-empty v-else />
        </div>

        <!-- 评论模板 -->
        <div class="com-tb">
          <ul>
            <template v-if="comment.length">
              <li v-for="item in comment" :key="item.id">
                <div class="hd">
                  <span>用户名称: {{ item.user_name }}</span>
                  <!-- <span>评分: {{ item.score }}</span> -->
                  <span>
                    评分:
                    <el-rate
                      v-model="item.score"
                      disabled
                      show-score
                      text-color="#ff9900"
                      score-template="{value}"
                    >
                    </el-rate>
                  </span>
                </div>
                <p>评价: {{ item.description || '无' }}</p>
              </li>
            </template>
            <div v-else class="com-ct">
              <a-empty />
            </div>
          </ul>

          <div class="com-field">
            <div class="com-field-score">
              <textarea
                v-model="description"
                placeholder="在此处输入你的评论……"
              />
              <span style="margin-right: 20px">评分: </span>
              <el-rate v-model="score" :colors="colors"> </el-rate>
              <!-- <el-select v-model="score" placeholder="评分" size="medium">
                <el-option
                  v-for="item in scoreOpt"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select> -->
            </div>
            <el-button type="primary" plain @click="onSubmitComment"
              >提交</el-button
            >
          </div>
        </div>
      </div>
    </div>

    <!-- 提交、查看 -->
    <div class="report-card" v-show="isActive">
      <report-card
        @onClose="onClose"
        @refresh="getSupplementList()"
        :report="report"
      />
    </div>
    <!-- 查看原报告 -->
    <a-modal
      v-model="showOriginalReport"
      ref="dialog"
      width="480px"
      :title="originalReportTitle"
      @cancel="showOriginalReport = false"
      centered
      :footer="null"
      :maskClosable="false"
      :getPopupContainer="(triggerNode) => triggerNode.parentNode"
    >
      <div class="form">
        <a-form-model
          :model="originalReport"
          :labelCol="{ style: 'width: 100px' }"
        >
          <a-form-model-item label="缺陷情况说明:">
            <a-input v-model="originalReport.description" disabled />
          </a-form-model-item>
          <a-form-model-item label="缺陷复现步骤:">
            <a-input v-model="originalReport.step" disabled />
          </a-form-model-item>
          <a-form-model-item label="缺陷测试消息:">
            <a-input v-model="originalReport.equipment" disabled />
          </a-form-model-item>
          <a-form-model-item label="缺陷应用截图:">
            <div
              class="img-wrapper"
              v-if="originalReport.pics && originalReport.pics.length"
            >
              <el-image
                style="width: 100px; height: 100px"
                :src="originalReport.pics[0]"
                :preview-src-list="originalReport.pics"
              >
              </el-image>
            </div>
            <a-empty v-else :description="false" />
          </a-form-model-item>
        </a-form-model>
      </div>
    </a-modal>
  </div>
</template>

<script>
// 测试数据
// const report = {
//   name: "工人1",
//   score: 5,
//   submit_time: "2022-03-20",
// };

// const report_sup = [];
// for (let i = 1; i < 20; i++) {
//   report_sup.push({
//     id: i,
//     name: `工人${i}`,
//     submit_time: "2022-03-20",
//     check: "查看报告",
//   });
// }

// const comment = [];
// for (let i = 1; i < 10; i++) {
//   comment.push({
//     id: i,
//     user_id: i,
//     report_id: i,
//     name: `工人${i}`,
//     score: 5,
//     description:
//       "这是评价内容这是评价内容这是评价内容这是评价内容这是评价内容这是评价内容",
//     post_time: "20222-03-20",
//   });
// }

const scoreOpt = []
for (let i = 1; i <= 5; i++) {
  scoreOpt.push({ value: i, label: i })
}

// 组件
import ReportCard from '../components/content/ReportCard.vue'
import axios from 'axios'
export default {
  data() {
    return {
      report: {
        scoreTotal: 0,
        description: '',
        report_id: null,
        user_id: null,
        step: '',
        equipment: '',
        // post_time: new Date(),
        pics: []
      },
      report_sup: [],
      comment: [],
      isActive: false,
      scoreOpt,
      score: 0,
      description: '',
      showOriginalReport: false,
      originalReport: {
        pics: []
      },
      cloneOriginalReport: {
        pics: []
      },
      similarReport: {
        scoreTotal: 0,
        description: '',
        report_id: null,
        user_id: null,
        step: '',
        equipment: '',
        pics: []
      },
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      originalReportTitle: '查看原报告'
    }
  },
  components: {
    ReportCard
  },
  watch: {
    comment: {
      handler: function (val, oldVal) {
        if (val && val.length != oldVal.length) {
          const totals = val.filter((item) => item.score)
          const calculateAverage = (arr = []) => {
            const reducer = (acc, { score }, index, array) => {
              let val = acc + score
              if (index === array.length - 1) {
                return val / array.length
              }
              return val
            }
            const res = arr.reduce(reducer, 0)
            return res.toFixed(1)
          }
          this.report.scoreTotal = calculateAverage(totals)
          // totals.reduce(
          //   (total, { score }) => (total = total + score),
          //   0
          // );
        }
      },
      deep: true
    }
  },
  created() {
    if (this.$route.params.id) {
      this.getReportDetail()
      this.getSupplementList()
      this.getComments()
    }
  },
  methods: {
    // 查看原报告
    onCheckOrigin() {
      this.originalReportTitle = '查看原报告'
      this.originalReport = this.cloneOriginalReport
      this.showOriginalReport = true
    },
    onCheckSimilar() {
      if(this.similarReport.report_id==null) {
        this.$message.error('没有相似报告')
        return
      }
      this.originalReportTitle = '查看相似报告'
      this.originalReport = this.similarReport
      this.showOriginalReport = true
    },
    supplement() {
      let $userrole = window.localStorage.getItem('$userrole')
      if ($userrole - 0 !== 2) {
        return this.$message.warn("非 '工人' 不建议补充报告!")
      }
      this.isActive = true
    },
    // 提交评论
    async onSubmitComment() {
      if (this.score === 0) {
        return this.$message.warn('请选择评分')
      }
      const res = await this.$axios({
        method: 'post',
        url: `/comment/createComment`,
        data: {
          user_id: this.report.user_id,
          report_id: this.report.report_id,
          score: this.score,
          description: this.description,
          post_time: new Date()
        }
      })
      if (res.data.code === 0) {
        this.$message.success(res.data.msg)
        this.score = 0
        this.description = ''
        this.getComments()
      } else {
        this.$message.error('评论已发布，不能发布了哦')
      }
    },

    // 关闭卡片
    onClose() {
      this.isActive = false
    },
    async getSupplement(item) {
      const res = await this.$axios({
        method: 'get',
        url: `/supplement/getSupplement/${item.id}`
      })
      this.showOriginalReport = true
      this.originalReportTitle = '查看补充报告'
      this.originalReport = res.data
      let url =
        axios.defaults.baseURL +
        `/supplement/getSupplement/${item.id}/pic?name=${res.data.picsList[0]}`
      this.originalReport.pics = [url]
      // this.originalReport.post_time=new Date(res.data.post_time).toLocaleDateString()
      // this.report.scoreTotal = this.$route.params.id - 0;
      // this.comment = res.data.list;
    },
    async getComments(currPage = 0) {
      const res = await this.$axios({
        method: 'get',
        url: `/comment/getComments?report_id=${this.$route.params.id}&currPage=${currPage}`
      })
      // this.report.scoreTotal = this.$route.params.id - 0;
      this.comment = res.data.list
    },
    async getReportDetail() {
      let uid = window.localStorage.getItem('userId')
      const res = await this.$axios({
        method: 'get',
        url: `/report/getReport/${this.$route.params.id}`
      })
      this.report.report_id = this.$route.params.id - 0
      this.report.userName = res.data.user_name //没有工人名称 暂替
      this.report.user_id = uid - 0
      this.report.submit_time = new Date(
        res.data.post_time
      ).toLocaleDateString()
      this.originalReport = res.data
      this.cloneOriginalReport = res.data
      let url = `http://121.5.6.104:8081/report/getReport/${res.data.id}/pic?name=${res.data.picsList[0]}`
      this.originalReport.pics = [url]
    },
    async getSimilar() {
      let uid = window.localStorage.getItem('userId')
      const res = await this.$axios({
        method: 'get',
        url: `/report/getSimilar/?reportId=${this.$route.params.id}`
      })
      this.similarReport.report_id = res.data.report_id
      this.similarReport.userName = res.data.user_name //没有工人名称 暂替
      this.similarReport.user_id = uid - 0
      this.similarReport.submit_time = new Date(
          res.data.post_time
      ).toLocaleDateString()
      let url = `http://121.5.6.104:8081/report/getReport/${res.data.id}/pic?name=${res.data.picsList[0]}`
      this.originalReport.pics = [url]
    },
    async getSupplementList(currPage = 0) {
      const res = await this.$axios({
        method: 'get',
        url: `/supplement/getList?report_id=${this.$route.params.id}&currPage=${currPage}`
      })
      this.report_sup = res.data.list
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item {
  display: flex;
}
.report-detail {
  width: 100%;
  height: 100%;
  padding: 1rem 2rem;
  position: relative;
}

.wrapper {
  width: inherit;
  font-size: 14px;
  .hd,
  .bd {
    display: flex;
    justify-content: space-between;
    width: 50%;
  }
  span {
    padding: 0 3rem;
  }
  a {
    font-size: 16px;
    text-decoration: underline;
  }
}

.report {
  width: 100%;
  margin-top: 5rem;
  height: calc(100% - 15rem);

  .td {
    width: inherit;
    height: 5rem;
    display: flex;
    align-items: center;
    .sup,
    .com {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 2rem;
      .el-button {
        padding: 1rem 3rem;
      }
    }
    .sup {
      width: 60rem;
    }
    .com {
      flex: 1;
    }
  }

  .tb {
    display: flex;
    height: calc(100% - 5rem);
  }

  .sup-tb {
    width: 60rem;
    height: 100%;
    overflow-x: hidden;
    overflow-y: scroll;
    border: 1px solid #e5e6eb;
    &::-webkit-scrollbar {
      width: 5px;
    }
    &::-webkit-scrollbar-thumb {
      background-color: #ddd;
    }
    table {
      width: inherit;
      // height: 100%;
      tr {
        width: inherit;
        border-top: 1px solid #e5e6eb;
        border-bottom: 1px solid #e5e6eb;
        transition: all 0.3s ease-in-out;
        height: 40px;
        cursor: auto;
        &:hover {
          background-color: #e5e6eb;
        }
        &:first-child {
          border-top: none;
        }
        td {
          padding: 2rem 0;
          text-align: center;
        }
      }
    }
  }

  .com-tb {
    flex: 1;
    .com-ct {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
    }
    ul {
      width: inherit;
      height: calc(100% - 15rem);
      overflow-y: scroll;
      &::-webkit-scrollbar {
        width: 5px;
      }
      &::-webkit-scrollbar-thumb {
        background-color: #ddd;
      }
      li {
        margin: 2rem 1rem 0;
        border-bottom: 2px solid #f6f7fb;
        padding: 0;
        .hd {
          display: flex;
          align-items: center;
          justify-content: space-between;
          line-height: 2s;
          margin-bottom: 5px;
        }
        p {
          white-space: pre-wrap;
        }
      }
    }
    .com-field {
      width: 100%;
      height: 15rem;
      padding: 1rem;
      border-top: 2px solid #f6f7fb;
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      .com-field-score {
        display: flex;
        width: 100%;
      }
      .el-button {
        width: 200px;
      }
      textarea {
        flex: 1;
        height: 100%;
        padding: 1rem;
        white-space: pre-wrap;
        border: none;
        resize: none;
        &::placeholder {
          color: rgb(185, 183, 183);
        }
        &::-webkit-scrollbar {
          width: 3px;
        }
        &::-webkit-scrollbar-thumb {
          background-color: #ddd;
        }
      }
      .el-select {
        width: 10rem;
      }
    }
  }
}

.report-card {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;

  display: flex;
  justify-content: center;
  align-items: center;

  background-color: rgba(0, 0, 0, 0.3);
}
</style>
