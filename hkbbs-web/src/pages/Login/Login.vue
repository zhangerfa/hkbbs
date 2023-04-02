<template>
  <div class="login-container">
    <h2>登录</h2>
    <el-form :model="form" label-width="120px">
      <el-form-item label="用户名">
        <el-input size="large" v-model="form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input size="large" type="password" v-model="form.password" />
      </el-form-item>
      <el-form-item class="btn-container">
          <el-button type="primary" @click="onSubmit">登录</el-button>
          <el-button>重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'

// do not use same name with ref
const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const onSubmit = () => {
  console.log('submit!')
}

</script>

<style scoped>
.login-container {
  box-sizing: border-box;
  width: 800px;
  height: 400px;
  margin: 150px auto;
  padding: 50px;
  text-align: center;
  border: 1px solid #ccc;
  border-radius: 10px;
}
</style>
