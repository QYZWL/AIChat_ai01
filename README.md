# 🤖 基于LangChain4j的AI编程助手  


## 一、 项目介绍

### 定位
- 编程学习导师: 提供清晰的学习路线规划和个性化建议
- 求职面试助手: 涵盖简历优化、面试技巧、高频题目解析
- 代码答疑专家: 实时解答编程技术问题，提供代码示例

### 技术

> LangChain4j、SpringBoot、RAG、MCP、SSE、日志（监视器）、Guardrail、会话记忆、结构化输出

###功能

- **AI能力引擎**
  - 基于`LangChain4j`构建智能代理层，集成`通义千问大模型`提供核心推理能力。
  - 采用`结构化输出`控制生成内容格式，确保响应准确性与可用性。
- **知识增强与实时交互**
  - 通过`RAG`结合本地知识库，提供精准答案。
  - 支持`MCP协议`动态扩展外部工具与数据源，强化AI上下文理解能力。
- **用户体验优化**
  - 基于`SSE`实现流式响应，带来打字机式的实时交互体验。
  - 利用`会话记忆`维护上下文关联，支持多轮深度对话与长期学习跟踪。
- **安全与可靠性**
  - 使用`Guardrail`实现输入内容安全检测，过滤敏感与恶意请求。
  - 使用`监视器`实现模型应用的日志监控与异常告警，保障服务稳定性。



## 二、 项目启动

### 环境要求

- **Java**: JDK 21+
- **Node.js**: 16.0+
- **Maven**: 3.6+
- **通义千问API**: 需申请API密钥（本项目主要使用到qwen-plus与text-embedding-v4，来源于[阿里](https://bailian.console.aliyun.com/?spm=5176.29597918.J_SEsSjsNv72yRuRFS2VknO.2.bb307b08iVJKlr&tab=home#/home)）
- **Big Model API**: 需申请API密钥（用于MCP，来源于[智谱](https://www.bigmodel.cn/usercenter/proj-mgmt/apikeys)）

### 启动步骤

#### 1. 后端启动
```bash
# 配置API密钥
# 编辑 src/main/resources/application.yml
# 填入您的通义千问 API 和 Big Model API 密钥（即api-key: <Your API Key>处）

# 启动后端服务
mvn spring-boot:run
```

另一种方法是，启动/Debug `src/main/java/com/yutest/yu_ai01/YuAi01Application.java` 



#### 2. 前端启动

```bash
# 进入前端目录
cd ai-code-helper-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

#### 3. 访问应用
- 前端地址: `http://localhost:3000`
- 后端API: `http://localhost:8081/api`

> 注意端口的配置



### 效果展示

> 初始界面

![res1.0](D:\HuaweiMoveData\Users\HUAWEI\Desktop\Macheine Learning\Yupi\yu_ai01\res1.0.bmp)

> 提出问题

![res1.1](D:\HuaweiMoveData\Users\HUAWEI\Desktop\Macheine Learning\Yupi\yu_ai01\res1.1.bmp)



## 三、技术架构

```
┌─────────────────┐    ┌─────────────────┐
│   Vue.js 前端    │────│  Spring Boot   │
│   - 聊天界面     │    │    后端服务      │
│   - 实时流式     │    │   - RESTful API │
│   - Markdown    │    │   - SSE 推送     │
└─────────────────┘    └─────────────────┘
                              │
                    ┌─────────────────┐
                    │   LangChain4j   │
                    │   - AI服务层    │
                    │   - 工具集成    │
                    │   - 安全防护    │
                    └─────────────────┘
                              │
                    ┌─────────────────┐
                    │   通义千问API    │
                    │   - 对话模型    │
                    │   - 嵌入模型    │
                    │   - 流式输出    │
                    └─────────────────┘
```



## 四、核心模块

- `AICodeService`: 核心对话服务
- `ragConfig`: 检索增强配置（RAG）
- `mcpConfig`: 模型上下文协议（MCP）
- `guardrailSafe`: 输入安全防护（类拦截器）
- `qwenModelLog`: 模型配置管理（辅助监听器）
- `myModelLister`: 对话监听器（类日志）



## 五、项目需要注意的小问题

**1、模型依赖的版本**

a）确定模型依赖版本version的可用性 -> 官方文档查询（[LangChain4j官网](https://github.com/langchain4j/langchain4j)、[LangChain4j版本查看](https://mvnrepository.com/artifact/dev.langchain4j/langchain4j-community-dashscope-spring-boot-starter)）

b）旧版本的模型依赖方法可能不适用于新版本（如`HttpMcpTransport.Builder()`在1.4.0-beta10版本会不可用）

c）在`pom.xml`可通过maven中的刷新更新依赖

**2、快捷键引入**

注意使用快捷键所产生的import引用，同名可能会有不同效果（如：@value的value是 `import org.springframework.beans.factory.annotation.Value` 而非`lombok.value` ）

**3、单元测试与断点调试** 

每完成一个核心模块或功能点，必须同步编写对应的单元测试并断点调试，验证正常流程、边界情况及异常处理，确保代码在预期内运行，可以快速度发现逻辑错误和边界情况问题，降低后期修复成本 。

**4、Prompt的编写技巧**

- 提供明确的角色定位与任务
- 使用CoT方法，说明分析问题的步骤，引导模型思考推理
- ……(参考资料：[How-To-Ask-Questions-The-Smart-Way](https://github.com/ryanhanwu/How-To-Ask-Questions-The-Smart-Way/blob/main/README-zh_CN.md)、[提示工程指南](https://www.promptingguide.ai/zh))



## 🚩未来改进方向

目前这个版本作为第一个完整项目版本的留存记录，我还有许多想法并计划逐步实现应用：

- 更新前端界面，提升交互感、美观性（当前前端页面为Cursor生成，但目前Cursor的大模型存在地区限制，使用不便，考虑结合国内大模型进行微调修改）
- 增加支持手动停止AI回复的功能（前端发送停止命令，后端接收并中断SSE输出）
- 优化前端智能体输出内容，如：
- - 增加输出模型思考的过程（将模型思考内容作为日志打印，并通过SSE返回前端）
  - 优化输出内容的样式（增加关键词加粗、标题样式等）
- 实现前后端部署（将项目打包成Docker容器，上传至Serverless平台，如[微信云托管](https://cloud.weixin.qq.com/cloudrun)，实现在平台上启动与监控）
- - 注意DDoS攻击，避免个人信息的暴露



## 参考文件

- [LangChain4j](https://github.com/langchain4j/langchain4j) - 强大的AI应用开发框架
- [阿里云通义千问](https://dashscope.aliyun.com/) - 优秀的大语言模型
- [Spring Boot](https://spring.io/projects/spring-boot) - 简化的Java开发框架
- [Vue.js](https://vuejs.org/) - 渐进式JavaScript框架

