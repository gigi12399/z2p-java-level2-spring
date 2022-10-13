$(document).ready(function(){
	changeDarkMode();
	$('a.log-out').click(function(event){
		event.preventDefault();
		$('form.logout-form').submit();
	});
	$(".fullscreen_button").on("click", function() {
			document.fullScreenElement && null !== document.fullScreenElement || !document.mozFullScreen && !document.webkitIsFullScreen ? document.documentElement.requestFullScreen ? document.documentElement.requestFullScreen() : document.documentElement.mozRequestFullScreen ? document.documentElement.mozRequestFullScreen() : document.documentElement.webkitRequestFullScreen && document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT) : document.cancelFullScreen ? document.cancelFullScreen() : document.mozCancelFullScreen ? document.mozCancelFullScreen() : document.webkitCancelFullScreen && document.webkitCancelFullScreen()
	});	
    $("a.darkmode_button").on("click", function(evt) {
		evt.preventDefault();
		let storageMode = localStorage.getItem("mode");
		let storageModeColor = JSON.parse(storageMode);
		console.log("Storage mode color => " + storageModeColor);
		if(storageModeColor != ("dark")){
			storageModeColor = "dark";
			localStorage.setItem("mode",JSON.stringify(storageModeColor));
		}else{
			storageModeColor = "light";
			localStorage.setItem("mode",JSON.stringify(storageModeColor));
		};
		changeDarkMode();
	});	
	
	
	
	function changeDarkMode(){
		let storageMode = localStorage.getItem("mode");
		let storageModeColor = JSON.parse(storageMode);
		if(storageModeColor == "dark"){
			$("div#content-wrapper").addClass("body-dark");
			$("nav.navbar").addClass("nav-dark");
			$("input.form-search").addClass("search-dark");
			$("ul.sidebar").addClass("sidebar-darkmode");
			$("div.card").addClass("card-dark");
			$("div.h5").addClass("card-test-dark");
			$("div.card-header").addClass("card-dark");
			$("footer").addClass("footer-dark");
			$("table td a.btn").addClass("button-dark");
			$("div.card-body a.btn").addClass("button-dark");
			$(".form-control-user").addClass("form-dark");
			$("div.bg-img").addClass("bg-img-dark");
			$(".btn-upload").addClass("button-dark");
		}
		else{
			$("div#content-wrapper").removeClass("body-dark");
			$("nav.navbar").removeClass("nav-dark");
			$("input.form-search").removeClass("search-dark");
			$("ul.sidebar").removeClass("sidebar-darkmode");
			$("div.card").removeClass("card-dark");
			$("div.h5").removeClass("card-test-dark");
			$("div.card-header").removeClass("card-dark");
			$("footer").removeClass("footer-dark");
			$("table td a.btn").removeClass("button-dark");
			$("div.card-body a.btn").removeClass("button-dark");
			$(".form-control-user").removeClass("form-dark");
			$(".btn-upload").removeClass("button-dark");
			$("div.bg-img").removeClass("bg-img-dark");
		}
	}
	
		/*$("div#content-wrapper").toggleClass("body-dark");
		$("nav.navbar").toggleClass("nav-dark");
		$("input.form-search").toggleClass("search-dark");
		$("ul.sidebar").toggleClass("sidebar-darkmode");
		$("div.card").toggleClass("card-dark");
		$("div.h5").toggleClass("card-test-dark");
		$("div.card-header").toggleClass("card-dark");
		$("footer").toggleClass("footer-dark");
		$("table td a.btn").toggleClass("button-dark");
		$("div.card-body a.btn").toggleClass("button-dark");
	//function applyTheme() {*/
2
   // let theme = matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
3
  //  document.documentElement.setAttribute("theme", theme);
4
	//}
5
6
	//matchMedia("(prefers-color-scheme: dark)").addEventListener("change", applyTheme);
7
	//applyTheme();		
	//});
});

    