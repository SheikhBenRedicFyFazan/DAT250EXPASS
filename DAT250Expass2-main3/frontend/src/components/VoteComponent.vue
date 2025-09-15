<template>
  <div>
    <h2>Vote on Poll</h2>
    <input v-model="pollId" placeholder="Poll ID" />
    <input v-model="username" placeholder="Your username" />
    <input v-model.number="voteOptionId" placeholder="Vote Option ID" />
    <button @click="vote">Submit Vote</button>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const pollId = ref('')
const username = ref('')
const voteOptionId = ref(null)
const message = ref('')

async function vote() {
  const res = await fetch(`http://localhost:8080/polls/${pollId.value}/votes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username: username.value, voteOptionId: voteOptionId.value })
  })
  message.value = res.ok ? 'Vote submitted!' : 'Error submitting vote'
}
</script>
