<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/partials/head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/partials/nav.jsp"></jsp:include>
	<table class="table table-dark">
		<thead>
			<tr>
				<th>Atraccion</th>
				<th>Costo</th>
				<th>Duracion</th>
				<th>Cupo</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${atracciones}" var="atraccion">
					<tr>
						<td><strong><c:out value="${atraccion.nombre}"></c:out></strong>
							<p>DESCRIPCION.</p></td>
						<td><c:out value="${atraccion.costo}"></c:out> monedas</td>
						<td><c:out value="${atraccion.tiempo}"></c:out> hs</td>
						<td><c:out value="${atraccion.cupo}"></c:out></td>
					</tr>
				</c:forEach>
		</tbody>
	</table>

</body>
</html>