# Automatic-push-schedule-robot
这是一个使用SpringBoot + MyBatis-Plus 的自动推送课表的企业微信公众号项目<br>
项目依赖MySql环境，推荐jdk1.8.0<br><br>


在使用这个项目前，你需要前往企业微信注册一个企业号<br><br>
---------------------------------------

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

到此，企业微信部分的配置就结束了，接下来配置数据库中的信息<br><br>

数据库配置
---------------------------------------------------

1、在MySql中新建一个库，名称为enterprise-wx-push<br>
2、将Sql文件目录中的两个Sql脚本导入表中<br>
3、打开enterprise_data表<br>
4、将AgentId、Secret填入表中对应空缺处，表中第三个字段为注释，可以参照填入<br>
5、将企业ID填入corpId中<br>
6、打开course_info表<br>
7、参照数据库表注释修改课程表信息<br><br>

数据库表注释
-----------------------------------------------------

course_info表<br>
course_id为课程id，是每个课程的唯一标识<br>
course_name为课程名称，不同id的课程名称可以相同，用于区分统一课程的不同上课时间<br>
course_venue为上课地点，表示此课程的上课地点<br>
course_period为课程周期，表示此课程的上课周期，如1-18表示此课程是从第一周开始、第18周结束<br>
course_week为课程星期，表示此课程的上课星期，如1表示此课程是每周一上课<br>
course_section为课程节次，表示此课程是每天的第几大节课程，如1-2表示此课程是一上午的课，注意此处表示的是大节<br>
course_specialized表示是否为专业课程，用于计次本学期上过多少次专业课程，并在学期末统计回顾<br><br>
