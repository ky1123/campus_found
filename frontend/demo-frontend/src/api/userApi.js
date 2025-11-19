import axios from 'axios'

const API_BASE_URL = '/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export const userApi = {
  async getAllUsers() {
    const response = await api.get('/users')
    return response.data
  },

  async getUserById(id) {
    const response = await api.get(`/users/${id}`)
    return response.data
  },

  async getUserByEmail(email) {
    const response = await api.get(`/users/email/${email}`)
    return response.data
  },

  async createUser(userData) {
    const response = await api.post('/users', userData)
    return response.data
  },

  async updateUser(id, userData) {
    const response = await api.put(`/users/${id}`, userData)
    return response.data
  },

  async deleteUser(id) {
    await api.delete(`/users/${id}`)
  },

  async searchUsers(keyword) {
    const response = await api.get('/users/search', {
      params: { keyword }
    })
    return response.data
  }
}
