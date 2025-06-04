<script setup lang="ts">
import { LockOutlined, UserOutlined } from "@vicons/antd";
import { type FormInst } from "naive-ui";
import { reactive, ref,onMounted } from "vue";
import { useAuthStore } from "/@/store/modules/auth";
const authStore = useAuthStore();
const loading = ref(false);
const loginFormRef = ref<FormInst | null>(null);
const loginForm = reactive({
  username: "admin",
  password: "123456",
});
const loginRules = {
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, message: "密码长度不能小于 6 个字符", trigger: "blur" },
  ],
};
const handleLogin = async (e: MouseEvent) => {
  e.preventDefault();
  loginFormRef.value?.validate(async (err) => {
    if (err) {
      return;
    }
    loading.value = true;
    await authStore.login(loginForm.username, loginForm.password);
    loading.value = false;
  });
};
// 页面加载完成后执行动画
onMounted(() => {
  animateBackground();
});

// 背景动画
const animateBackground = () => {
  const circles = document.querySelectorAll('.circle');
  circles.forEach((circle,index) => {
    // 根据窗口宽度调整动画速度
    const speedFactor = window.innerWidth > 768 ? 1 : 1.5;
    (circle as HTMLElement).style.animation = `float ${(3 + index * 2) * speedFactor}s ease-in-out infinite`;
  });
};
window.addEventListener('resize', animateBackground);
</script>

<template>
  <div class="login-wrapper">
    <!-- 左侧品牌展示区 -->
    <div class="login-side left-side">
      <div class="brand-container">
        <div class="logo">
          <svg
            width="48"
            height="48"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2Z"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M12 16C14.2091 16 16 14.2091 16 12C16 9.79086 14.2091 8 12 8C9.79086 8 8 9.79086 8 12C8 14.2091 9.79086 16 12 16Z"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </div>
        <h1 class="brand-title">企业管理系统</h1>
        <p class="brand-desc">高效、安全、智能的企业管理平台</p>
      </div>

      <!-- 动画背景 -->
      <div class="bg-animation">
        <div class="circle circle-1" />
        <div class="circle circle-2" />
        <div class="circle circle-3" />
      </div>
    </div>
    <!-- 右侧登录表单区 -->
    <div class="login-side right-side">
      <div class="login-card">
        <h3 class="login-title">登录系统</h3>
        <n-form
          ref="loginFormRef"
          class="login-form"
          :model="loginForm"
          :rules="loginRules"
          :label-width="80"
        >
          <n-form-item label="用户名" path="username">
            <n-input
              v-model:value="loginForm.username"
              placeholder="请输入用户名"
            >
              <template #prefix>
                <n-icon :component="UserOutlined" />
              </template>
            </n-input>
          </n-form-item>
          <n-form-item label="密码" path="password">
            <n-input
              v-model:value="loginForm.password"
              type="password"
              placeholder="请输入密码"
              show-password-on="click"
            >
              <template #prefix>
                <n-icon :component="LockOutlined" />
              </template>
            </n-input>
          </n-form-item>
          <n-form-item>
            <n-button type="primary" class="login-button" @click="handleLogin">
              {{ loading ? "登录中..." : "登录" }}
            </n-button>
          </n-form-item>
        </n-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-wrapper {
  display: flex;
  height: 100vh;
  overflow: hidden;
  background-color: #f5f7fa;
}

.login-side {
  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;
  transition: all 0.5s ease;
}

.left-side {
  background: linear-gradient(135deg, #a235e7, #3a0ca3);
  color: white;
  position: relative;
}

.right-side {
  background-color: #f5f7fa;
}

.brand-container {
  position: relative;
  z-index: 2;
  text-align: center;
}

.logo {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.logo svg {
  width: 40px;
  height: 40px;
  fill: white;
}

.brand-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 10px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.brand-desc {
  font-size: 16px;
  opacity: 0.9;
  max-width: 80%;
  margin: 0 auto;
}

.bg-animation {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.15);
  z-index: 1;
  transform: translateZ(0); /* 启用GPU加速 */
  will-change: transform, opacity; /* 提示浏览器优化 */
}

.circle-1 {
  width: 400px;
  height: 400px;
  top: -200px;
  right: -200px;
}

.circle-2 {
  width: 200px;
  height: 200px;
  bottom: -100px;
  left: -50px;
}

.circle-3 {
  width: 300px;
  height: 300px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0.7;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 30px;
  text-align: center;
  color: #333;
}

.login-form {
  margin-top: 20px;
}

.form-remember {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.forgot-password {
  color: #4361ee;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s;
}

.forgot-password:hover {
  color: #3a0ca3;
}

.login-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
}

.login-footer {
  margin-top: 40px;
  text-align: center;
  color: #888;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-wrapper {
    flex-direction: column;
  }

  .login-side {
    flex: none;
    height: 50vh;
  }

  .left-side {
    height: 40vh;
    order: 2;
  }

  .right-side {
    height: 60vh;
    order: 1;
  }

  .brand-container {
    padding: 20px;
  }

  .login-card {
    max-width: 90%;
    padding: 30px;
  }
}

/* 动画定义 */
@keyframes float {
  0%,
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.8;
  }
  50% {
    transform: translate(20px, -20px) scale(1.05);
    opacity: 1;
  }
}
</style>
