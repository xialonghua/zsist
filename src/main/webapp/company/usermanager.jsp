<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>版本管理</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/datepicker3.css" rel="stylesheet">
<link href="css/bootstrap-table.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->
<script>
    function addVersion(modal, btn, close, x, version, desp, url, platform, table){
        btn.button('loading');
        close.prop('disabled', "true");
        x.prop('disabled', "true");

        $.ajax({
            type: 'GET',
            url: '../update/addVersion?version.version=' + version
            + '&version.channel=0'
            + '&version.platform=' + platform
            + '&version.isForce=0'
            + '&version.desp=' + desp
            + '&version.url=' + url,
            success: function (data) {
                btn.button('reset');
                close.prop('disabled', "");
                x.prop('disabled', "");
                modal.modal('hide')

                if (data.code == 200){
                    table.bootstrapTable('refresh');
                }
            },
        });

    }

    function handleUserData(res){
        var data = new Object();
        data.total = res.page.total;
        data.rows = res.data;
        return data;
    }
    function queryUsers(params) {

        return "page.page=" + params.offset / params.limit + "&page.pageSize=" + params.limit;
    }

    function operation(value,row,index){
        var a = "<Button class='btn btn-default' data-toggle='modal' data-target='#iosModal'>添加</Button>";
        var d = "<Button class='btn btn-default' onclick='javascript:deleteUser(" + row.id + ")'>删除</Button>";
        return a + '  ' + d;

    }

    function head(value,row,index){
        var a = "";
        if(!row.avatar){
            a = "<img src='../images/user.jpg' class='img-thumbnail' width='45px' height='45px'>";
        }else {
            a = "<img src='" + row.avatar + "' class='img-thumbnail' width='45px' height='45px'>";
        }
        return a;

    }

    function deleteUser(id){
        $.ajax({
            type: 'GET',
            url: '../ad/delVersion?version.id=' + id,
            success: function (data) {
                if (data.code == 200){
                    tables.bootstrapTable('refresh');
                }
            },
        });
    }

</script>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>Lumino</span>Admin</a>
				<ul class="user-menu">
					<li class="dropdown pull-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> User <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
							<li><a href="#"><span class="glyphicon glyphicon-cog"></span> Settings</a></li>
							<li><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
						</ul>
					</li>
				</ul>
			</div>

		</div><!-- /.container-fluid -->
	</nav>

	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>
		<ul class="nav menu">
			<li><a href="index.jsp"><span class="glyphicon glyphicon-dashboard"></span> Dashboard</a></li>
			<li><a href="version.jsp"><span class="glyphicon glyphicon-th"></span> 版本管理</a></li>
            <li class="active"><a href="usermanager.jsp"><span class="glyphicon glyphicon-dashboard"></span> 用户管理</a></li>
        </ul>
	</div><!--/.sidebar-->

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
				<li class="active">用户管理</li>
			</ol>
		</div><!--/.row-->

		<div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">用户列表</div>
                    <div class="panel-body">
                        <table id="androidTable" data-show-refresh="true" data-toggle="table" data-url="../user/getUsers" data-response-handler="handleUserData" data-query-params="queryUsers"
                               data-pagination="true" data-side-pagination="server" data-show-pagination-switch="false"
                               data-events="operationEvents"
                               data-unique-id="id">
                            <thead>
                            <tr>
                                <th data-field="id">id</th>
                                <th data-field="avatar" data-formatter="head">头像</th>
                                <th data-field="tel">电话</th>
                                <th data-field="username">帐号</th>
                                <th data-field="nickname">昵称</th>
                                <th data-field="level">账户类型</th>
                                <th data-field="companyName">公司名称</th>
                                <th data-field="companyTel">公司电话</th>

                                <th data-field="operation" data-formatter="operation">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
		</div><!--/.row-->

	</div>	<!--/.main-->


	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/chart.min.js"></script>
	<script src="js/chart-data.js"></script>
	<script src="js/easypiechart.js"></script>
	<script src="js/easypiechart-data.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
    <script src="js/bootstrap-table.js"></script>
	<script>

		!function ($) {
		    $(document).on("click","ul.nav li.parent > a > span.icon", function(){
		        $(this).find('em:first').toggleClass("glyphicon-minus");
		    });
		    $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
	</script>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="iosModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static"
         data-keyboard="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" id="iosX"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        添加IOS新版本
                    </h4>
                </div>
                <div class="modal-body">
                    <form role="form">
                        <div class="form-group">
                            <label for="version">版本号</label>
                            <input type="text" class="form-control" id="version"
                                   placeholder="请输入版本号">
                        </div>
                        <div class="form-group">
                            <label for="desp">更新描述</label>
                            <input type="text" class="form-control" id="desp"
                                   placeholder="请输入描述">
                        </div>
                        <div class="form-group">
                            <label for="desp">url</label>
                            <input type="text" class="form-control" id="url"
                                   placeholder="请输入下载地址">
                        </div>
                        <button type="button" class="btn btn-default" data-loading-text="loading..." onclick="javascript:addVersion($(iosModal), $(this), $('#iosClose'), $('#iosX'), $('#version').val(), $('#desp').val(), $('#url').val(), 0, $('#iosTable'))">提交</button>
                        <button type="button" class="btn btn-default" id="iosClose"
                                data-dismiss="modal">关闭
                        </button>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</body>

</html>
