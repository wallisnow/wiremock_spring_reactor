# Wiremock + Spring reactor的简单集成测试

# 1.为什么使用Wiremock
我们在开发过程中, 有时会碰到需要测试其他系统响应的情况, 例如你开发的系统需要调用某个外部API, 此时, 为了快速开发测试, 我们需要模拟一个外部系统的请求及响应的过程, 那么此时Wiremock 就是一种选择

# 2.应用场景
## 场景1.
*我们最常见的, 我们有时调用接口时需要获取认证中心提供的token再加入token到我们的请求中, 那么认证授权中心的服务在测试时我们可以用mock server来替代达到测试的效果.*
## 场景2.
*我们只对某请求结果二次封装, 内部实则是调用其他接口.*

# 3.例子
```
 stubFor(post(urlMatching("/remote/post"))
                .willReturn(aResponse()
                        .withBody(requestBody)
                        .withStatus(200)));
```
# 4.代码解析
- 测试类 : DummyControllerTest
- 介绍 : https://www.jianshu.com/p/71e4c0bf0129

# 5.Refs
- wiremock 官方文档: http://wiremock.org/docs/
- spring cloud: [https://cloud.spring.io/spring-cloud-contract/reference/html/project-features.html#features-wiremock](https://cloud.spring.io/spring-cloud-contract/reference/html/project-features.html#features-wiremock)
