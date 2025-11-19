import axios from 'axios'

const API_BASE_URL = '/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export const backendTestApi = {
  async register(payload) {
    const resp = await api.post('/users/register', payload)
    return resp.data
  },

  async login(payload) {
    const resp = await api.post('/users/login', payload)
    return resp.data
  },

  async getUser(id) {
    const resp = await api.get(`/users/${id}`)
    return resp.data
  },

  async updateUser(id, payload) {
    const resp = await api.put(`/users/${id}`, payload)
    return resp.data
  },

  async checkPhone(phoneNumber) {
    const resp = await api.get('/users/check-phone', { params: { phoneNumber } })
    return resp.data
  }
}
