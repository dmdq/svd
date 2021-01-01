1.配置/启动
	配置文件 src/main/resources/application.properties
	主运行类 com.company.myapp.Application

2.初始化测试数据
	访问：http://127.0.0.1:8888/demo

3.测试网络连接
	telnet 127.0.0.1 7777
	{"event":"login","source":{"uid":1,"token":"token","from":"pc"}}

4.发送消息API
	系统消息（全服推送）：http://127.0.0.1:8888/sendmsg/?value=Happy New Year 2021!
	用户消息（定向推送）：http://127.0.0.1:8888/senduser/?uid=1&value=Link, Happy birthday!	
