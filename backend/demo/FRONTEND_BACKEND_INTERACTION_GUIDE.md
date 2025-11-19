# 前后端交互思路与规范教学文档

> 文件位置：`backend/demo/FRONTEND_BACKEND_INTERACTION_GUIDE.md`

## 目标读者

- 前端开发初学者、需要与后端协作的前端工程师、或者希望理解后端 API 设计与消费方式的同学。

## 文档目的

- 讲清楚前后端如何约定契约（Contract）、如何设计稳定易用的 API、如何处理错误与鉴权、如何在开发时快速联调与测试。

---

## 1. 概念梳理（为什么要规范）

- 前后端契约（API contract）是双方沟通的“合同”。清晰的契约降低返工和接口误用的概率。
- 统一响应格式、状态码、错误码、分页格式可以让前端处理更简单、更统一。
- 好的开发流程包含：设计 → Mock → 实现 → 联调 → 自动化测试 → 上线监控。

---

## 2. API 设计原则（通用）

- 使用 RESTful 风格：资源（users, items）使用名词；HTTP 动作表示操作（GET/POST/PUT/DELETE）。
- 一致性：相同类型的接口（分页、创建、更新）应返回相同结构。
- 明确版本管理：路径中包含版本号 `/api/v1/...`，方便升级与回滚。
- 使用语义化状态码：200/201/204/400/401/403/404/500 等，并通过响应体给出更详细的信息。
- 易于测试与调试：添加可选的 request-id / correlation-id 用于链路追踪。

---

## 3. 响应结构规范（示例）

后端项目中使用了 `ApiResponse<T>`（示例），建议统一如下结构：

```json
{
  "success": true,
  "message": "操作成功",
  "data": { ... },
  "errorCode": null,
  "timestamp": 1700000000000
}
```

- `success`：布尔，快速判断是否成功。
- `message`：友好提示（可直接展示在 UI）。
- `data`：业务数据（成功时）。
- `errorCode`：标准错误码（失败时用于前端逻辑判断）。
- `timestamp`：时间戳（可选）。

错误返回示例：

```json
{
  "success": false,
  "message": "手机号已被注册",
  "data": null,
  "errorCode": "USER_PHONE_TAKEN",
  "timestamp": 1700000000000
}
```

前端消费时：先判断 `success` 或 HTTP 状态码，再读取 `data`。

---

## 4. 请求参数与 DTO 约定

- 后端应在 API 文档中明确请求体（JSON）字段及其类型、必填项、格式（手机号、email、日期格式）。
- 使用简单、明确的字段名：`phoneNumber`, `password`, `name`。
- 对于复杂字段使用嵌套对象，但尽量不要随意变更字段名，变更需版本化或兼容处理。

示例：`RegisterRequest` 约定：

```json
{
  "name": "张三",
  "phoneNumber": "13800001111",
  "password": "secret"
}
```

---

## 5. 验证与错误处理

- 后端使用 Bean Validation（如 `@NotBlank`, `@Size`, `@Email`）来做入参校验。校验失败返回 400 并给出字段级错误信息。
- 前端在发送请求前应做基础校验（非空、格式），以减少请求次数并提升 UX。
- 统一错误码列表（后端维护），前端根据 `errorCode` 做特定提示或引导（例如 `USER_NOT_FOUND`、`INVALID_CREDENTIALS`）。

---

## 6. 认证与授权（短文）

- 推荐使用 JWT（无状态）或 Oauth2/JWT（更复杂场景）。
- 登录后后端返回 token，前端将 token 存到内存或 `httpOnly` cookie（更安全）。
- 每次受保护请求在 header 中加入 `Authorization: Bearer <token>`。
- 后端在过滤器/拦截器中验证 token 并把用户信息放到安全上下文中。

示例（axios 配置）：

```javascript
// src/api/axiosInstance.js
import axios from "axios";
const api = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "application/json" },
});
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});
export default api;
```

注意：不要把敏感信息放在 localStorage（XSS 风险），生产环境推荐 `httpOnly` cookie。

---

## 7. CORS 与开发环境代理

- 如果前后端在不同域名/端口，后端需配置 CORS（允许前端 origin 与方法），或在开发时使用前端 dev server 的代理。
- Vite 开发代理示例（`vite.config.ts` 或 `vite.config.js`）：

```js
export default defineConfig({
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "/api"),
      },
    },
  },
});
```

---

## 8. 本地联调流程（建议）

1. 后端运行：`cd backend/demo` → `./mvnw.cmd spring-boot:run` 或在 IDE 中运行 `DemoApplication`。
2. 前端运行：`cd frontend/demo-frontend` → `npm install` → `npm run dev`（确保 Vite proxy 指向后端）。
3. 使用 `BackendTestView` 或 Postman/RestClient 调试接口。
4. 观察后端日志与前端控制台的请求/响应信息。

