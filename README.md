# Automatic-push-schedule-robot
这是一个使用SpringBoot + MyBatis-Plus 的自动推送课表的企业微信公众号项目


项目依赖MySql环境，推荐jdk1.8.0


在使用这个项目前，你需要前往企业微信注册一个企业号

注册地址：https://work.weixin.qq.com/wework_admin/register_wx?from=loginpage

注册完成后登录控制台

登录地址：https://work.weixin.qq.com/wework_admin/loginpage_wx?etype=expired#profile


1、进入控制台后选择应用管理

2、点击创建应用

3、上传应用头像、填写应用名称与介绍(选填)、选择应用可见范围(建议设为全部门可见)

4、点击创建应用后，复制保持应用的 AgentId (这需要用到)

5、点击 Secret 旁边的查看，点击发送

6、你需要下载企业微信获得应用的 Secret，在企业微信内查看后复制保存(这需要用到)

7、往下拉找到企业可信IP，点击配置，将你的本机IP配置进去，否则后续启动项目将会报错

8、返回控制台找到我的企业，复制保存企业Id(这需要用到)

到此，企业微信部分的配置就结束了，接下来配置数据库中的信息
