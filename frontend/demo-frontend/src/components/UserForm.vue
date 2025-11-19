<template>
  <div class="modal-overlay" @click.self="$emit('cancel')">
    <div class="modal-content">
      <div class="modal-header">
        <h2>{{ user ? '编辑用户' : '添加用户' }}</h2>
        <button @click="$emit('cancel')" class="close-btn">×</button>
      </div>

      <form @submit.prevent="handleSubmit" class="user-form">
        <div class="form-group">
          <label for="name">姓名 *</label>
          <input
            id="name"
            v-model="form.name"
            type="text"
            required
            placeholder="请输入用户姓名"
            class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="email">邮箱 *</label>
          <input
            id="email"
            v-model="form.email"
            type="email"
            required
            placeholder="请输入用户邮箱"
            class="form-input"
          />
        </div>

        <div class="form-actions">
          <button
            type="button"
            @click="$emit('cancel')"
            class="btn btn-cancel"
          >
            取消
          </button>
          <button
            type="submit"
            :disabled="!isFormValid"
            class="btn btn-submit"
          >
            {{ user ? '更新' : '创建' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, watch, computed } from 'vue'

export default {
  name: 'UserForm',
  props: {
    user: {
      type: Object,
      default: null
    }
  },
  emits: ['save', 'cancel'],
  setup(props, { emit }) {
    const form = ref({
      name: '',
      email: ''
    })

    // 如果是编辑模式，填充表单数据
    watch(() => props.user, (newUser) => {
      if (newUser) {
        form.value = {
          name: newUser.name,
          email: newUser.email
        }
      } else {
        form.value = {
          name: '',
          email: ''
        }
      }
    }, { immediate: true })

    const isFormValid = computed(() => {
      return form.value.name.trim() && form.value.email.trim()
    })

    const handleSubmit = () => {
      if (isFormValid.value) {
        const userData = {
          ...form.value
        }

        if (props.user) {
          userData.id = props.user.id
        }

        emit('save', userData)
      }
    }

    return {
      form,
      isFormValid,
      handleSubmit
    }
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 12px;
  padding: 0;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 10px 30px rgba(0,0,0,0.3);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid #e1e5e9;
}

.modal-header h2 {
  margin: 0;
  color: #333;
}

.close-btn {
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  color: #999;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  color: #666;
}

.user-form {
  padding: 1.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #333;
}

.form-input {
  width: 100%;
  padding: 12px;
  border: 2px solid #e1e5e9;
  border-radius: 8px;
  font-size: 16px;
  transition: border-color 0.3s;
}

.form-input:focus {
  outline: none;
  border-color: #667eea;
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 2rem;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s;
  min-width: 80px;
}

.btn-cancel {
  background: #6c757d;
  color: white;
}

.btn-cancel:hover {
  background: #5a6268;
}

.btn-submit {
  background: #667eea;
  color: white;
}

.btn-submit:hover:not(:disabled) {
  background: #5a6fd8;
}

.btn-submit:disabled {
  background: #ccc;
  cursor: not-allowed;
}
</style>
