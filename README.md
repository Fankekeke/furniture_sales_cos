### 基于SpringBoot + Vue的家具销售平台.

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok

##### 管理员： 
用户收货地址，公告管理，家具管理，家具类型，订单评价，积分兑换，物品积分，会员积分，商家管理，商家会员，订单管理，员工管理，用户管理，帖子审核，数据统计

##### 商家： 
数据统计，家具管理，订单评价，订单管理，员工管理，商家会员，商家信息管理

##### 用户：
用户注册，个人信息修改，收货地址，订单下单，订单评价，商品收藏，帖子发布与评论，论坛发帖，支付为支付宝沙盒支付


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

[商家管理员]
shangjia
1234qwer

[用户]
fank
1234qwer

#### 项目截图

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153101917.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153580458.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725154173768.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153562930.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153939901.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153548220.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153905308.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153504617.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153879407.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153468723.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153810745.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153450030.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153782847.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153434742.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153747060.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153415062.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153672982.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153404097.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153652662.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153362753.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153632512.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153310432.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725153603322.png) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解`

#### 其它资源

[2024年答辩顺利通过](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年答辩顺利通过](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年答辩通过率100%](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)
