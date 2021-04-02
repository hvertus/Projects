<!-- Header -->
<jsp:include page="header.jsp" />
	
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- 	Just some stuff you need -->
<header>
	<div class="container">
	  
		<c:choose>
		<c:when test="${not empty message }">
		  <p class="alert ${messageClass}">${message }</p>
		<%
		  session.setAttribute("message", null);
		  session.setAttribute("messageClass", null);
		%>
		</c:when>
		</c:choose>
	
		<h1>PUBHUB <small>Add tag</small></h1>		
		
		<form action="tag?option=tag" method="post" class="form-horizontal">
		  <div class="form-group">
		    <label for="isbn13" class="col-sm-4 control-label">Tag name</label>
		    <div class="col-sm-5">
		      <input type="text" class="form-control" id="tagName" name="tagName" placeholder="Enter tag name here" required="required" value="${param.tagName}" />
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-4 col-sm-1">
		      <button type="submit" class="btn btn-info">Add</button>
		    </div>
		  </div>		  
		</form>	
		
		<div class="form-group">
		<table class="table table-striped table-hover table-responsive pubhub-datatable" 
			style="width: 400px; text-align: left; margin-left: auto; margin-right: auto;">
			<thead>
				<tr>
					<td>#</td>
					<td>List of Tags</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tag" items="${tags}" varStatus="loopStatus">
					<tr>
						<td><c:out value="${loopStatus.index+1}" /></td>
						<td><c:out value="${tag.tagName}" /></td>
						<td>
							<form action="tag?option=remove&tagName=${tag.tagName}" method="post">	
								<button class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		</div>
		
	</div>
</header>

<!-- Footer -->
<jsp:include page="footer.jsp" />
	