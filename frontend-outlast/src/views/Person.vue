<template>
  <div class="wrap">
    <div class="container">
      <el-row>
        <el-col :span="8" class="col">
          <span>用户名:</span>
          <span>{{userInfo.uname}}</span>
        </el-col>
        <el-col :span="8" class="col">
          <span>邮箱:</span>
          <span>{{userInfo.email||'无'}}</span>
        </el-col>
        <el-col :span="8" class="col">
          <span>手机号:</span>
          <span>{{userInfo.phone||'无'}}</span>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24" class="col">
          <span>个人简介:</span>
          <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 6}" placeholder="请输入个人简介" v-model="userInfo.description"></el-input>
        </el-col>
        <el-col :span="24" class="col justify-end">
          <el-button type="primary" size="small" style="margin-top:10px;" @click="editDes">修改简介</el-button>
        </el-col>
      </el-row>
      <template v-if="userrole==2">
        <el-divider></el-divider>
        <el-row type="flex" align="middle">
          <el-col :span="12">
            <div id="main" class="echarts"></div>
          </el-col>
          <el-col :span="12" class="progressCol">
            <el-progress type="circle" :percentage="userInfo.activity" :format="()=>(userInfo.activity||0)+'\n活跃度'"></el-progress>
            <el-progress type="circle" :percentage="userInfo.ability" :format="()=>(userInfo.ability||0)+'\n能力值'"></el-progress>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div id="bar3" class="echarts"></div>
          </el-col>
          <el-col :span="12">
            <div id="bar4" class="echarts"></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div id="bar1" class="echarts"></div>
          </el-col>
          <el-col :span="12">
            <div id="bar2" class="echarts"></div>
          </el-col>
        </el-row>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      detail: [], //echarts数据
      userInfo: '', //个人信息
      userrole: '' //用户权限
    }
  },
  created() {
    this.userrole = localStorage.getItem('$userrole') || ''

    this.$axios({
      method: 'get',
      url: `/user/getInformation`,
      params: {
        uid: window.localStorage.getItem('userId')
      }
    }).then(res => {
      this.detail = res.data
      console.log(this.detail)
      this.userInfo = res.data.userVO
      //只有工人有
      if (this.userrole == 2) {
        this.radar() //雷达
        this.bar() //任务
        this.barDevice() //设备
        this.showScoreBarChart()
        this.showSimilarityBarChart()
      }
    })
  },
  methods: {
    //更新简介
    editDes() {
      if (!this.userInfo.description) {
        this.$message.warning('请输入个人简介')
        return
      }
      this.$axios({
        method: 'post',
        url: `/user/updateInformation`,
        data: {
          uid: window.localStorage.getItem('userId'),
          description: this.userInfo.description
        }
      }).then(res => {
        this.$message.success('更新成功')
      })
    },
    radar() {
      var myChart = this.$echarts.init(document.getElementById('main'))
      let { activity, ability, cooperation, evaluation, similarity } = this.userInfo
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '个人雷达图'
        },
        // legend: {
        //   data: ['能力图']
        // },
        radar: {
          // shape: 'circle',
          //max最大值修改
          indicator: [
            { name: '活跃度', max: 100 },
            { name: '专业能力值', max: 20 },
            { name: '报告协作能力', max: 20 },
            { name: '报告评价程度', max: 5 },
            { name: '报告重复程度', max: 100 }
          ],
          axisName: {
            color: '#fff',
            backgroundColor: '#666',
            borderRadius: 3,
            padding: [3, 5]
          }
        },

        series: [
          {
            name: '能力图',
            type: 'radar',
            data: [
              {
                //对应上面的5个值
                value: [activity, ability, cooperation, evaluation, similarity],
                name: '能力图',
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value
                  }
                }
                // areaStyle: {
                //   color: 'rgba(0, 0, 0, 1)' //更改区域的颜色
                // }
              }
            ]
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    },
    bar() {
      var myChart = this.$echarts.init(document.getElementById('bar1'))
      let option = {
        title: {
          text: '任务详情'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '15%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['功能测试', '性能测试', '漏洞扫描', '用例执行', '安全性测试'],
          name: '任务类型'
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        series: [
          {
            name: '报告数量',
            type: 'bar',
            data: this.detail.preference_report
          },
          {
            name: '补充说明报告数量',
            type: 'bar',
            data: this.detail.preference_supplement
          },
          {
            name: '报告评价',
            type: 'bar',
            data: this.detail.preference_score
          },
          {
            name: '报告重复程度',
            type: 'bar',
            data: this.detail.preference_similarity
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    },
    barDevice() {
      var myChart = this.$echarts.init(document.getElementById('bar2'))
      let option = {
        title: {
          text: '设备详情'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '15%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['安卓', 'ios', 'linux', 'window', '鸿蒙'],
          name: '测试设备'
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        series: [
          {
            name: '报告数量',
            type: 'bar',
            data: this.detail.equipment_report
          },
          {
            name: '补充说明报告数量',
            type: 'bar',
            data: this.detail.equipment_supplement
          },
          {
            name: '报告评价',
            type: 'bar',
            data: this.detail.equipment_score
          },
          {
            name: '报告重复程度',
            type: 'bar',
            data: this.detail.equipment_similarity
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    },
    showScoreBarChart() {
      var myChart = this.$echarts.init(document.getElementById('bar3'))
      let option = {
        title: {
          text: '报告评分分布'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '15%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['1', '2', '3', '4', '5'],
          name: '评分'
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '数量',
            type: 'bar',
            data: this.detail.score_distribution
          }
        ]
      }
      myChart.setOption(option)
    },
    showSimilarityBarChart() {
      var myChart = this.$echarts.init(document.getElementById('bar4'))
      let option = {
        title: {
          text: '报告相似度分布'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '15%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['0-20', '20-40', '40-60', '60-80', '80-100'],
          name: '相似度'
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '数量',
            type: 'bar',
            data: this.detail.similarity_distribution
          }
        ]
      }
      myChart.setOption(option)
    }
  }
}
</script>

<style lang="less" scoped>
.wrap {
  padding: 20px;
  background: #f1f1f1;
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
  .container {
    min-height: 100%;
    background: #fff;
    padding: 20px;
    box-sizing: border-box;
    .col {
      display: flex;
      span {
        margin-right: 10px;
      }
    }
    .justify-end {
      justify-content: flex-end;
    }
    .echarts {
      width: 100%;
      height: 400px;
      background: #fff;
    }
    .progressCol {
      display: flex;
      align-items: center;
      justify-content: space-around;
    }
  }
}
</style>
<style lang="less">
.wrap {
  .el-row {
    margin-bottom: 20px;
  }
  .el-textarea {
    vertical-align: top;
  }
  .el-progress--circle .el-progress__text {
    white-space: pre-line;
    line-height: 1.4;
  }
}
</style>