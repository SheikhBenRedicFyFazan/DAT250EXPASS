<template>
  <div>
    <h2>Create User</h2>
    <form @submit.prevent="createUser">
      <input v-model="username" placeholder="Username" required />
      <input v-model="email" placeholder="Email" required />
      <button type="submit">Create</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const username = ref('')
const email = ref('')
const message = ref('')

async function createUser() {
  const res = await fetch('http://localhost:8080/users', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username: username.value, email: email.value })
  })
  message.value = res.ok ? 'User created!' : 'Error creating user'
}
</script>
