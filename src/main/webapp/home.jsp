<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


 <script src="jquery/jquery.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.3.3/underscore-min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.9.2/backbone-min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/backbone-localstorage.js/1.1.7/backbone.localStorage.js" type="text/javascript"></script> 
<script type="text/javascript">
var User = Backbone.Model.extend({
	urlRoot:'rs/user'
});
var user = new User();
var userData={userName:'500059',password:'SARSWATHI NAGAR'};


$( document ).ready(function() {

	$("#save").on('click',function(){
		user.set("username",$("#username").val());
		user.set("password",$("#pass").val());
		user.save();
	});
	
});
</script>

</head>
<body>

User <input type="text" id ="username"/>
<br/>

Password <input type="password" id="pass"/>

<br/>
<button id="save">Save</button>

</body>
</html>