$(document).ready(function() {
	
	
	
	$.ajax({
		url : "http://localhost:8080/blog/settings",
		type : 'GET',
		dataType : 'json', // added data type
		success : function(res) {
			console.log(res.color.substring(7,0));
			color = res.color.substring(7,0);
			
			document.title = res.name;
			
			$('.sidebar-brand-text').empty()
			$('.sidebar-brand-text').append(res.name)
			
			$('.bg-gradient-primary').css('background-color', color);
			$('.bg-gradient-primary').css('background-image', 'linear-gradient(180deg,'+color+' 10%,'+color+' 100%)');
			$('.bg-gradient-primary').load('.bg-gradient-primary')
			
			$('.text-primary').css('color', color+'!important');
			$('.text-primary').load('.text-primary')
			

		}
	});
	
	
});