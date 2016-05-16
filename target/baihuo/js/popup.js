jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)\./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
function myPopup(opts) {
	if (!(this instanceof myPopup)) {
		return new myPopup(opts);
	}
	this.elem=$(opts.elem);
	this.mask=$("<div id='mask'></div>");
	this.close=$(opts.close);
	this.srollFn=function(){};
	var _this=this;
	$(window).resize(function () {
		_this.maskFn();
		_this.setPos();
	});
	this.iframe=$("<iframe id='iframe'></iframe>");
	this.pup();
}
myPopup.prototype.pup= function() {
	var _this=this;
	_this.maskFn();
	_this.setPos();
	_this.close.bind("click",function(){
		_this.closeFn();
		$(this).unbind("click");
	});
};
myPopup.prototype.setPos=function(){
	var _this=this;
	var _width=_this.elem.width(), //获取弹出框宽度
		_height=_this.elem.height(),
		_top;
	if($.browser.msie && $.browser.version<7) {
		var _srcoll_top=$(window).scrollTop();
		$(window).bind("scroll", function() {
			_this.srollFn();
		});
		_this.srollFn=function(){
			_top=$(window).scrollTop()+($(window).height()-_height)/2;
			_this.elem.css("top",_top);
		};
		_this.elem.css("position","absolute");
		_top=_srcoll_top+($(window).height()-_height)/2;

	} else {
		_this.elem.css("position","fixed");
		_top=($(window).height()-_height)/2;
	}
	//垂直居中
	var _left=(($(window).width())-_width)/2;
	//设置水平
	_this.elem.css({
		"top":  _top,
		"left": _left,
		"margin":0,
		"z-index":100
	}).fadeIn(200);
};
myPopup.prototype.closeFn= function() {
	var _this=this;
	_this.elem.fadeOut(0);
	_this.mask.css("display","none");
	_this.iframe.css("display","none");
	$(window).unbind("scroll",_this.srollFn);
	$(window).unbind("resize");
};
myPopup.prototype.maskFn= function() {
	var _this=this;
	var mask_height=Math.max($("body").height(),$(window).height());
	var mask_width=$("body").width();
	if($.browser.msie && $.browser.version<7) {
		if(!$("#iframe")[0]){
			$("body").append(_this.iframe);
		}else{
			//alert(1)
			_this.iframe=$("#iframe");
		}
		_this.iframe.css({
			"width":  mask_width,
			"height": mask_height,
			"margin":0,
			"z-index":98,
			"opacity":0,
			"position": "absolute",
			"left": 0,
			"top":0,
			"display":"block"
		});
	}
//	console.log(!$("#mask")[0])
	if(!$("#mask")[0]){
		$("body").append(_this.mask);
	}else{
		_this.mask=$("#mask");
	}
	_this.mask.css({
		"height":mask_height,
		"width":mask_width,
		"display":"block",
		"backgroundColor": "#000000",
		"opacity":0.7,
		"position":"absolute",
		"left":0,
		"top":0,
		"z-index":99
	});
};
myPopup.prototype.srcoll= function(_srcoll_top) {
	var _this=this;
	var srcoll_top=$(window).scrollTop();
	if($.browser.msie && $.browser.version<7) {
		_this.elem.css("top",srcoll_top+_top-_srcoll_top);
	} else {
		_this.elem.css("top",_top);
	}
};