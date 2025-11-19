# 项目结构与学习指南（面向初学者）

> 文件位置：`backend/demo/PROJECT_STRUCTURE_AND_GUIDE.md`

## 一、项目概览

- 项目类型：Spring Boot 后端应用（Maven 构建）
- 入口：`src/main/java/com/zhixun/demo/DemoApplication.java`
- 构建工具：Maven（含 `mvnw` / `mvnw.cmd`）
- 配置文件：`src/main/resources/application.properties`

父 POM 与关键配置（来自 `pom.xml`）：

- Spring Boot 父 POM：`org.springframework.boot:spring-boot-starter-parent:3.5.7`
- Java 版本：`<java.version>21</java.version>`（项目已配置为使用 Java 21）
- 编译插件：`maven-compiler-plugin:3.11.0`，配置 `<release>21</release>`

主要依赖（从 `pom.xml` 提取）：

- `spring-boot-starter-web`（Web / REST）
- `spring-boot-starter-actuator`（监控）
- `spring-boot-starter-data-jpa`（JPA / 数据持久化）
- `spring-boot-starter-validation`（验证）
- `spring-boot-starter-security`（安全）
- `spring-boot-devtools`（开发热重载，runtime）
- `org.projectlombok:lombok`（样板代码简化）
- 数据库驱动：`com.h2database:h2`（开发），`com.mysql:mysql-connector-j`（生产）
- 测试：`spring-boot-starter-test`, `junit-jupiter-*`

## 二、目录结构（重要位置）

- `src/main/java/com/zhixun/demo/`
  - `DemoApplication.java`：应用启动类
  - `aspect/`：AOP 切面（`LoggingAspect.java`, `PerformanceAspect.java` 等）
  - `config/`：配置类（`SecurityConfig.java`, `DatabaseConfig.java`, `CorsConfig.java`, `WebConfig.java`）
  - `controller/`：控制器（如 `UserController.java`）
  - `dto/request`、`dto/response`：请求/响应对象
  - `Entity/`：JPA 实体（`User.java`, `Item.java`, ...）
  - `exception/`：自定义异常与全局异常处理器
  - `Repository/`：数据访问接口（Spring Data JPA）
  - `Service/`：服务层与 `impl/` 实现
- `src/main/resources/`：`application.properties`, `templates/`, `static/`
- `src/test/java/`：测试代码

## 三、MVC 分层详解（面向初学者）

下面以最常见的 Controller → Service → Repository 路径为例，逐层解释每层的职责、示例代码与常见注解。

### 1) Model（实体 / DTO）

- 位置：`Entity/`（实体），`dto/`（数据传输对象）
- 作用：定义数据结构。实体用于 ORM（JPA 映射数据库表），DTO 用于接口传输。
- 示例（实体）：

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // getters / setters
}
```

- 示例（请求 DTO）：

```java
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // getters / setters
}
```

### 2) Controller（控制器）

- 位置：`controller/`
- 作用：接收 HTTP 请求，转换参数，调用 Service 层，返回响应。
- 常用注解：`@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`。
- 示例：

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest req) {
        String token = userService.login(req);
        return ResponseEntity.ok(token);
    }
}
```

说明：Controller 不直接处理数据库或复杂业务；它负责请求/响应处理和简单校验。

### 3) Service（服务层）

