<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<body>
<form:form method="post" action="/" modelAttribute="formdata">
    <form:textarea type="text" name="name"  path="name" rows="30"/>
    <input type="submit" value="Analyze"/>
</form:form>
<div><h2>${classify}</h2></div>
<div>
    <table id="searchResult" class="user users tablesorter">
        <thead>
        <tr>
            <th>Key</th>
            <th>Value</th>
            <th>Timeline</th>

        </tr>
        </thead>
        <c:forEach var="keyvalue" items="${results}">
            <tr >
                <td>${keyvalue.key}</td>
                <td>${keyvalue.value}</td>
                <td>${keyvalue.timeline}</td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>