---

## 9. 接口契约示例（基于代码中的 `UserController`）

下面给出前端常用调用示例（使用 `backendTestApi` 中的方法）：

- 注册：
  - URL: `POST /api/users/register`
  - Body: `RegisterRequest`（见上）
  - 前端调用：

```js
await backendTestApi.register({
  name: "张三",
  phoneNumber: "13800001111",
  password: "pwd",
});
```

- 登录：

  - URL: `POST /api/users/login`
  - Body: `LoginRequest`
  - 返回：`ApiResponse<UserResponse>`，`data` 包含用户信息（可改为返回 token）

- 查询用户：

  - URL: `GET /api/users/{id}`
  - 前端调用： `backendTestApi.getUser(1)`

- 更新用户：

  - URL: `PUT /api/users/{id}`
  - Body: `UpdateUserRequest`

- 检查手机号：
  - URL: `GET /api/users/check-phone?phoneNumber=138...`
  - 返回：`ApiResponse<Boolean>`

前端根据 `ApiResponse.success` 或 HTTP 状态码判断并在 UI 层显示 `message`。

---

## 10. 版本控制与接口变更策略

- 新增字段：尽量向后兼容（旧字段保持有意义），并在文档中注明可选性。
- 删除/重命名字段：应创建新版本 `/api/v2/...` 并同时维护 v1 一段时间。
- 大改动前建议通过 feature flags、灰度发布或逐步迁移。

---

## 11. 测试与 Mock

- 前端可在联调前使用 Mock Server（例如 `msw`、json-server）来模拟后端行为。
- 后端提供 Swagger/OpenAPI 规范，前端可以根据 OpenAPI 生成请求模型或 Mock。
- 推荐在前端实现单元测试（组件、service）的同时对 API 调用部分写集成测试或使用契约测试（Pact）保证兼容性。

---

## 12. 性能与分页

- 对于列表接口，统一采用分页参数 `page`、`size` 或 `offset`/`limit`，并返回分页元数据：`total, page, size`。
- 返回数据量大时考虑后端分页、筛选与排序接口。

分页返回示例：

```json
{
  "success": true,
  "data": {
    "items": [ ... ],
    "page": 1,
    "size": 20,
    "total": 234
  }
}
```

---

## 13. 日志、监控与错误上报

- 后端应记录请求 ID、用户 ID、耗时、异常堆栈（敏感信息脱敏）。
- 前端应在关键错误上传过程中附上用户行为快照与 request-id，便于后端排查。
- 生产环境推荐接入 APM（如 Elastic APM、SkyWalking）与异常上报（Sentry）。

---

## 14. 安全注意事项（高优先级）

- 输入校验与输出编码，防止 SQL 注入 / XSS / CSRF。
- 密码永远后端加密（bcrypt 或更强算法），前端不保存明文。
- 对敏感操作二次确认或 MFA。

---

## 15. 示例清单（供前端开发者检查）

- [ ] 是否有 API 文档（Swagger/OpenAPI）？
- [ ] 是否统一响应结构并有错误码表？
- [ ] 开发环境是否配置代理（Vite）？
- [ ] 登录后是否有 token 方案？是否给出 token 刷新策略？
- [ ] 是否提供 mock 数据或 Mock Server？
- [ ] 是否明确分页、排序、筛选参数？
- [ ] 是否提供范例请求与响应？

---

## 16. 快速实操示例（axios）

```javascript
// src/api/backendTestApi.js
import axios from "axios";
const api = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "application/json" },
});

export async function register(payload) {
  const res = await api.post("/users/register", payload);
  return res.data;
}
```

在组件中：

```js
import { register } from "../api/backendTestApi";
async function onSubmit() {
  const resp = await register({ name, phoneNumber, password });
  if (resp.success) {
    // 处理成功
  } else {
    // 显示 resp.message
  }
}
```

---

## 17. 总结（给初学者的建议）

- 先约定契约：字段名、响应结构、错误码、鉴权方式。
- 在前后端都做基础校验，提升 UX 并减少无效请求。
- 使用工具（Postman / Swagger / Mock Server）在实现前完成联调准备。
- 写自动化测试（后端集成测试，前端单元/集成测试），并在 CI 中运行。
- 记录常见问题与约定，形成团队内的 API 规范文档。

---

## 参考资料

- Spring 官方文档：https://spring.io/docs
- OpenAPI / Swagger 文档规范
- Axios 文档：https://axios-http.com/

---

如需我，我可以：

- 把此文档生成为 README、或在 `frontend/demo-frontend` 下生成 `API_USAGE.md`；
- 根据 `UserController` 自动生成 Postman 集合或 OpenAPI 草案；
- 为 `vite.config` 添加代理配置示例文件。

告诉我你要我接着做哪一步。
