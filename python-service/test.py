from http.server import BaseHTTPRequestHandler, HTTPServer
import json
import threading
from nacos import NacosClient

# Nacos 配置
NACOS_SERVER = "127.0.0.1:8848"  # Nacos 服务地址
SERVICE_NAME = "python-service"  # 服务名称
SERVICE_PORT = 8086  # 服务端口

# 初始化 Nacos 客户端
nacos_client = NacosClient(NACOS_SERVER, namespace="public",username="nacos", password="nacos")

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
            self.wfile.write("Not Found".encode('utf-8'))

    def do_POST(self):
        if self.path == "/echo":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            try:
                data = json.loads(post_data)
                response = {"message": f"Received: {data}"}
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.end_headers()
                self.wfile.write(json.dumps(response).encode('utf-8'))
            except json.JSONDecodeError:
                self.send_response(400)
                self.send_header('Content-type', 'text/plain; charset=utf-8')
                self.end_headers()
                self.wfile.write("Invalid JSON".encode('utf-8'))
        else:
            self.send_response(404)
            self.send_header('Content-type', 'text/plain; charset=utf-8')
            self.end_headers()
            self.wfile.write("Not Found".encode('utf-8'))

def register_service():
    """注册服务到 Nacos"""
    nacos_client.add_naming_instance(SERVICE_NAME, "127.0.0.1", SERVICE_PORT, healthy=True)
    print(f"Service registered: {SERVICE_NAME} on port {SERVICE_PORT}")

def deregister_service():
    """从 Nacos 注销服务"""
    nacos_client.remove_naming_instance(SERVICE_NAME, "127.0.0.1", SERVICE_PORT)
    print(f"Service deregistered: {SERVICE_NAME} on port {SERVICE_PORT}")

def run(server_class=HTTPServer, handler_class=SimpleHTTPRequestHandler, port=SERVICE_PORT):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f"Starting httpd server on port {port}...")
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