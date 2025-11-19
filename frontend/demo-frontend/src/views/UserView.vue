<template>
  <div class="user-view">
    <div class="header">
      <h1>用户管理</h1>
      <button @click="showForm = true" class="btn btn-primary">
        <span>+ 添加用户</span>
      </button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input
        v-model="searchKeyword"
        @input="handleSearch"
        type="text"
        placeholder="搜索用户姓名或邮箱..."
        class="search-input"
      />
      <button @click="clearSearch" class="btn btn-secondary">
        清除
      </button>
    </div>

    <!-- 错误提示 -->
    <div v-if="userStore.error" class="error-message">
      {{ userStore.error }}
      <button @click="userStore.clearError()" class="close-btn">×</button>
    </div>

    <!-- 加载状态 -->
    <div v-if="userStore.loading" class="loading">
      加载中...
    </div>

    <!-- 用户列表 -->
    <UserList
      v-else
      :users="userStore.users"
      @edit="handleEdit"
      @delete="handleDelete"
    />

    <!-- 用户表单弹窗 -->
    <UserForm
      v-if="showForm"
      :user="editingUser"
      @save="handleSave"
      @cancel="handleCancel"
    />
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user'
import UserList from '@/components/UserList.vue'
import UserForm from '@/components/UserForm.vue'
import { ref, onMounted } from 'vue'

export default {
  name: 'UserView',
  components: {
    UserList,
    UserForm
  },
  setup() {
    const userStore = useUserStore()
    const showForm = ref(false)
    const editingUser = ref(null)
    const searchKeyword = ref('')

    onMounted(() => {
      userStore.fetchUsers()
    })

    const handleEdit = (user) => {
      editingUser.value = { ...user }
      showForm.value = true
    }

    const handleDelete = async (id) => {
      if (confirm('确定要删除这个用户吗？')) {
        try {
          await userStore.deleteUser(id)
        } catch (error) {
          // 错误已在 store 中处理
        }
      }
    }

    const handleSave = async (userData) => {
      try {
        if (userData.id) {
          await userStore.updateUser(userData.id, userData)
        } else {
          await userStore.createUser(userData)
        }
        showForm.value = false
        editingUser.value = null
      } catch (error) {
        // 错误已在 store 中处理
      }
    }

    const handleCancel = () => {
      showForm.value = false
      editingUser.value = null
    }

    const handleSearch = () => {
      if (searchKeyword.value.trim()) {
        userStore.searchUsers(searchKeyword.value.trim())
      } else {
        userStore.fetchUsers()
      }
    }

    const clearSearch = () => {
      searchKeyword.value = ''
      userStore.fetchUsers()
    }

    return {
      userStore,
      showForm,
      editingUser,
      searchKeyword,
      handleEdit,
      handleDelete,
      handleSave,
      handleCancel,
      handleSearch,
      clearSearch
    }
  }
}
</script>

<style scoped>
.user-view {
  max-width: 1000px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.header h1 {
  color: #333;
  font-size: 2.5rem;
}

.search-bar {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.search-input {
  flex: 1;
  padding: 12px 16px;
  border: 2px solid #e1e5e9;
  border-radius: 8px;
  font-size: 16px;
  transition: border-color 0.3s;
}

.search-input:focus {
  outline: none;
  border-color: #667eea;
}

.error-message {
  background: #fee;
  border: 1px solid #fcc;
  color: #c33;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #c33;
}

.loading {
  text-align: center;
  padding: 2rem;
  font-size: 1.1rem;
  color: #666;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s;
}

.btn-primary {
  background: #667eea;
  color: white;
}

.btn-primary:hover {
  background: #5a6fd8;
  transform: translateY(-2px);
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background: #5a6268;
}
</style>
