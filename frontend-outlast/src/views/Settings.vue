<!--
 * @Description: 
 * @Author: lz
 * @Date: 2022-04-02 22:58:54
 * @LastEditTime: 2022-04-03 00:25:34
 * @LastEditors: lz
-->
<template>
  <div class="setting-wrapper">
    <h3>规则设置</h3>
    <div class="setting-item">
      <div class="setting-item-label">难度范围:</div>
      <div class="setting-item-span">
        <p>
          简单:
          <a-input-number
            :min="1"
            :max="10"
            v-model="settingForm.easy_min"
            placeholder="请输入"
            style="margin-left: 5px; width: 140px; margin-right: 10px"
            @blur="checknum('min', 'easy_min', 'easy_max')"
          ></a-input-number>

          <a-input-number
            :min="1"
            :max="10"
            v-model="settingForm.easy_max"
            placeholder="请输入"
            style="margin-left: 5px; width: 140px"
            @blur="checknum('max', 'easy_max', 'easy_min')"
          ></a-input-number>
        </p>
        <p>
          普通:
          <a-input-number
            :min="1"
            :max="10"
            v-model="settingForm.medium_min"
            placeholder="请输入"
            style="margin-left: 5px; width: 140px; margin-right: 10px"
            @blur="checknum('min', 'medium_min', 'medium_max')"
          ></a-input-number>

          <a-input-number
            :min="1"
            :max="10"
            v-model="settingForm.medium_max"
            placeholder="请输入"
            style="margin-left: 5px; width: 140px"
            @blur="checknum('max', 'medium_max', 'medium_min')"
          ></a-input-number>
        </p>
        <p>
          困难:
          <a-input-number
            :min="1"
            :max="10"
            v-model="settingForm.hard_min"
            placeholder="请输入"
            style="margin-left: 5px; width: 140px; margin-right: 10px"
            @blur="checknum('min', 'hard_min', 'hard_max')"
          ></a-input-number>

          <a-input-number
            :min="1"
            :max="10"
            v-model="settingForm.hard_max"
            placeholder="请输入"
            style="margin-left: 5px; width: 140px"
            @blur="checknum('max', 'hard_max', 'hard_min')"
          ></a-input-number>
        </p>
      </div>
    </div>
    <div class="setting-item">
      <div class="setting-item-label">能力值规则:</div>
      <div class="setting-item-span">
        <p>
          完成简单任务加:
          <a-input-number
            v-model="settingForm.easy_increase"
            :min="0.1"
            :max="0.9"
            :step="0.1"
          />
        </p>
        <p>
          完成普通任务加:
          <a-input-number
            v-model="settingForm.medium_increase"
            :min="0.1"
            :max="0.9"
            :step="0.1"
          />
        </p>
        <p>
          完成困难任务加:
          <a-input-number
            v-model="settingForm.hard_increase"
            :min="0.1"
            :max="0.9"
            :step="0.1"
          />
        </p>
      </div>
    </div>
    <a-button @click="ruleUpdate()" type="primary">更新</a-button>
  </div>
</template>
<script>
// const userrole = localStorage.getItem("$userrole") - 0;
export default {
  data() {
    return {
      settingForm: {
        id: 1,
        easy_min: null,
        easy_max: "",
        medium_min: "",
        medium_max: "",
        hard_min: "",
        hard_max: "",
        easy_increase: "",
        medium_increase: "",
        hard_increase: "",
      },
    };
  },
  created() {
    this.getRule();
  },
//   beforeRouteEnter(to, from, next) {
//     if (userrole !== 0) {
//       next("/home/square");
//     } else {
//       next();
//     }
//   },
  methods: {
    checknum(type, value, thanValue) {
      let value1 = this.settingForm[value] - 0;
      let value2 = this.settingForm[thanValue] - 0;
      if (type === "max") {
        if (value1 < value2) {
          this.settingForm[value] = value2 += 1;
        }
      } else {
        if (value1 >= value2) {
          this.settingForm[value] = value2 - 1;
        }
      }
    },
    async getRule() {
      const res = await this.$axios({
        method: "get",
        url: `rule/getRule`,
      });
      if (res.data) {
        this.settingForm = res.data;
      }
    },
    async ruleUpdate() {
      const res = await this.$axios({
        method: "post",
        url: `rule/update`,
        data: this.settingForm,
      });
    },
  },
};
</script>
<style lang="less" scoped>
.setting-wrapper {
  padding: 40px;
  font-size: 16px;
  h3 {
    margin-bottom: 20px;
    font-size: 20px;
  }
  .setting-item {
    display: flex;
    .setting-item-label {
      width: 80px;
      margin-right: 20px;
      text-align: right;
    }
  }
}
</style>
