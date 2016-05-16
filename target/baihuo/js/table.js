(function(win) {
	win.Table = function() {
		this.pageNo = 0;
		this.totalPage = 0;
		this.setPageNoCallback = null;
		this.setPageTotalCallback = null;
		this.setQueryUrlCallback = null;
		this.callback = null;
	};

	function isNumber(obj) {
		return Object.prototype.toString.call(obj) == "[object Number]";
	}

	function show(msg) {
		alert(msg);
	}

	function showInfo(msg) {
		if (win.console) {
			win.console.info(msg);
		}
	}

	Table.prototype = {
		to : function(p) {
			if (!p)
				return;
			p = parseInt(p, 10);
			if (!p) {
				show("无效数字");
				return;
			}
			if (p > this.totalPage || p < 1) {
				return;
			}
			this.setPageNo(p);
			this.load();
		},
		first : function() {
			this.setPageNo(1);
			this.load();
		},
		pre : function() {
			if (this.pageNo <= 1)
				return;
			this.setPageNo(this.pageNo - 1);
			this.load();
		},
		next : function() {
			if (this.pageNo >= this.totalPage)
				return;
			this.setPageNo(this.pageNo + 1);
			this.load();
		},
		end : function() {
			this.setPageNo(this.totalPage);
			this.load();
		},
		load : function() {
			var url = this.setQueryUrlCallback();
			var _t = this;
			if (url != null && "" != url) {
				if (url.indexOf("?") !== -1) {
					url += "&pageNo=" + this.pageNo;
				} else {
					url += "?pageNo=" + this.pageNo;
				}
				var sortStr = this.getSortStr();
				if(!!sortStr){
					url+="&orderBy="+sortStr;
				}
				url = window.encodeURI(url);
				$.getJSON(url,{_t:new Date()*1}, function(json) {
					if (json == null)
						return;
					if (json.totalPage != null) {
						_t.setPageTotal(json.totalPage);
						_t.setPageNo(_t.pageNo);
						_t.callback(json);
					} else {
						if(console) console.error("返回的对象中没有pageTotal属性");
					}
				});
			}
		}
		,bind : function(setPageNoCallback, setPageTotalCallback,
				setQueryUrlCallback, callback) {
			this.setPageNoCallback = setPageNoCallback;
			this.setPageTotalCallback = setPageTotalCallback;
			this.setQueryUrlCallback = setQueryUrlCallback;
			this.callback = callback;
		},
		setPageNo : function(val) {
			this.pageNo = val || 1;
			if (!this.setPageNoCallback) {
				if (!!console)
					console.info("setPageNoCallback is null");
				return;
			}
			if(this.totalPage==0){
				val = 0;
			}
			this.setPageNoCallback.call(win, val);
		},
		setPageTotal : function(val) {
			this.totalPage = val || 0;
			if (!this.setPageTotalCallback) {
				if (!!console)
					console.info("setPageTotalCallback  is null");
				return;
			}
			this.setPageTotalCallback.call(win, val);
		}
		/**
		 * obj ={"sort_1": {tname:"app_create_time",order: -1}
				,"sort_2": {tname:"api_active_users",order: -1}
				,"sort_3": {tname:"api_new_users ",order: -1}
				,"sort_4": {tname:"sdk_active_users",order: -1}
				,"sort_5": {tname:"sdk_new_users ",order: -1}
			};
		 */
		,
		bindSort : function(obj) {
			sortMap = obj;
			for(var id in obj){
				$("#"+id).click(this,sortFun).css("cursor","pointer");
				var sort = sortMap[id];
				if(sort.order!==-1){
					setSortStatus(this,id,sort.order);
				}else{
					clearSortStatus(id);
				}
			}
		}
		,getSortStr:function(){
//			var sortStr = "";
			for(var id in sortMap){
				var sort = sortMap[id];
				if(sort.order!==-1){
//					sortStr+=","+sort.tname+":"+sort.order;
					return sort.tname+":"+sort.order;
				}
			}
//			if(sortStr.length>0){
//				return sortStr.substring(1);
//			}
			return "";
		}
	};
	
	var sortMap = {};
	
	function sortFun(e){
		var tid = this.id;
		for(var id in sortMap){
			if(id!=tid){
				clearSortStatus(id);
			}
		}
		changeSortStatus(e.data, tid);
	}
	
	function clearSortStatus(id){
		var sort = sortMap[id];
		sort.order = -1;
		setSortStyle(id,sort.order);
	}
	
	function changeSortStatus( tb , id){
		var sort = sortMap[id];
		if(sort.order===-1){
			sort.order = 0;
		}else{
			sort.order = sort.order==1?0:1;
		}
		setSortStatus(tb,id);
	}
	
	function setSortStatus( tb , id ){
		var sort = sortMap[id];
		setSortStyle(id,sort.order);
		orderBy = sort.tname+":"+sort.order;
		tb.first();
	}
	
	function setSortStyle(id,order){
		$("#"+id+">em").remove();
		var classs = order ==-1? "icon":order ==1 ? "icon_s1": "icon_s2";
		$("#"+id).append("<em class='"+classs+"'/>");
	}
	
	var tables = {};
	Table.getInst = function(key) {
		if (!tables[key]) {
			tables[key] = new Table();
		}
		return tables[key];
	};
	return Table;
})(window);

$T = Table.getInst("first");
