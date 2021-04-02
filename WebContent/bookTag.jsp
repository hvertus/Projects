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
		    var sel = document.getElementById('tag');
		    var selectedTag = "${selectedTag}";
		    for(var i = 0; i < sel.options.length; i++) {
		        if(sel.options[i].value == selectedTag) {
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
		
		<h1>PUBHUB <small>List of Books for a given Tag</small></h1>
		<hr class="book-primary">
		
		<form action="bookTag?option=search" method="post" class="form-horizontal">
		  <div class="form-group">
		    <label for="tag" class="col-sm-4 control-label">Select a tag</label>
		    <div class="col-sm-4">		      
		      <select id="tag" name="tag" required="required" class="form-control">
		      	 <option value="" />
		      	 <c:forEach var="tag" items="${tags}">
		      	 	<option value="${tag.tagName}">${tag.tagName}</option>
		      	 </c:forEach>
		      </select>
		    </div>
		  </div>  
		  		  
		  <div class="form-group">
		    <div class="col-sm-offset-4 col-sm-1">
		      <button type="submit" class="btn btn-info" style="width: 200px;">Get books</button>
		    </div>
		  </div>
		</form>

		  <div class="form-group">
			<table class="table table-striped table-hover table-responsive"
			   style="width: 75%; text-align: left; margin-left: auto; margin-right: auto;">
				<thead>
					<tr>
						<td>#</td>
						<td>ISBN</td>
						<td>Title</td>
						<td>Author</td>
						<td>Publish date</td>
						<td>Price</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="book" items="${books}" varStatus="loopStatus">
						<tr>
							<td><c:out value="${loopStatus.index+1}" /></td>														
							<td><c:out value="${book.isbn13}" /></td>
							<td><c:out value="${book.title}" /></td>
							<td><c:out value="${book.author}" /></td>
							<td><c:out value="${book.publishDate}" /></td>
							<td><c:out value="${book.price}" /></td>
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