const mysql = require("mysql2");

const pool = mysql.createPool({
  host: "localhost", // 数据库地址
  user: "root", // 数据库用户名
  password: "lplpmm5201314", // 数据库密码
  database: "user_center", // 数据库名称
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
});

module.exports = pool;
