<template>
  <div class="task">
    <div class="container">
      <!-- 全部任务 -->
      <div class="all">
        <h2>全部任务</h2>
        <div class="wrapper" ref="wrapper">
          <task-card
            v-for="task in taskList"
            :key="task.id"
            :task="task"
            @onCheck="onCheck"
            @showDatail="onShowDetail(task)"
            @refresh="onRefresh"
          />
        </div>
        <div class="pagination">
          <a-pagination :total="taskTotal" show-less-items :pageSize="6" v-model:current="taskCurPage" @change="getTaskList" />
        </div>
      </div>

      <!-- 推荐任务 -->
      <div class="recommend" v-if="userrole === '2'">
        <h2>推荐任务</h2>
        <div class="wrapper">
          <task-card
            v-for="task in recommendList"
            :key="task.id"
            :task="task"
            @onCheck="onCheck"
            @showDatail="onShowDetail(task)"
            @refresh="onRefresh"
          />
        </div>
        <div class="pagination">
          <a-pagination :total="recommendTotal" show-less-items :pageSize="6" v-model:current="recommendCurPage" @change="getRecommend" />
        </div>
      </div>
    </div>

    <!-- 任务详情 -->
    <div class="detail">
      <div v-if="taskLength">
        <h2>{{ taskDetail.name }}</h2>
        <h3>描述：</h3>
        <p>
          {{ taskDetail.description }}
        </p>
        <h3>状态：{{ getStatus(taskDetail) }}</h3>
        <h3>任务类型：{{ taskDetail.type }}</h3>
        <h3>测试设备：{{ taskDetail.equipment }}</h3>
        <h3>任务难度：{{ taskDetail.difficulty }}</h3>

        <h3>需要工人数量：{{ taskDetail.worker_num }}</h3>

        <h3>
          截止时间：{{ new Date(taskDetail.end_time).toLocaleDateString() }}
        </h3>
        <!-- <h3>结算薪资：{{ taskDetail.reward }}￥</h3> -->
      </div>
      <div
        v-else
        style="
          height: 100%;
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: center;
        "
      >
        <a-empty />
      </div>
    </div>
    <div>
      <button class="help-button" @click="resetGuidance">
        帮助
      </button>
    </div>
  </div>
</template>

<script>
// 测试数据
// const taskList = [];
// for (let i = 1; i < 5; i++) {
//   taskList.push({
//     id: i,
//     name: `任务${i}`,
//     description: "该任务主要是……该任务主要是……",
//     detail: "该任务主要是……该任务主要是……",
//     status: 1,
//     beginTime: "2022-2-26",
//     endTime: "2022-2-28",
//     reward: 1000,
//   });
// }

import TaskCard from '../components/content/TaskCard.vue'

