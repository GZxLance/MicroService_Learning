from http.server import BaseHTTPRequestHandler, HTTPServer
import json
import threading
from nacos import NacosClient
from wordcloud import WordCloud, ImageColorGenerator
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
import io
from PIL import Image
import os

# 设置中文字体
plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False  # 解决负号显示问题

# Nacos 配置
NACOS_SERVER = "127.0.0.1:8848"  # Nacos 服务地址
SERVICE_NAME = "python-service"  # 服务名称
SERVICE_PORT = 8086  # 服务端口

# 初始化 Nacos 客户端
nacos_client = NacosClient(NACOS_SERVER, namespace="public", username="nacos", password="nacos")


class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == "/hello":
            self.send_response(200)
            self.send_header('Content-type', 'text/plain; charset=utf-8')
            self.end_headers()
            self.wfile.write("Hello, python开发服务接口成功!".encode('utf-8'))
        else:
            self.send_response(404)
            self.send_header('Content-type', 'text/plain; charset=utf-8')
            self.end_headers()
            self.wfile.write("未找到".encode('utf-8'))

    def do_POST(self):
        if self.path == "/echo":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            try:
                data = json.loads(post_data)
                response = {"消息": f"接收: {data}"}
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.end_headers()
                self.wfile.write(json.dumps(response).encode('utf-8'))
            except json.JSONDecodeError:
                self.send_response(400)
                self.send_header('Content-type', 'text/plain; charset=utf-8')
                self.end_headers()
                self.wfile.write("无效的JSON".encode('utf-8'))

        elif self.path == "/generate_wordcloud":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            try:
                data = json.loads(post_data)
                text = data.get('text')
                if not text:
                    raise ValueError("未提供文本")

                # 指定中文字体文件路径
                font_path = 'simhei.ttf'  # 确保该路径正确

                # 加载掩码图像（可选）
                mask_image_path = 'mask.png'  # 掩码图像路径
                mask = None
                if os.path.exists(mask_image_path):
                    mask = np.array(Image.open(mask_image_path))

                # 生成词云，使用中文字体和其他样式设置
                wc = WordCloud(
                    width=800,
                    height=400,
                    background_color='white',  # 背景颜色
                    font_path=font_path,  # 字体路径
                    max_words=200,  # 最大单词数
                    colormap='viridis',  # 同色系颜色映射
                    contour_width=3,  # 轮廓宽度
                    contour_color='steelblue',  # 轮廓颜色
                    mask=mask  # 掩码图像
                ).generate(text)

                # 将词云保存到内存缓冲区
                img = io.BytesIO()
                plt.figure(figsize=(10, 5))
                plt.imshow(wc, interpolation='bilinear')
                plt.axis('off')
                plt.savefig(img, format='png')
                img.seek(0)

                self.send_response(200)
                self.send_header('Content-type', 'image/png')
                self.end_headers()
                self.wfile.write(img.getvalue())

            except Exception as e:
                self.send_response(400)
                self.send_header('Content-type', 'text/plain; charset=utf-8')
                self.end_headers()
                self.wfile.write(str(e).encode('utf-8'))

        elif self.path == "/generate_barplot":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            try:
                data = json.loads(post_data)
                df = pd.DataFrame(data)

                # 假设数据是一个包含键 'category' 和 'value' 的字典列表
                plt.figure(figsize=(10, 5))
                sns.barplot(x='category', y='value', data=df, palette='viridis')  # 使用同色系调色板
                plt.title('编程语言流行度')  # 图表标题
                plt.xlabel('编程语言')  # x轴标签
                plt.ylabel('流行度 (%)')  # y轴标签
                plt.xticks(rotation=45)  # 旋转类别标签以提高可读性

                # 将图表保存到内存缓冲区
                img = io.BytesIO()
                plt.savefig(img, format='png', bbox_inches='tight')
                img.seek(0)

                self.send_response(200)
                self.send_header('Content-type', 'image/png')
                self.end_headers()
                self.wfile.write(img.getvalue())

            except Exception as e:
                self.send_response(400)
                self.send_header('Content-type', 'text/plain; charset=utf-8')
                self.end_headers()
                self.wfile.write(str(e).encode('utf-8'))

        elif self.path == "/generate_piechart":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            try:
                data = json.loads(post_data)
                labels = [item['label'] for item in data]
                values = [item['value'] for item in data]

                # 创建饼图
                plt.figure(figsize=(8, 8))
                plt.pie(values, labels=labels, autopct='%1.1f%%', startangle=140,
                        colors=sns.color_palette('viridis', len(labels)))
                plt.title('服务器操作系统使用比例')  # 图表标题

                # 将图表保存到内存缓冲区
                img = io.BytesIO()
                plt.savefig(img, format='png')
                img.seek(0)

                self.send_response(200)
                self.send_header('Content-type', 'image/png')
                self.end_headers()
                self.wfile.write(img.getvalue())

            except Exception as e:
                self.send_response(400)
                self.send_header('Content-type', 'text/plain; charset=utf-8')
                self.end_headers()
                self.wfile.write(str(e).encode('utf-8'))

        else:
            self.send_response(404)
            self.send_header('Content-type', 'text/plain; charset=utf-8')
            self.end_headers()
            self.wfile.write("未找到".encode('utf-8'))


def register_service():
    """注册服务到 Nacos"""
    nacos_client.add_naming_instance(SERVICE_NAME, "127.0.0.1", SERVICE_PORT, healthy=True)
    print(f"服务已注册: {SERVICE_NAME} 端口 {SERVICE_PORT}")


def deregister_service():
    """从 Nacos 注销服务"""
    nacos_client.remove_naming_instance(SERVICE_NAME, "127.0.0.1", SERVICE_PORT)
    print(f"服务已注销: {SERVICE_NAME} 端口 {SERVICE_PORT}")


def run(server_class=HTTPServer, handler_class=SimpleHTTPRequestHandler, port=SERVICE_PORT):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f"启动 httpd 服务，端口 {port}...")
    try:
        register_service()  # 注册服务
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    finally:
        httpd.server_close()
        deregister_service()  # 注销服务


if __name__ == "__main__":
    run()



