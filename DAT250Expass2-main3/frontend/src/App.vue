<script setup>
import CreateUserComponent from './components/CreateUserComponent.vue'
import CreatePollComponent from './components/CreatePollComponent.vue'
import VoteComponent from './components/VoteComponent.vue'
</script>

<template>
  <main>
    <h1>Poll Application</h1>

    <section>
      <CreateUserComponent />
    </section>

    <section>
      <CreatePollComponent />
    </section>

    <section>
      <VoteComponent />
    </section>
  </main>
</template>

<style scoped>
main {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  font-family: Arial, sans-serif;
}

section {
  margin-bottom: 2rem;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 6px;
}
</style>
