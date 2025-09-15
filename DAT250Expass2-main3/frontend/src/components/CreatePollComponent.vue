<template>
  <div>
    <h2>Create Poll</h2>
    <form @submit.prevent="createPoll">
      <input v-model="question" placeholder="Poll question" required />
      <input v-model="creatorUsername" placeholder="Creator username" required />
      <input v-model="validUntil" type="datetime-local" required />
      <div v-for="(opt, index) in voteOptions" :key="index">
        <input v-model="opt.caption" placeholder="Option caption" />
      </div>
      <button type="button" @click="addOption">Add Option</button>
      <button type="submit">Create Poll</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const question = ref('')
const creatorUsername = ref('')
const validUntil = ref('')
const voteOptions = ref([{ caption: '' }, { caption: '' }])
const message = ref('')

function addOption() {
  voteOptions.value.push({ caption: '' })
}

async function createPoll() {
  const payload = {
    question: question.value,
    creatorUsername: creatorUsername.value,
    validUntil: new Date(validUntil.value).toISOString(),
    voteOptions: voteOptions.value.map((opt, i) => ({
      caption: opt.caption,
      presentationOrder: i + 1
    }))
  }

  const res = await fetch('http://localhost:8080/polls', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  })
  message.value = res.ok ? 'Poll created!' : 'Error creating poll'
}
</script>
