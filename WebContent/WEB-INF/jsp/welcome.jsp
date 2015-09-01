<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>Welcome User</title>
<link rel="stylesheet" href="css/bootstrap.min.css">

<script type="text/javascript" src="js/lib/jquery.js"></script>
<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>

</head>

<body>
	<nav class="navbar navbar-default navbar-static-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="navbar-brand">
					<a href="welcome" class="btn btn-primary btn-sm"><span
						class="glyphicon glyphicon-home"></span></a> 
						<span><strong>Popularity Prediction with Combined Social Network 
						Analysis for Sports Domain</strong></span>
				</div>
			</div>
		</div>
		<!--/.container-fluid -->
	</nav>

	<div class="container">

		<div class="row">
		
			<div class="col-md-4">
				<div class="list-group">
					<a href="#" class="list-group-item active"><span
						class="glyphicon glyphicon-flag" aria-hidden="true"></span> <strong>Team</strong>
					</a>
					
					<c:forEach items="${clusters}" var="cluster">
						<c:if test="${cluster.category == 'Team' && cluster.totalPs > 29}">
						<a href="#" class="list-group-item">
							<c:out value="${cluster.key}" />
							<span class="badge"><fmt:formatNumber 
							value="${cluster.totalPs}" type="number" pattern="#" /></span>
						</a>
						</c:if>
					</c:forEach>
					
				</div>
				
				<div class="panel panel-danger">
					<div class="panel-heading"><span
						class="glyphicon glyphicon-star" aria-hidden="true"></span> <strong>Popular</strong></div>
					<div class="panel-body">
					
						<p><c:out value="${team.data['data']}" /></p>
						<div>
							<button class="btn btn-default btn-sm" type="button">
							  Likes <span class="badge"><c:out value="${team.data['likes']}" /></span>
							</button>
							
							<button class="btn btn-default btn-sm" type="button">
							  Shares/Retweets <span class="badge"><c:out value="${team.data['spread']}" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-4">
				<div class="list-group">
					<a href="#" class="list-group-item active"><span
						class="glyphicon glyphicon-user" aria-hidden="true"></span> <strong>Player</strong>
					</a>
					
					<c:forEach items="${clusters}" var="cluster">
						<c:if test="${cluster.category == 'Player' && cluster.totalPs > 1}">
						<a href="#" class="list-group-item">
							<c:out value="${cluster.key}" />
							<span class="badge"><fmt:formatNumber 
							value="${cluster.totalPs}" type="number" pattern="#" /></span>
						</a>
						</c:if>
					</c:forEach>
					
				</div>
				
				<div class="panel panel-danger">
					<div class="panel-heading"><span
						class="glyphicon glyphicon-star" aria-hidden="true"></span> <strong>Popular</strong></div>
					<div class="panel-body">
					
						<p><c:out value="${player.data['data']}" /></p>
						<div>
							<button class="btn btn-default btn-sm" type="button">
							  Likes <span class="badge"><c:out value="${player.data['likes']}" /></span>
							</button>
							
							<button class="btn btn-default btn-sm" type="button">
							  Shares/Retweets <span class="badge"><c:out value="${player.data['spread']}" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-4">

				<div class="list-group">
					<a href="#" class="list-group-item active"><span
						class="glyphicon glyphicon-tower" aria-hidden="true"></span> <strong>Event</strong>
					</a>
					
					<c:forEach items="${clusters}" var="cluster">
						<c:if test="${cluster.category == 'Tournament' && cluster.totalPs > 0}">
						<a href="#" class="list-group-item">
							<c:out value="${cluster.key}" />
							<span class="badge"><fmt:formatNumber 
							value="${cluster.totalPs}" type="number" pattern="#" /></span>
						</a>
						</c:if>
					</c:forEach>
					
				</div>
				
				<div class="panel panel-danger">
					<div class="panel-heading"><span
						class="glyphicon glyphicon-star" aria-hidden="true"></span> <strong>Popular</strong></div>
					<div class="panel-body">
					
						<p><c:out value="${event.data['data']}" /></p>
						<div>
							<button class="btn btn-default btn-sm" type="button">
							  Likes <span class="badge"><c:out value="${event.data['likes']}" /></span>
							</button>
							
							<button class="btn btn-default btn-sm" type="button">
							  Shares/Retweets <span class="badge"><c:out value="${event.data['spread']}" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>