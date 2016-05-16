<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
$(document).ready(function() {

	var $image = $('#image');
	var cropBoxData;
	var canvasData;

	$('#modal').on('shown.bs.modal', function () {
		$image.cropper({
			autoCropArea: 0.5,
			built: function () {
				$image.cropper('setCanvasData', canvasData);
				$image.cropper('setCropBoxData', cropBoxData);
			}
		});
	}).on('hidden.bs.modal', function () {
//		cropBoxData = $image.cropper('getCropBoxData');
//		canvasData = $image.cropper('getCanvasData');
//		$image.cropper('destroy');
                                var imgsrc = $("#cropPic").prop('src');
                        if (imgsrc.indexOf("base64,") < 0) {
                            return;
                        }
                        $("#banner_avatar").prop("src", imgsrc);
                        $("#cropPic").prop('src', '');
                        imgsrc = imgsrc.substr(imgsrc.indexOf("base64,") + 7);
                        JiaFang.uploadToQiNiu(imgsrc, function (pic) {
                            $("#picUrlKey_avatar").val(pic.url);
                        });
	});

});
</script>
<!-- Modal -->
<div class="modal fade" id="modal" aria-labelledby="modalLabel" role="dialog" tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="modalLabel">裁剪图片</h4>
			</div>
			<div class="modal-body">
				<!-- <h3 class="page-header">Demo:</h3> -->
				<div class="img-container">
					<img id="image" src="" alt="Picture">
				</div>
				<label class="btn btn-primary btn-upload" for="inputImage" title="打开图片">
					<input type="file" class="sr-only" id="inputImage" name="file" accept="image/*">
            <span class="docs-tooltip" data-toggle="tooltip" title="打开图片">
              <span class="fa fa-upload">打开图片</span>
            </span>
				</label>

			</div>
			<div class="modal-footer">

                <div class="docs-buttons">
                    <div class="btn-group">

                        <button type="button" class="btn btn-primary" data-method="rotate" data-option="-45"
                                title="Rotate Left">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;rotate&quot;, -45)">
              <span class="fa fa-rotate-left">向左转45度</span>
            </span>
                        </button>
                        <button type="button" class="btn btn-primary" data-method="rotate" data-option="45"
                                title="Rotate Right">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;rotate&quot;, 45)">
              <span class="fa fa-rotate-right">向右转45度</span>
            </span>
                        </button>

                    </div>
                    <div class="btn-group">

                        <button type="button" class="btn btn-primary" data-method="zoom" data-option="0.1"
                                title="Zoom In">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;zoom&quot;, 0.1)">
              <span class="fa fa-search-plus">放大</span>
            </span>
                        </button>
                        <button type="button" class="btn btn-primary" data-method="zoom" data-option="-0.1"
                                title="Zoom Out">
            <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;zoom&quot;, -0.1)">
              <span class="fa fa-search-minus">缩小</span>
            </span>
                        </button>


                    </div>

                    <div class="btn-group">

                        <button type="button" class="btn btn-primary" data-method="setAspectRatio"
                                data-option="1.7777777777777777" title="aspectRatio: 16 / 9">
            <span class="docs-tooltip" data-toggle="tooltip" title="16:9">
              <span class="fa fa-search-plus">16:9</span>
     </span>
                        </button>

                        <button type="button" class="btn btn-primary" data-method="setAspectRatio"
                                data-option="1.3333333333333333" title="aspectRatio: 4 / 3">
            <span class="docs-tooltip" data-toggle="tooltip" title="Free">
              <span class="fa fa-search-plus">4:3</span>
     </span>
                        </button>
                        </label>
                        <button type="button" class="btn btn-primary" data-method="setAspectRatio" data-option="1"
                                title="aspectRatio: 1 / 1">
            <span class="docs-tooltip" data-toggle="tooltip" title="1:1">
              <span class="fa fa-search-plus">1:1</span>
     </span>
                        </button>
                        <button type="button" class="btn btn-primary" data-method="setAspectRatio"
                                data-option="0.6666666666666666" title="aspectRatio: 2 / 3">
            <span class="docs-tooltip" data-toggle="tooltip" title="2:3">
              <span class="fa fa-search-plus">2:3</span>
     </span>
                        </button>
                        <button type="button" class="btn btn-primary" data-method="setAspectRatio" data-option="NaN"
                                title="Free">
            <span class="docs-tooltip" data-toggle="tooltip" title="Free">
              <span class="fa fa-search-plus">自由</span>
     </span>
                        </button>
                    </div>
                </div>









                <div class="docs-buttons">
                    <div class="btn-group">

                        <button type="button" class="btn btn-primary" data-method="getCroppedCanvas"
                                title="裁剪" data-dismiss="modal">
            <span class="docs-tooltip" data-toggle="tooltip" title="裁剪">
              <span class="fa fa-search-minus">裁剪</span>
            </span>
                        </button>

                        <button type="button" class="btn btn-primary" data-method="getCroppedCanvas" data-option="{ &quot;width&quot;: 832, &quot;height&quot;: 468 }"
                                title="832*468 裁剪" data-dismiss="modal">
            <span class="docs-tooltip" data-toggle="tooltip" title="832*468 裁剪">
              <span class="fa fa-search-minus">832*468 裁剪</span>
            </span>
                        </button>


                    </div>
                </div>

                <%--<button type="button" class="btn btn-default" data-dismiss="modal">裁剪</button>--%>

				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
</div>
<div id="viewDiv" style="display: none">
    <img id="cropPic">
</div>

