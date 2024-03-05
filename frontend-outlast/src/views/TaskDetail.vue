<template>
  <div class="task-detail">
    <div class="wrapper">
      <h2>标题: {{ task.name }}</h2>
      <p>简介: {{ task.description }}</p>
      <p>类型: {{ task.type }}</p>
      <div>
        <span
          >截止时间: {{ new Date(task.end_time).toLocaleDateString() }}</span
        >
        <span>
          <a
            href="javascript:;"
            @click="onDownload(task.id)"
            >下载exe</a
          >
          <a
            href="javascript:;"
            @click="onDownloadDoc(task.id)"
            >下载文档</a
          >
        </span>
      </div>
    </div>

    <!-- 关系图 -->
    <h2>报告关系图</h2>
    <div class="tree-graph">
      <SeeksRelationGraph
          ref="seeksRelationGraph"
          :options="graphOptions"
          :on-node-click="onNodeClick"
          :on-line-click="onLineClick" />
    </div>

    <div class="report">
      <h2>已提交报告</h2>
      <div class="box">
        <table v-if="report && report.length">
          <tr v-for="(item, index) in report" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.user_name }}</td>
            <td>{{ new Date(item.post_time).toLocaleDateString()}}</td>
            <td>
              <a href="javascript:;" @click="goTo(item.id)">查看报告</a>
            </td>
          </tr>
        </table>
        <a-empty v-else />
      </div>
    </div>
  </div>
</template>

<script>
import SeeksRelationGraph from 'relation-graph'
import axios from "axios";
export default {
  name: "TaskDetail",
  components: { SeeksRelationGraph },
  data() {
    return {
      task: {},
      report: [],
      graphOptions: {
        allowSwitchLineShape: true,
        allowSwitchJunctionPoint: true,
        defaultJunctionPoint: 'border',
        defaultNodeFontColor: '#390457',
        "allowShowMiniToolBar": false,
        'layouts': [
          {
            "label": "主题2",
            "layoutName": "force",
            "layoutClassName": "seeks-layout-center",
            distance_coefficient: 0.2

          }
        ]
      }
    };
  },
  created() {

  },
  mounted() {
    if (this.$route.params.id) {
      this.getTaskId(this.$route.params.id);
      this.getFinished();
    }
  },
  methods: {
    // 获取任务id
    getTaskId(task_id) {
      this.$axios({
        method: "get",
        url: `/task/${task_id}`,
      }).then((response) => {
        let ret = response.data;
        this.task = ret;
      });
    },

    // 跳转到报告详情页
    goTo(id) {
      this.$router.push(`/home/square/report-detail/${id}`);
    },
    // 下载exe
    onDownload(task_id) {
      window.open(
        axios.defaults.baseURL + `/task/${task_id}/downloadExecutable`
      );
    },
 // 下载文档
    onDownloadDoc(task_id) {
      window.open(
        axios.defaults.baseURL + `/task/${task_id}/downloadDoc`
      );
    },
    async getFinished(currPage = 0) {
      let response = await this.$axios({
        method: "get",
        url: `/report/getList/${this.$route.params.id}?currPage=${currPage}`,
      });
      let ret = response.data;
      this.report = ret.list;

      var __graph_json_data = {
        rootId: 'task',
        nodes: [
          {id: 'task', text: `任务`, color: '#f6e54d', borderColor: '#b49d5b'}
        ],
        links: [],
      }
      let i;
      console.log(this.report)
      for (i in this.report) {
        let r = this.report[i]
        __graph_json_data.nodes.push({id: `${r.id}`, text: `报告-${r.user_name}`, color: '#a0d6ff', borderColor: '#85a2ce'})
        __graph_json_data.links.push({from: `${r.id}`, to: 'task', text: '', color: '#67C23A'})
      }
      for (i in this.report) {
        let r = this.report[i]
        response = await this.$axios({
          method: "get",
          url: `/report/getSimilar?reportId=${r.id}`,
        })
        ret = response.data;
        console.log(ret)
        if(ret!=="") {
          __graph_json_data.links.push({from: `${r.id}`, to: `${ret.id}`, text: '相似', color: '#67C23A'})
        }
        response = await this.$axios({
          method: "get",
          url: `/supplement/getList?report_id=${r.id}&currPage=0`,
        });
        ret = response.data.list;
        let s;
        for (s in ret) {
          let sup = ret[s]
          __graph_json_data.nodes.push({id: `sup_${sup.id}`, text: `补充报告-${sup.user_name}`, color: '#a0d6ff', borderColor: '#e5709f'})
          __graph_json_data.links.push({from: `sup_${sup.id}`, to: `${r.id}`, text: '补充', color: '#43a2f1'})
        }
      }

      this.$refs.seeksRelationGraph.setJsonData(__graph_json_data, (seeksRGGraph) => {
        //console.log(seeksRGGraph)
      })
    },
    onNodeClick(nodeObject, $event) {
      console.log('onNodeClick:', nodeObject)
    },
    onLineClick(lineObject, $event) {
      console.log('onLineClick:', lineObject)
    }
  },
};
</script>

<style lang="less" scoped>
.task-detail {
  width: 100%;
  height: 100%;
  padding: 1rem 2rem;
  overflow: scroll;
}

.wrapper {
  width: inherit;
  height: 30%;
  font-size: 16px;
  h2 {
    letter-spacing: 2px;
  }
  p {
    white-space: pre-wrap;
    letter-spacing: 1px;
  }
  div {
    display: flex;
    justify-content: space-between;
    align-items: center;
    span {
      a {
        margin: 0 2rem;
        padding: 5px 10px;
      }
      &:last-child {
        margin-right: 2rem;
      }
    }
  }
}

.tree-graph {
  width: 50vw;
  height: 50vh;
  border-color: darkblue;
  border-style: solid;
  position: center;
}

.report {
  width: inherit;
  height: calc(100% - 35%);
  padding: 1rem;
  h2 {
    height: 3rem;
  }
  .box {
    height: calc(100% - 3rem);
    overflow-y: scroll;
    &::-webkit-scrollbar {
      width: 5px;
    }
    &::-webkit-scrollbar-thumb {
      background-color: #ddd;
    }
  }
  table {
    width: 100%;
    font-size: 14px;
    tr {
      width: inherit;
      border-top: 2px solid #f6f7fb;
      border-bottom: 2px solid #f6f7fb;
      transition: all 0.3s ease-in-out;
      cursor: auto;
      &:hover {
        background-color: #f6f7fb;
      }
      td {
        padding: 2rem 0;
        text-align: center;
      }
    }
  }
}
</style>
