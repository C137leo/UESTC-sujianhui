# UESTC-sujianhui
宿建会物资管理系统

# 搭建宿建会物资管理网页遇到的问题和解决方法



https://blog.csdn.net/byteArr/article/details/80955703

https://blog.csdn.net/xiaojinsanMM/article/details/81099811

https://blog.csdn.net/HOWSUNSHINE/article/details/83988456

2019.11.13

bootstrap的引入路径必须注意，否则html页面解析不出来

开头的引入

![1572878526758](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1572878526758.png)

body标签尾部的引入，注意jquery放在bootstrap的前面

![1572878583760](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1572878583760.png)

2019.11.4

xml的sql语句返回值类型一定要和实体对象要求的一样，否则会报错

![1572878696463](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1572878696463.png)

![1572878813414](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1572878813414.png)

![1572878851933](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1572878851933.png)

2019.11.5

表单校验步骤

1.光标离开文本框开始校验

2.判断用户名是否为空，

​			为空--添加错误提示

​			不为空--添加正确提示

3.添加获取焦点，键盘输入事件

4.为表单添加一个submit事件

​	执行blur（）函数

​	获得错误信息长度>0阻止提交

2019.11.7

Jquery Validate（表单校验）

https://blog.csdn.net/s445320/article/details/50748975

https://blog.csdn.net/pengjunlee/article/details/80685500

https://www.cnblogs.com/shizhijie/p/9524136.html

```
<script src="../static/js/jquery-3.4.1.min.js"></script>
```

将jquery导入到项目中的路径。

![1573091881644](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1573091881644.png)

使用validation时，红框中的值必须和元素的name对应

![1573138482853](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1573138482853.png)

![1573138579216](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1573138579216.png)

和name属性值对应。

11/23 SpringSecurity对登录的校验应用。

很有营养价值的博客https://www.jianshu.com/p/c3b79a625d84

11/25 已经完成SpringSecurity对登录的校验应用。现在需要完成的功能有：

​		1.添加注册页面，主页，注册完成页面跳转到登录页面（注册页面有注册成为管理员的选项）。

​		2.登陆完成页面跳转到数据库物品表单页面（表单根据权限显示对应的按钮admin-有增删改查，批量删除）。

11/28

完成了jQuery.validate和SpringSecurty前端表单提交前的校验和对登录，注册的后端查找校验。

需要注意的点

错误示范：register.html服务器会报500错误

![1574948033763](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1574948033763.png)

正确情况：

![1574948091537](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1574948091537.png)

12/1

学习了使用session向前端传值

![1575135456976](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575135456976.png)

12/1 12:02

学习了pagehelper的集成

1.在pom.xml中下载最新的pagehelper依赖

```xml
 <!--pagehelper插件帮助分页-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.10</version>
        </dependency>
```

2.将service层中用到分页的部分（查询所有员工/商品等）返回类型改为PageInfo<>类型

```java
@Autowired
    MaterialsMapper materialsMapper;

    public PageInfo<MaterialsDTO> getAllMaterials(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<MaterialsDTO> materilasList=materialsMapper.getAllMaterials();
        PageInfo<MaterialsDTO> page = new PageInfo<MaterialsDTO>(materilasList);
        return page;
    }
```

3.在controller层中取到service的返回值，并用model返回给前端页面

学习了向前端发送json格式的对象，前端用result来进行接收/解析

```java
@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn,Model model) {
		//这不是一个分页查询
		//引入PageHelper分页插件
		//在查询之前只需要使用
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的查询就是一个分页查询
		List<Employee> emps=employeeService.getAll();
		//使用pageInfo包装查询后的结果，只需要将PageInfo交给页面就行了
		//封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageInfo",page);
	};
```

前端收到controller传来的Msg对象后（result即为服务器返回的json对象）

```javascript
function to_page(pn) {
			$.ajax({
				url : '${APP_PATH}/emps',
				data : 'pn=' + pn,
				type : 'get',
				//请求成功时执行该函数内容，result即为服务器返回的json对象
				success : function(result) {
					//console.log(result);
					//1.解析并显示员工信息
					//2.解析并显示分页信息
					build_emps_table(result);
					//解析并显示分页信息
					bulid_page_info(result);
					//解析并显示分页条
					build_page_nav(result);
				}
			});
		}

```

遇到的问题是：resultmap出现问题，服务器无法识别我的xml中的sql语句。

12/4 完成了pagehelper和前后端的整合，完成了模态框的jquery.validator的校验。

12/5 完成了模态框的表单修改，和按钮点击事件的绑定。遇到的问题：sql语句有问题，where id=#{id}出错。![1575557493682](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575557493682.png)

12/7

解决页面403错误的办法

https://stackoverflow.com/questions/23477344/put-csrf-into-headers-in-spring-4-0-3-spring-security-3-2-3-thymeleaf-2-1-2（springSecurity和springBoot结合后授权的问题）

完整实现了update物资信息的校验，及后台提交。（注意连表update的xml写法和validate的ajax提交方式）

12/8

实现了对多表的插入，（不规范不合格，一点一点插得）完成了物资新增的功能，（不是完整完成，jrs303校验提示出现错误，还需要继续修改。）

12/9

![](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575872004678.png)

这里的两个名称要对应否则会报400错误。

解决jrs303校验提示显示错误的问题，但是前端显示还存在一定的问题

![1575871984667](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575871984667.png)

12/9-22:49

解决了前端的显示问题，现在jquery.validate可以和jrs303结合正确显示前后端的校验结果了，自己真他妈菜。

至此，添加物资的功能全部完成（点击新增按钮的时候还是会有一些问题出现，input文本框会保持上一刻的span样式）

