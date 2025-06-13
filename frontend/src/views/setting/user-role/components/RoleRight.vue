<script setup lang="ts">
import type {IRole} from "/@/types/role.ts";

const currentRole = defineModel<Partial<IRole>>('role', {default: {}})
</script>

<template>
  <div>
    show something about selected role:
    <p>
      role name : {{currentRole.name}}
    </p>
    <p>
      internal : {{currentRole.internal}}
    </p>
  </div>
</template>

<style scoped>

</style>