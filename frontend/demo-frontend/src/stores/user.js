import { defineStore } from 'pinia'
import { userApi } from '../api/userApi'

export const useUserStore = defineStore('user', {
  state: () => ({
    users: [],
    loading: false,
    error: null,
    currentUser: null
  }),

  actions: {
    async fetchUsers() {
      this.loading = true
      this.error = null
      try {
        this.users = await userApi.getAllUsers()
      } catch (error) {
        this.error = error.message
      } finally {
        this.loading = false
      }
    },

    async fetchUserById(id) {
      this.loading = true
      this.error = null
      try {
        this.currentUser = await userApi.getUserById(id)
      } catch (error) {
        this.error = error.message
      } finally {
        this.loading = false
      }
    },

    async createUser(userData) {
      this.loading = true
      this.error = null
      try {
        const newUser = await userApi.createUser(userData)
        this.users.push(newUser)
        return newUser
      } catch (error) {
        this.error = error.message
        throw error
      } finally {
        this.loading = false
      }
    },

    async updateUser(id, userData) {
      this.loading = true
      this.error = null
      try {
        const updatedUser = await userApi.updateUser(id, userData)
        const index = this.users.findIndex(user => user.id === id)
        if (index !== -1) {
          this.users[index] = updatedUser
        }
        return updatedUser
      } catch (error) {
        this.error = error.message
        throw error
      } finally {
        this.loading = false
      }
    },

    async deleteUser(id) {
      this.loading = true
      this.error = null
      try {
        await userApi.deleteUser(id)
        this.users = this.users.filter(user => user.id !== id)
      } catch (error) {
        this.error = error.message
        throw error
      } finally {
        this.loading = false
      }
    },

    async searchUsers(keyword) {
      this.loading = true
      this.error = null
      try {
        this.users = await userApi.searchUsers(keyword)
      } catch (error) {
        this.error = error.message
      } finally {
        this.loading = false
      }
    },

    clearError() {
      this.error = null
    },

    clearCurrentUser() {
      this.currentUser = null
    }
  },

  getters: {
    getUserById: (state) => (id) => {
      return state.users.find(user => user.id === id)
    }
  }
})
