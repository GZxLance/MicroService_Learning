const express = require("express");
const pool = require("./db");
const { NacosNamingClient } = require("nacos"); // 引入 NacosNamingClient

const app = express();
const port = 3000;
// 启用 JSON 请求体解析
app.use(express.json());

// 启用 URL 编码请求体解析（用于表单数据）
app.use(express.urlencoded({ extended: true }));
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
// 获取用户列表
app.get("/users", async (req, res) => {
  const query = "SELECT * FROM t_user";
  pool.execute(query, (err, results) => {
    if (err) return res.status(500).json({ error: err.message });
    res.status(200).json(results);
  });
});

app.post("/users", (req, res) => {
  const { mobile, password, user_name = "", avatar_url = "" } = req.body;

  // 检查必填字段是否存在
  if (!mobile || !password) {
    return res.status(400).json({
      error: "Missing required fields: mobile and password are required",
    });
  }

  const insertQuery = `
    INSERT INTO t_user (mobile, password, user_name, avatar_url, create_time, update_time)
    VALUES (?, ?, ?, ?, NOW(), NOW());
  `;

  // 执行插入操作
  pool.execute(
    insertQuery,
    [mobile, password, user_name, avatar_url],
    (err, result) => {
      if (err) {
        console.error(err); // 打印错误信息
        return res.status(500).json({ error: err.message });
      }

      // 获取新插入的用户 ID
      const newUserId = result.insertId;

      // 根据新插入的 ID 查询用户信息
      const selectQuery = "SELECT * FROM t_user WHERE id = ?";
      pool.execute(selectQuery, [newUserId], (err, results) => {
        if (err) {
          console.error(err);
          return res.status(500).json({ error: err.message });
        }

        // 返回当前用户信息
        if (results.length > 0) {
          res.status(200).json(results[0]);
        } else {
          res
            .status(500)
            .json({ error: "Failed to retrieve user information" });
        }
      });
    }
  );
});

app.post("/updateUser", (req, res) => {
  const { id, mobile, password, user_name, avatar_url } = req.body;
  const userId = id;

  // 构建更新语句
  const updates = [];
  const values = [];

  if (password !== undefined) {
    updates.push("password = ?");
    values.push(password);
  }
  if (user_name !== undefined) {
    updates.push("user_name = ?");
    values.push(user_name);
  }
  if (avatar_url !== undefined) {
    updates.push("avatar_url = ?");
    values.push(avatar_url);
  }
  if (mobile !== undefined) {
    updates.push("mobile = ?");
    values.push(mobile);
  }

  if (updates.length === 0) {
    return res.status(400).json({ message: "No fields to update" });
  }

  values.push(userId);

  const query = `
    UPDATE t_user
    SET ${updates.join(", ")}
    WHERE id = ?;
  `;

  pool.execute(query, values, (err, result) => {
    if (err) {
      return res.status(500).json({ message: err.message });
    }
    if (result.affectedRows === 0) {
      return res.status(404).json({ message: "User not found" });
    }
    res.status(200).json({ message: "success" });
  });
});

// 删除用户
app.delete("/users/:id", async (req, res) => {
  const userId = req.params.id;
  const query = "DELETE FROM t_user WHERE id = ?";
  pool.execute(query, [userId], (err, result) => {
    if (err) return res.status(500).json({ error: err.message });
    if (result.affectedRows === 0)
      return res.status(404).json({ error: "没有该用户" });
    res.status(200).json({ message: "用户删除成功" });
  });
});

app.listen(port, async () => {
  console.log(`Node.js service is running at http://localhost:${port}`);
  await registerService();
});