- 位置：`Service/` 与 `Service/impl/`
- 作用：封装业务逻辑、事务管理、多个 Repository 协作。
- 常用注解：`@Service`, `@Transactional`（需要事务时）
- 示例：

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByUsername(req.getUsername());
        // 验证密码、生成 token 等业务逻辑
        return "token-sample";
    }
}
```

### 4) Repository（持久层）

- 位置：`Repository/`
- 作用：与数据库交互。使用 Spring Data JPA 可声明接口继承 `JpaRepository`。
- 示例：

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

说明：你通常不需要自己实现接口，Spring Data 会在运行时生成实现。

### 5) 配置、异常、切面

- `config/`：放置 Spring 配置类（例如安全配置 `SecurityConfig`、跨域 `CorsConfig`、数据源配置等）。
- `exception/`：全局异常处理器（`@ControllerAdvice` + `@ExceptionHandler`）。
- `aspect/`：切面用于日志、性能统计、方法验证等横切逻辑（`@Aspect`）。

## 四、典型请求处理流程（顺序）

1. 客户端发起 HTTP 请求 → DispatcherServlet 接收
2. 匹配到 Controller 和处理方法
3. 参数绑定（JSON → DTO）
4. Controller 调用 Service
5. Service 使用 Repository 查询/更新数据库
6. Repository 与数据库交互（JPA）
7. 返回结果 → Controller → HTTP 响应给客户端
8. 出现异常时由 `GlobalExceptionHandler` 统一处理

## 五、关键注解速查（常见且重要）

- `@SpringBootApplication`：主类，包含组件扫描和自动配置
- `@RestController`：REST 控制器，自动返回 JSON
- `@Controller`：MVC 控制器，配合视图模板
- `@Service`：标识服务类
- `@Repository`：标识数据访问类（异常翻译）
- `@Entity`：JPA 实体
- `@Id`, `@GeneratedValue`：主键及主键生成策略
- `@Autowired`：自动注入（推荐使用构造器注入替代字段注入）
- `@Transactional`：事务边界
- `@ControllerAdvice` / `@ExceptionHandler`：全局异常处理
- `@Aspect`：AOP 切面
- `@Valid` / `@NotBlank` 等：Bean Validation 校验

## 六、给初学者的学习要点（按你的要求列出）

- Java 基础

  - 类与对象：类定义、构造器、实例方法/字段、静态成员
  - 包：`package` 声明、`import` 使用、类路径组织
  - 异常：检查型异常与运行时异常、`try-catch-finally`、自定义异常
  - 集合：`List`, `Set`, `Map` 的典型用法和区别
  - 泛型：类型参数、通配符、泛型方法

- 基础 Spring

  - 控制器（Spring MVC）：`@Controller` / `@RestController`、路径映射、请求参数绑定
  - 依赖注入（DI）：`@Component`/`@Service`/`@Repository`、构造器注入 vs 字段注入
  - Spring Boot 入门：`@SpringBootApplication`、自动配置、使用 `application.properties`

- 数据持久化

  - JPA 基础：`@Entity`, `@Table`, 字段映射
  - 实体关系：`@OneToMany`, `@ManyToOne`, `@JoinColumn` 等
  - Repository 使用：继承 `JpaRepository`、方法命名查询、`@Query` 自定义

- 实战练习

  - 运行项目：使用 `mvnw.cmd -DskipTests package` 构建；用 IDE 或 `java -jar` 运行
  - 写一个简单的 REST 接口：创建 Controller、Service、Repository、Entity、DTO
  - 写单元测试：使用 JUnit（`@SpringBootTest` / Mockito）测试 Service 或 Controller

- 推荐资源（中文/英文）
  - 廖雪峰 Java 教程（中文）
  - Spring 官方文档（https://spring.io/docs）
  - Baeldung Spring / Spring Boot 教程（https://www.baeldung.com）

## 七、实操命令（Windows `cmd.exe`）

在 `backend\demo` 目录下运行：

```bat
REM 查看 Java 版本
java -version

REM 使用 Maven wrapper 构建（跳过测试以加快）
mvnw.cmd -v
mvnw.cmd -DskipTests package

REM 运行测试
mvnw.cmd test

REM 运行应用（如果生成了可运行 jar）
java -jar target\demo-0.0.1-SNAPSHOT.jar
```

## 八、常见小问题与解答（FAQ）

- 为什么推荐构造器注入？

  - 更易于单元测试，避免空指针问题，可明确依赖关系，遵循不可变模式。

- Controller 可以直接注入 Repository 吗？
  - 可以，但不推荐。推荐保持 Controller 只负责请求/响应，业务逻辑放到 Service 层。

## 九、下一步建议（可选）

- 我可以为你：
  - 生成一份 PlantUML 类图或时序图，帮助理解类之间的交互；
  - 在项目中添加一个入门示例（`HelloController` + `HelloService` + `HelloRepository` + 单元测试）并运行构建；
  - 运行一次本地构建并把结果发给你（需要允许我执行构建命令或你在本地运行后反馈）。

如果你要我继续，请选择其中一项或告诉我你更想先学的内容（例如依赖注入或 JPA 实体关系）。
