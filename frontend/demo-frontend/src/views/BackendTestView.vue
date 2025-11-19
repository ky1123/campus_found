<template>
  <div class="backend-test">
    <h1>后端接口测试页面</h1>

    <section>
      <h2>注册 (POST /api/users/register)</h2>
      <input v-model="registerData.name" placeholder="name" />
      <input v-model="registerData.phoneNumber" placeholder="phoneNumber" />
      <input v-model="registerData.password" placeholder="password" type="password" />
      <button @click="doRegister">Register</button>
      <pre>{{ lastRegisterResponse }}</pre>
    </section>

    <section>
      <h2>登录 (POST /api/users/login)</h2>
      <input v-model="loginData.phoneNumber" placeholder="phoneNumber" />
      <input v-model="loginData.password" placeholder="password" type="password" />
      <button @click="doLogin">Login</button>
      <pre>{{ lastLoginResponse }}</pre>
    </section>

    <section>
      <h2>查询用户 (GET /api/users/{id})</h2>
      <input v-model.number="queryId" placeholder="user id" />
      <button @click="doGetUser">Get User</button>
      <pre>{{ lastGetUserResponse }}</pre>
    </section>

    <section>
      <h2>更新用户 (PUT /api/users/{id})</h2>
      <input v-model.number="updateId" placeholder="user id" />
      <input v-model="updateData.name" placeholder="name" />
      <input v-model="updateData.otherContact" placeholder="otherContact" />
      <button @click="doUpdateUser">Update</button>
      <pre>{{ lastUpdateResponse }}</pre>
    </section>

    <section>
      <h2>检查手机号 (GET /api/users/check-phone?phoneNumber=)</h2>
      <input v-model="checkPhoneNumberValue" placeholder="phoneNumber" />
      <button @click="doCheckPhone">Check Phone</button>
      <pre>{{ lastCheckPhoneResponse }}</pre>
    </section>

  </div>
</template>

<script lang="ts">
import { reactive, ref } from 'vue'
import { backendTestApi } from '../api/backendTestApi'

export default {
  name: 'BackendTestView',
  setup() {
    const registerData = reactive({ name: '', phoneNumber: '', password: '' })
    const loginData = reactive({ phoneNumber: '', password: '' })
    const queryId = ref(null)
    const updateId = ref(null)
    const updateData = reactive({ name: '', otherContact: '' })
    const checkPhoneNumberValue = ref('')

    const lastRegisterResponse = ref('')
    const lastLoginResponse = ref('')
    const lastGetUserResponse = ref('')
    const lastUpdateResponse = ref('')
    const lastCheckPhoneResponse = ref('')

    async function doRegister() {
      try {
        const res = await backendTestApi.register(registerData)
        lastRegisterResponse.value = JSON.stringify(res, null, 2)
      } catch (err) {
        lastRegisterResponse.value = err?.response?.data || String(err)
      }
    }

    async function doLogin() {
      try {
        const res = await backendTestApi.login(loginData)
        lastLoginResponse.value = JSON.stringify(res, null, 2)
      } catch (err) {
        lastLoginResponse.value = err?.response?.data || String(err)
      }
    }

    async function doGetUser() {
      try {
        const res = await backendTestApi.getUser(queryId.value)
        lastGetUserResponse.value = JSON.stringify(res, null, 2)
      } catch (err) {
        lastGetUserResponse.value = err?.response?.data || String(err)
      }
    }

    async function doUpdateUser() {
      try {
        const res = await backendTestApi.updateUser(updateId.value, updateData)
        lastUpdateResponse.value = JSON.stringify(res, null, 2)
      } catch (err) {
        lastUpdateResponse.value = err?.response?.data || String(err)
      }
    }

    async function doCheckPhone() {
      try {
        const res = await backendTestApi.checkPhone(checkPhoneNumberValue.value)
        lastCheckPhoneResponse.value = JSON.stringify(res, null, 2)
      } catch (err) {
        lastCheckPhoneResponse.value = err?.response?.data || String(err)
      }
    }

    return {
      registerData,
      loginData,
      queryId,
      updateId,
      updateData,
      checkPhoneNumberValue,
      lastRegisterResponse,
      lastLoginResponse,
      lastGetUserResponse,
      lastUpdateResponse,
      lastCheckPhoneResponse,
      doRegister,
      doLogin,
      doGetUser,
      doUpdateUser,
      doCheckPhone
    }
  }
}
</script>

<style scoped>
.backend-test {
  padding: 16px;
}
section {
  margin-bottom: 18px;
  padding: 12px;
  border: 1px solid #eee;
  border-radius: 6px;
}
input {
  margin-right: 8px;
  margin-bottom: 6px;
}
button {
  margin-right: 8px;
}
pre {
  background: #f6f8fa;
  padding: 8px;
  border-radius: 4px;
  white-space: pre-wrap;
}
</style>
