const express = require("express");
const { NacosNamingClient } = require("nacos"); // 引入 NacosNamingClient

const app = express();
const port = 3000;

// Nacos 配置
const nacosConfig = {
  serverList: "127.0.0.1:8848", // Nacos 服务地址
  namespace: "public", // Nacos 命名空间（如果有）
  username: "nacos", // Nacos 用户名（如果有）
  password: "nacos", // Nacos 密码（如果有）
  logger: console, // 使用 console 作为日志工具
};

// 创建 Nacos 命名客户端
const nacosClient = new NacosNamingClient(nacosConfig);

// 注册服务到 Nacos
async function registerService() {
  try {
    const serviceName = "node-service"; // 服务名称
    const instance = {
      ip: "127.0.0.1", // 服务 IP
      port: port, // 服务端口
      weight: 1, // 权重
      metadata: {
        version: "1.0.0",
        env: "dev",
      },
    };

    await nacosClient.ready(); // 等待客户端准备就绪
    await nacosClient.registerInstance(serviceName, instance); // 注册实例
    console.log("Service registered to Nacos successfully!");
  } catch (error) {
    console.error("Failed to register service to Nacos:", error);
  }
}

// 启动服务
app.get("/", (req, res) => {
  res.send("你好，node！");
});

app.listen(port, async () => {
  console.log(`Node.js service is running at http://localhost:${port}`);
  await registerService();
});