![1575904108344](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575904108344.png)

![1575904139212](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575904139212.png)

下面的工作是

1.增加删除功能（12/10日23：14刚刚完成）

2.增加用户的增删改查（由管理员进行）

3.对物资的修改和用户的增删改查都需要前后端的校验。

4.增加宿管会同学的身份（宿管会同学进来会看到不同的物资表）

5.增加花姐，婷姐，雨薇姐的身份验证。

6.增加值日表提示系统，定时发送短信

7.视频上传到首页功能（coincedance）

8.留言板。（卧槽，东西好多，需要一步一步慢慢来，但是一天也不能停下，总有人会成功的，为啥不能是我？）

12/29-23:15

总结今天：

![1575904799245](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575904799245.png)

根据模态框里的提交按钮被我们人为赋予的属性值来进行判断，如果该属性的值为“success”则将物品名称的输入框设置为验证正确的状态，否则就是不正确的状态。

![1575905181386](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575905181386.png)

1.位置保证了在点击保存新增物资的按钮时前端validate验证没通过表单不会被提交

2.位置保证了在进行后端验证不通过（提交按钮的人为添加属性为“error”时）表单不会被提交

![1575905393676](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575905393676.png)

后端校验的关键代码，

1.当物资名称的改变输入框（失去）焦点的话，会发送ajax请求去后端校验，

2.根据校验的结果对按钮增加相应的人为的属性。

明天来继续写。

![1575906445409](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575906445409.png)

2019-12/10

至此，物资的增删改查全部完成了！！！

技术要点：

1.完成check_box的勾选功能

![1575991931872](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575991931872.png)

![1575992011795](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575992011795.png)

```javascript
//完成全选全不选功能
    $("#check_all").click(function() {
        //attr获取checked是undefined；
        //我们这些dom原生的属性，attr获取自定义属性的值；
        //prop修改和读取dom原生属性的值
        //$(this).prop("checked");
        //下面这句话的意思是：所有check_item的值都是当前这个check选框的值
        $(".check_item").prop("checked", $(this).prop("checked"));
    });

```

点击表头的check_all确定勾选元素，点击了表头的check_all后，表单的每一行的check_item的prop.checked的值都跟$(this).prop("checked")保持一致。

![1575992416033](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575992416033.png)

2.单个删除和批量删除功能

![1575992874409](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575992874409.png)

图中的1.为批量2.为单个删除。

```javascript
//单个删除
    $(document).on("click", ".delete_btn", function() {
        //1.弹出是否确认删除对话框
        var name =$(this).parents("tr").find("td:eq(2)")
            .text();
        var id = $(this).attr("del-id");
        //alert($(this).parent("tr").find("td:eq(1)").text());
        if (confirm("确认删除【" + name + "】吗？")) {
            //确认，发送ajax请求即可
            $.ajax({
                url : "/deleteMaterials/" + id,
                type : "DELETE",
                success : function(result) {
                    alert(result.msg);
                    to_page(currentPage);
                }
            });
        }
    });
```

![1575993008635](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1575993008635.png)

```javascript
var name =$(this).parents("tr").find("td:eq(2)")
            .text();
```

表示的含义是寻找当前行的父<tr>的"td:eq(2)"（第三个<td>）删除。

12/12springSecurity不能使用<sec:authorize access=\"hasRole('ROLE_ADMIN')\">的问题

https://blog.csdn.net/qq_43268365/article/details/88344125

解决方法：页面<html> 标签中导入对应的命名空间

```javascript
<html xmlns:th="http://www.thymeleaf.org"
	  xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
```

pom.xml添加对应依赖

```xml
<!--springsecurity4 要指定3.0以上版本，否则权限标签可能无法工作-->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity4</artifactId>
    <version>3.0.4.RELEASE</version>
</dependency>
```

12/12

今天遇到两个奇怪的bug，一个是mybatis无法根据sql语句查询id

select* from materials where id=#{id}

一个是collection的cotains方法返回的是一个false

```java
Collection collection=SecurityContextHolder.getContext().getAuthentication().getAuthorities();System.out.println(collection.contains("ROLE_admin"));
```

不知道咋解决了。明天再看一天不行就问许大佬。

今天主要工作是增加了springSecurity的logout登出操作，这里有个值得注意的坑是在springsecurity中logout的网址必须是post请求的方式否则不得行，会报404错误。

然后就又有一个坑，<li><a></a></li>这个标签只是有href的属性，不能用post提交，，，就导致登出的按钮只能是以表单<form>的形式，会使界面很丑陋，但赖好实现了功能。

![1576164549645](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1576164549645.png)

明天的工作：实现页面根据不同的身份显示不同的内容这一功能。

12/13

今天继续解决页面显示的问题，解决了前两天的奇怪的bug，post请求时data哪里不要乱加东西。

12/14 解决了前端分身份显示增删改查按钮的问题

普通用户jh

![1576316893628](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1576316893628.png)

管理员用户la

![1576316926967](C:\Users\leo\AppData\Roaming\Typora\typora-user-images\1576316926967.png)

接下来的任务：

完成搜索的按钮！！17：50

搜索按钮：实现了搜索的跳转，写个方法完成传值吧！23：27.



12/15继续完成传值的方法17：49完成了传值的方法。

现在可以搜索了，至此所有的功能基本完成！。明天写开题，每天写博客写优化。今天痛快地玩了一下深深地感受到了努力工作后的快乐是多么的纯粹，经过这一段时间的工作和学习，我也认识到了很多，只要下定决心做一件事，每天都去作，总会有回报总会有收获的。而且变得更加出色不需要多久，一个月就足够了。
