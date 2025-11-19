<template>
  <div class="home">
    <div class="hero-section">
      <h1 class="hero-title">欢迎使用用户管理系统</h1>
      <p class="hero-subtitle">基于 Vue 3 + Spring Boot 的全栈应用</p>
      <div class="hero-actions">
        <router-link to="/users" class="btn btn-primary">开始管理用户</router-link>
      </div>
    </div>

    <div class="features">
      <div class="feature-card">
        <h3>用户管理</h3>
        <p>完整的 CRUD 操作，支持用户信息的增删改查</p>
      </div>
      <div class="feature-card">
        <h3>实时搜索</h3>
        <p>基于姓名和邮箱的智能搜索功能</p>
      </div>
      <div class="feature-card">
        <h3>响应式设计</h3>
        <p>完美适配桌面和移动设备</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.home {
  text-align: center;
}

.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 4rem 2rem;
  border-radius: 15px;
  margin-bottom: 3rem;
}

.hero-title {
  font-size: 3rem;
  margin-bottom: 1rem;
  font-weight: 700;
}

.hero-subtitle {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.9;
}

.hero-actions {
  margin-top: 2rem;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-top: 3rem;
}

.feature-card {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 25px rgba(0,0,0,0.15);
}

.feature-card h3 {
  color: #667eea;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}

.feature-card p {
  color: #666;
  line-height: 1.6;
}

.btn {
  display: inline-block;
  padding: 12px 30px;
  background: white;
  color: #667eea;
  text-decoration: none;
  border-radius: 25px;
  font-weight: 600;
  transition: all 0.3s;
  border: 2px solid transparent;
}

.btn-primary {
  background: #667eea;
  color: white;
}

.btn-primary:hover {
  background: transparent;
  border-color: white;
  color: white;
}
</style>
