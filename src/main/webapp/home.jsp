<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


 <script src="jquery/jquery.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.3.3/underscore-min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.9.2/backbone-min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/backbone-localstorage.js/1.1.7/backbone.localStorage.js" type="text/javascript"></script> 

</head>
<body>
<script type="text/javascript">
var User = Backbone.Model.extend({
	urlRoot:'rs/user'
});
var user = new User();
var weatherreport={userName:'500059',
		password:'SARSWATHI NAGAR',
		state:'ANDHRA PRADESH',
		temperature:'32'
};
user.save(weatherreport, {
	success: function(user){
		console.log(user);
	}
});
</script>
</body>
</html>