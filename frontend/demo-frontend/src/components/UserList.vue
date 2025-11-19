<template>
  <div class="user-list">
    <div v-if="users.length === 0" class="empty-state">
      暂无用户数据
    </div>

    <div v-else class="users-grid">
      <div
        v-for="user in users"
        :key="user.id"
        class="user-card"
      >
        <div class="user-info">
          <h3 class="user-name">{{ user.name }}</h3>
          <p class="user-email">{{ user.email }}</p>
          <p class="user-created">
            创建时间: {{ formatDate(user.createdAt) }}
          </p>
        </div>
        <div class="user-actions">
          <button
            @click="$emit('edit', user)"
            class="btn btn-edit"
          >
            编辑
          </button>
          <button
            @click="$emit('delete', user.id)"
            class="btn btn-delete"
          >
            删除
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserList',
  props: {
    users: {
      type: Array,
      required: true
    }
  },
  emits: ['edit', 'delete'],
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString)
      return date.toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.user-list {
  margin-top: 2rem;
}

.empty-state {
  text-align: center;
  padding: 3rem;
  color: #666;
  font-size: 1.1rem;
}

.users-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.user-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.user-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 20px rgba(0,0,0,0.15);
}

.user-info {
  margin-bottom: 1rem;
}

.user-name {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 1.2rem;
  font-weight: 600;
}

.user-email {
  color: #666;
  margin-bottom: 0.5rem;
  word-break: break-all;
}

.user-created {
  color: #999;
  font-size: 0.9rem;
}

.user-actions {
  display: flex;
  gap: 0.5rem;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
  font-weight: 600;
  transition: all 0.3s;
  flex: 1;
}

.btn-edit {
  background: #28a745;
  color: white;
}

.btn-edit:hover {
  background: #218838;
}

.btn-delete {
  background: #dc3545;
  color: white;
}

.btn-delete:hover {
  background: #c82333;
}
</style>
