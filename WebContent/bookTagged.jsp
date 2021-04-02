<!-- Header -->
<jsp:include page="header.jsp" />
	
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- Just some stuff you need -->
<header>
	<script>	
		function setSelectedValue() {
		    var sel = document.getElementById('book');
		    var selectedBook = "${selectedBook}";
		    for(var i = 0; i < sel.options.length; i++) {
		        if(sel.options[i].value == selectedBook) {
		           sel.selectedIndex = i;
		           break;
		        }
		    }		    
		}
	</script>
	
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
		
		<h1>PUBHUB <small>Book Tagged</small></h1>
		<hr class="book-primary">
		
		<form action="BookTagged?option=search" method="post" class="form-horizontal">
		  <div class="form-group">
		    <label for="book" class="col-sm-4 control-label">Select a book</label>
		    <div class="col-sm-4">		      
		      <select id="book" name="book" required="required" class="form-control">
		      	 <option value="" />
		      	 <c:forEach var="book" items="${books}">
		      	 	<option value="${book.isbn13}">${book.description}</option>
		      	 </c:forEach>
		      </select>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-4 col-sm-1">
		      <button type="submit" class="btn btn-info" style="width: 200px;">Get tags</button>
		    </div>
		  </div>
		</form>

		  <div class="form-group">
			<table class="table table-striped table-hover table-responsive pubhub-datatable"
			   style="width: 400px; text-align: left; margin-left: auto; margin-right: auto;">
				<thead>
					<tr>
						<td>#</td>
						<td>Tags' list</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tag" items="${tags}" varStatus="loopStatus">
						<tr>
							<td><c:out value="${loopStatus.index+1}" /></td>														
							<td><c:out value="${tag.tagName}" /></td>
							<td><form action="BookTagged?tagName=${tag.tagName}" method="post">								
								<button class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>
							</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>		  
		  </div>
	</div>
</header>
<body onload="setSelectedValue()" />
<!-- Footer -->
<jsp:include page="footer.jsp" />