export default {
  name: 'square',
  components: {
    TaskCard
  },
  data() {
    return {
      taskList: [],
      recommendList: [],
      task: {},
      taskLength: 0,
      taskWidth: 400,
      taskDetail: {},
      userrole: 0,
      taskTotal: 0,
      recommendTotal: 0,
      taskCurPage: 1,
      recommendCurPage: 1,
    }
  },
  filters: {
    isTaskStatus(status) {
      const statusTable = {
        1: '正在招募',
        2: '已完成',
        0: '正在执行'
      }
      return statusTable[status]
    }
  },
  mounted() {
    this.isTaskEmpty()
    this.taskWidth = this.$refs.wrapper.getBoundingClientRect().height
    this.userrole = window.localStorage.getItem('$userrole')
    //读取全部任务列表
    this.getTaskList()
    this.getRecommend()
    // this.getTaskDetail();
    setTimeout(()=>this.getGuidance(),1000)
  },
  methods: {
    resetGuidance() {
      localStorage.guidanceShown = false;
      //console.log(localStorage.guidanceShown);
      this.getGuidance()
    },
    getGuidance() { //页面引导
      //console.log(localStorage.guidanceShown);
      if(localStorage.guidanceShown === "true") {
        return;
      }
      const steps = [{
        element: '.all',        // Query selector string or Node to be highlighted
        stageBackground: '#ffffff',   // This will override the one set in driver
        popover: {                    // There will be no popover if empty or not given
          className: 'popover-all', // className to wrap this specific step popover in addition to the general className in Driver options
          title: '全部任务',             // Title on the popover
          description: '这里可以浏览全部任务，在右下角可以翻页。', // Body of the popover
          position: 'mid-center',
          showButtons: true,
        }
      },
        {
          element: '.recommend',        // Query selector string or Node to be highlighted
          stageBackground: '#ffffff',   // This will override the one set in driver
          popover: {                    // There will be no popover if empty or not given
            className: 'popover-all', // className to wrap this specific step popover in addition to the general className in Driver options
            title: '推荐任务',             // Title on the popover
            description: '推荐任务会根据您参与过的历史任务进行推荐。', // Body of the popover
            position: 'mid-center',
            showButtons: true,
          }
        },
        {
          element: '.detail',        // Query selector string or Node to be highlighted
          stageBackground: '#ffffff',   // This will override the one set in driver
          popover: {                    // There will be no popover if empty or not given
            className: 'popover-all', // className to wrap this specific step popover in addition to the general className in Driver options
            title: '任务详情',             // Title on the popover
            description: '选择任务后可在这里查看详细信息。', // Body of the popover
            position: 'left',
            showButtons: true,
          }
        }];
      this.$driver.defineSteps(steps) //传入配置参数
      this.$driver.start()
      localStorage.guidanceShown = true;
    },
    getTaskDetail() {
      let task_id = window.localStorage.getItem('task_id')
      if (task_id) {
        this.$axios({
          method: 'get',
          url: `/task/${task_id}`
        }).then((response) => {
          // console.log(response) //请求成功返回的数据
          let ret = response.data
          this.taskList = ret.list.reduce((prev, next) => {
            obj[next.id] ? '' : (obj[next.id] = true && prev.push(next))
            return prev
          }, [])
        })
      }
    },
    getRecommend() {
      let uid1 = window.localStorage.getItem('userId')
      let userrole = window.localStorage.getItem('$userrole')
      let uid = Number.parseInt(uid1)
      let url = `/task/getAll?page=${this.recommendCurPage}`
      if (userrole === '2') {
        url = `/task/getRecommend?uid=${uid}&page=${this.recommendCurPage}`
      }
      this.$axios({
        method: 'get',
        url
      }).then((response) => {
        // console.log(response) //请求成功返回的数据
        let ret = response.data
        let obj = {}
        this.recommendTotal = ret.total
        this.recommendList = ret.list.reduce((prev, next) => {
          obj[next.id] ? '' : (obj[next.id] = true && prev.push(next))
          return prev
        }, [])
      })
    },
    //读取任务列表
    getTaskList() {
      let uid1 = window.localStorage.getItem('userId')
      let userrole = window.localStorage.getItem('$userrole')
      let uid = Number.parseInt(uid1)
      console.log(this.taskCurPage)
      let url = `/task/getAll?page=${this.taskCurPage}`
      // console.log(userrole)
      if (userrole === '2') {
        url = `/task/getList?uid=${uid}&page=${this.taskCurPage}`
      }
      this.$axios({
        method: 'get',
        url
      }).then((response) => {
        console.log(response) //请求成功返回的数据
        let ret = response.data
        let obj = {}
        this.taskTotal = ret.total
        this.taskList = ret.list.reduce((prev, next) => {
          obj[next.id] ? '' : (obj[next.id] = true && prev.push(next))
          return prev
        }, [])
      })
    },

    // 查看任务详情
    onCheck(task) {
      this.$router.push(`/home/square/task-detail/${task.id}`)
    },

    isTaskEmpty() {
      return Object.values(this.taskDetail).length
    },
    getStatus(task) {
      const currentDate = new Date().getTime()
      const taskDate = new Date(task.end_time).getTime()
      return taskDate > currentDate ? '进行中' : '已截止'
    },
    // 右侧边栏显示任务详情
    onShowDetail(task) {
      this.$axios({
        method: 'get',
        url: `/task/${task.id}`
      }).then((response) => {
        let ret = response.data
        this.taskDetail = ret
        this.taskLength = this.isTaskEmpty()
      })
    },

    /**
     * refresh page
     */
    onRefresh() {
      location.reload()
    }


  }
}
</script>

<style lang="less" scoped>
.task {
  width: 100%;
  height: 100%;
  padding: 1rem 0;
  position: relative;

  display: flex;
}

//
.container {
  flex: 1;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
  &::-webkit-scrollbar {
    width: 3px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #ddd;
  }
  .all,
  .recommend {
    flex: 1;
    width: 100%;
  }

  h2 {
    display: inline-block;
    margin-left: 2rem;
    padding: 0 2rem;
    border-bottom: 2px solid var(--global-color);
  }
  .wrapper {
    width: 100%;
    height: calc(100% - 10rem);
    display: flex;
    flex-wrap: wrap;
  }

  .pagination {
    width: 100%;
    text-align: right;
    .ant-pagination {
      margin-right: 3rem;
    }
  }
}

.detail {
  right: 0;
  width: 30rem;
  height: 100%;
  padding: 2rem 1rem;
  white-space: pre-wrap;
  border-left: 2px solid #ddd;
  h2 {
    letter-spacing: 2px;
  }
  p {
    white-space: pre-wrap;
    text-indent: 2em;
  }
}
.help-button{
  width: 50px;
  height: 50px;
  position: fixed;
  left: 30px;
  bottom: 30px;
  background-color: #1b0ba8;
  border: none;
  border-radius: 50%;
  color: white;
}
.driver-fix-stacking .header {
  position:relative;
}
</style>
