<template>
  <div class="create">
    <!-- form -->
    <div class="form">
      <table>
        <!-- 名称 -->
        <tr>
          <td>名称：</td>
          <td>
            <a-input placeholder="任务名称" v-model="taskinfo.name" />
          </td>
        </tr>

        <!-- 简介 -->
        <tr>
          <td>简介：</td>
          <td>
            <a-input placeholder="任务简介" v-model="taskinfo.description" />
          </td>
        </tr>
        <!-- <tr>
          <td>起始时间：</td>
          <td>
            <a-date-picker @change="onChan0ge" />
          </td>
        </tr> -->

        <!-- 测试类型 -->
        <tr>
          <td>类型：</td>
          <td>
            <el-select
              v-model="taskinfo.type"
              placeholder="请选择测试类型"
              size="small"
            >
              <el-option
                v-for="item in testType"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </td>
        </tr>

        <!-- 测试设备 -->
        <tr>
          <td>设备：</td>
          <td>
            <el-select
              v-model="taskinfo.equipment"
              placeholder="请选择测试设备"
              size="small"
            >
              <el-option
                v-for="item in testDevice"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </td>
        </tr>

        <!-- 截止时间 -->
        <tr>
          <td>截止时间：</td>
          <td colspan="3">
            <a-date-picker
              v-model="taskinfo.end_time"
              :disabled-date="disabledDate"
              valueFormat="YYYY-MM-DD"
            />
          </td>
        </tr>

        <!-- 所需人数 -->
        <tr>
          <td>所需人数：</td>
          <td>
            <a-input-number
              id="inputNumber"
              v-model="taskinfo.worker_num"
              :min="1"
              :max="30"
            />
          </td>

          <!-- 难度 -->
          <td>难度：</td>
          <td>
            <el-select
              v-model="taskinfo.difficulty"
              placeholder="请选择难度"
              size="small"
            >
              <el-option v-for="num in 10" :key="num" :label="num" :value="num">
              </el-option>
            </el-select>
          </td>
        </tr>

        <!-- 可执行文件 -->
        <tr>
          <td>可执行文件：</td>
          <td>
            <!-- <el-upload
              ref="uploadExe"
              action="https://jsonplaceholder.typicode.com/posts/"
              :http-request="uploadExe"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-change="handleExeChange"
              :auto-upload="false"
              :limit="1"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload> -->
            <input
              id="fileExe"
              type="file"
              name="fileExe"
              @change="handleExeChange"
            />
          </td>
        </tr>

        <!-- 描述文档 -->
        <tr>
          <td>描述文档：</td>
          <td>
            <!-- <el-upload
              ref="uploadDoc"
              :http-request="uploadDoc"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-change="handleDocChange"
              :auto-upload="false"
              :limit="1"
              action=""
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload> -->
            <input
              id="fileDoc"
              type="file"
              name="fileDoc"
              @change="handleDocChange"
            />
          </td>
        </tr>

        <!-- 其他需求 -->
        <tr>
          <td>其他需求：</td>
          <td>
            <textarea
              style="
                resize: none;
                padding: 1rem;
                width: 100%;
                height: 10rem;
                border: 1px solid #ddd;
              "
              v-model="taskinfo.other"
            />
          </td>
        </tr>
      </table>

      <div class="option">
        <a-button type="primary" class="bottom" @click="sendTask">
          点击创建
        </a-button>
      </div>
    </div>
  </div>
</template>

<script>
// 测试类型集合
const testType = [
  {
    value: '功能测试',
    label: '功能测试'
  },
  {
    value: '性能测试',
    label: '性能测试'
  },
  {
    value: '漏洞扫描',
    label: '漏洞扫描'
  },
  {
    value: '用例执行',
    label: '用例执行'
  },
  {
    value: '安全性测试',
    label: '安全性测试'
  }
]

// 测试设备集合
const testDevice = [
  {
    value: '安卓',
    label: '安卓'
  },
  {
    value: 'ios',
    label: 'ios'
  },
  {
    value: 'linux',
    label: 'linux'
  },
  {
    value: 'window',
    label: 'window'
  },
  {
    value: '鸿蒙',
    label: '鸿蒙'
  }
]

//
import moment from 'moment'
export default {
  name: 'Create',
  data() {
    return {
      task: {},
      taskid: null,
      fileExe: null,
      fileDoc: null,
      taskinfo: {
        taskID: null,
        name: null,
        type: null,
        equipment: null,
        description: null,
        end_time: null,
        worker_num: null,
        other: null,
        difficulty: null
      },

      // 测试类型
      testType,

      //测试设备
      testDevice,

      // 测试难度
      testHardValue: ''
    }
  },
  methods: {
    disabledDate(current) {
      return current && current < moment().endOf('day')
    },
    onChange() {},
    //提交发布众测任务
    sendTask() {
      let uid = window.localStorage.getItem('userId')
      let uname = window.localStorage.getItem('username')
      // let reader = new FileReader();
      // let sendData = {
      //   id: "0",
      //   name: this.taskinfo.name,
      //   description: this.taskinfo.description,
      //   end_time: this.taskinfo.end_time,
      //   worker_num: this.taskinfo.worker_num + "",
      //   type: this.taskinfo.type,
      //   others: this.taskinfo.other,
      //   user_id: uid,
      //   user_name: uname,
      //   executable_file: reader.readAsText(
      //     document.getElementById("fileExe").files[0]
      //   ),
      //   doc_file: document.getElementById("fileDoc").files[0],
      // };
      const isFileUrl =
        document.getElementById('fileExe').files[0] &&
        document.getElementById('fileDoc').files[0]
      if (!isFileUrl) {
        return this.$message.warn('请上传可执行文件和描述文档!')
      }
      let formData = new FormData()
      formData.append('id', 0)
      formData.append('name', this.taskinfo.name)
      formData.append('description', this.taskinfo.description)
      formData.append('end_time', this.taskinfo.end_time)
      formData.append('worker_num', this.taskinfo.worker_num + '')
      formData.append('difficulty', this.taskinfo.difficulty)
      formData.append('type', this.taskinfo.type)
      formData.append('equipment', this.taskinfo.equipment)
      formData.append('others', this.taskinfo.other)
      formData.append('user_id', uid)
      formData.append('user_name', uname)
      formData.append(
        'executable_file',
        document.getElementById('fileExe').files[0]
      )
      formData.append('doc_file', document.getElementById('fileDoc').files[0])
      this.$axios({
        method: 'post',
        url: `/task/create`,
        data: formData,
        // 发送formdata这个数据格式，需要修改头部信息Content-type
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then((response) => {
          let ret = response.data
          if (ret.code === 0) {
            this.taskid = ret.data.id
            this.$message.success(ret.msg)
            this.$router.replace('/home/square')
          }
          else {
            this.$message.error(ret.msg)
          }
        })
        .catch((err) => {
          this.$message.error("创建失败！请检查参数是否正确填写。")
          console.log('err', err)
        })
    },
    /**
     * 处理 .exe 文件
     */
    handleExeChange(event) {
      const tar = event.target
      const file = tar.files[0]
      if (!/\.(zip|rar)$/gim.test(file.name)) {
        this.$message.warning('文件格式不支持,允许的格式为: zip,rar')
        tar.value = ''
        return
      }
    },
    /**
     * 处理 .doc|.docx 文件
     */
    handleDocChange(event) {
      const tar = event.target
      const file = tar.files[0]
      if (!/\.(pdf|md|doc|docx|txt)$/gim.test(file.name)) {
        this.$message.warning('文件格式不支持,允许的格式为: txt,pdf,md,doc,docx')
        tar.value = ''
        return
      }
    },
  }
}
</script>

<style lang="less" scoped>
.create {
  width: 100%;
  height: 100%;

  background: url(../assets/images/home_people.jpg) no-repeat;
  background-size: cover;

  position: relative;
  &::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.1);
  }

  .form {
    position: absolute;
    z-index: 10;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 80rem;
    height: auto;
    padding: 5rem;
    background-color: #fff;

    border-radius: 1rem;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);

    table {
      width: 100%;
      td {
        height: 5rem;
      }
    }

    .option {
      width: 100%;
      padding: 1rem 0 0;
      text-align: right;
    }
  }
}
</style>
